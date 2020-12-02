package com.buderus.connection.call;

import com.buderus.connection.config.KM200Device;
import com.google.common.io.ByteStreams;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class KM200RestCall {

    private final Logger logger = LoggerFactory.getLogger(KM200RestCall.class);

    private HttpClient client = null;
    private KM200Device device;
    boolean connected;

    @Value("${device.url}")
    String deviceUrl;

    @Value("${md5.key}")
    String md5Key;

    @PostConstruct
    public void init() {
        device = new KM200Device();
        device.setMD5Salt(md5Key);
        device.setIP4Address(deviceUrl);
    }

    private byte[] callKM200(String service) {

        byte[] responseBodyB64 = null;
        // Create an instance of HttpClient.
        if (client == null) {
            client = new HttpClient();
        }

        // Create a method instance.
        GetMethod method = new GetMethod("http://" + device.getIP4Address() + service);

        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 30 * 1000);
        method.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 30 * 1000);
        // Set the right header
        method.setRequestHeader("Accept", "application/json");
        method.addRequestHeader("User-Agent", "TeleHeater/2.2.3");


        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);
            // Check the status and the forbidden 403 Error.
            if (statusCode != HttpStatus.SC_OK) {
                String statusLine = method.getStatusLine().toString();
                if (statusLine.contains(" 403 ")) {
                    return new byte[1];
                } else {
                    logger.error("HTTP GET failed: {}", method.getStatusLine());
                    return null;
                }
            }
            device.setCharSet(method.getResponseCharSet());
            // Read the response body.
            responseBodyB64 = ByteStreams.toByteArray(method.getResponseBodyAsStream());

        } catch (HttpException e) {
            logger.error("Fatal protocol violation: ", e);
            connected = false;
        } catch (IOException e) {
            logger.error("Fatal transport error: ", e);
            connected = false;
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        return responseBodyB64;
    }

    public String recieveMessage(String service){
        return decodeMessage(callKM200(service));
    }

    /**
     * This function does the decoding for a new message from the device
     */
    private String decodeMessage(byte[] encoded) {
        String retString = null;
        byte[] decodedB64 = null;

        try {
            decodedB64 = Base64.decodeBase64(encoded);
        } catch (Exception e) {
            logger.error("Message is not in valid Base64 scheme: {}", e);
            e.printStackTrace();
            return null;
        }
        try {
            /* Check whether the length of the decryptData is NOT multiplies of 16 */
            if ((decodedB64.length & 0xF) != 0) {
                /* Return the data */
                retString = new String(decodedB64, device.getCharSet());
                return retString;
            }
            // --- create cipher
            final Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(device.getMD5Salt(), "AES"));
            final byte[] decryptedData = cipher.doFinal(decodedB64);
            byte[] decryptedDataWOZP = removeZeroPadding(decryptedData);
            retString = new String(decryptedDataWOZP, device.getCharSet());
            return retString;
        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException
                | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            // failure to authenticate
            logger.error("Exception on encoding: {}", e);
            return null;
        }
    }

    /**
     * This function removes zero padding from a byte array.
     */
    public byte[] removeZeroPadding(byte[] bytes)
    {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0)
        {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }
}
