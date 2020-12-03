package com.buderus.connection.call;

import com.buderus.connection.call.publish.OperationModeHC;
import com.google.common.io.ByteStreams;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class KM200RestAbstract {

    private final Logger logger = LoggerFactory.getLogger(KM200RestAbstract.class);

    private String charSet;
    private boolean connected = false;

    protected abstract String doCall(String deviceUrl, String service, String jsonString, byte[] md5Salt) throws IOException;

    protected void addHeader(HttpRequestBase restCall) {
        restCall.addHeader("Accept", "application/json");
        restCall.addHeader("User-Agent", "TeleHeater/2.2.3");
        restCall.addHeader("Content-Type", "ISO-8859-1");
    }

    protected void addEntityToRequest(HttpPost put, String jsonString, byte[] md5Salt) {
        final String contentType = "ISO-8859-1";
        String encodedString = new String(encodeMessage(jsonString,contentType, md5Salt));
        HttpEntity stringEntity = new StringEntity(encodedString, ContentType.APPLICATION_JSON);
        put.setEntity(stringEntity);
    }

    protected void addRequestConfig(HttpRequestBase restCall){
        final RequestConfig params = RequestConfig.custom().setConnectTimeout(30*1000).setSocketTimeout(30*1000).build();
        restCall.setConfig(params);
    }

    protected String convertResponseToString(HttpResponse response, byte[] md5Salt) {
        byte[] responseBodyB64 = null;
        final String contentType = "ISO-8859-1";
        try {
            // Execute the method.
            int statusCode = response.getStatusLine().getStatusCode();
            // Check the status and the forbidden 403 Error.
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("HTTP GET failed: {}", statusCode);
                return null;
            }
            // wrong contentType
            //contentType = response.getEntity().getContentType().getValue();
            setCharSet(contentType);
            // Read the response body.
            responseBodyB64 = ByteStreams.toByteArray(response.getEntity().getContent());

        } catch (HttpException e) {
            logger.error("Fatal protocol violation: ", e);
            connected = false;
        } catch (IOException e) {
            logger.error("Fatal transport error: ", e);
            connected = false;
        }
        String result = null;
        if (responseBodyB64 != null) {
            result = decodeMessage(responseBodyB64, contentType, md5Salt);
        }
        return result;
    }


    /**
     * This function does the decoding for a new message from the device
     */
    private String decodeMessage(byte[] encoded, String charSet, byte[] md5Salt) {
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
                retString = new String(decodedB64, charSet);
                return retString;
            }
            // --- create cipher
            final Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(md5Salt, "AES"));
            final byte[] decryptedData = cipher.doFinal(decodedB64);
            byte[] decryptedDataWOZP = removeZeroPadding(decryptedData);
            retString = new String(decryptedDataWOZP, charSet);
            return retString;
        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException
                | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            // failure to authenticate
            logger.error("Exception on decoding: {}", e);
        }
        return null;
    }

    /**
     * This function removes zero padding from a byte array.
     */
    private byte[] removeZeroPadding(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }

    private byte[] encodeMessage(String message, String charSet, byte[] md5Salt) {
        final Cipher cipher;
        try {
            byte[] byteFromString = message.getBytes(charSet);
            byte[] encodedB64 = Base64.encodeBase64(byteFromString);
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(md5Salt, "AES"));
            final byte[] encryptedData = cipher.doFinal(encodedB64);
            return encodedB64;
        } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            logger.error("Exception on encoding: {}", e);
        }
        return null;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    @Test
    public void checkEncoding() {
        final String charSet = "ISO-8859-1";
        byte[] md5Salt = DatatypeConverter.parseHexBinary("f6671c4f6a0a68f9876f202a154d7010f1f4ed4e08dd66c17ccca4803c2301d5");

        JSONObject obj = new JSONObject();
        obj.put("value", Float.valueOf(10));

        byte[] encodeMessage = encodeMessage(obj.toJSONString(), charSet, md5Salt);
        String jsonString = null;
        byte[] entityMessage = null;
        try {
            jsonString = new String(encodeMessage, charSet);
            HttpEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            entityMessage = ByteStreams.toByteArray(stringEntity.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String entityResult = decodeMessage(entityMessage, charSet, md5Salt);
        String result = decodeMessage(encodeMessage, charSet, md5Salt);
        assertEquals(obj.toJSONString(), result);
        assertEquals(obj.toJSONString(), entityResult);
    }

    public void checkIfRequestEntityCanConvertToNormalString(){
        final String charSet = "ISO-8859-1";
        byte[] md5Salt = DatatypeConverter.parseHexBinary("f6671c4f6a0a68f9876f202a154d7010f1f4ed4e08dd66c17ccca4803c2301d5");

        JSONObject obj = new JSONObject();
        obj.put("value", Float.valueOf(10));

        byte[] encodeMessage = encodeMessage(obj.toJSONString(), charSet, md5Salt);
        try {
            String s = new String(encodeMessage, charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String retString = null;
        try {
            retString = new String(encodeMessage, charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = decodeMessage(encodeMessage, charSet, md5Salt);
        assertEquals(obj.toJSONString(), result);
    }
}
