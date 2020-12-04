package com.buderus.connection.call;

import com.buderus.connection.call.publish.OperationModeHC;
import com.google.common.io.ByteStreams;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.nio.BufferOverflowException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class KM200RestAbstract extends KM200RestHelper {

    private final Logger logger = LoggerFactory.getLogger(KM200RestAbstract.class);

    private String charSet;
    private boolean connected = false;

    protected abstract String doCall(String deviceUrl, String service, String jsonString, byte[] md5Salt) throws Exception;

    protected void addHeader(HttpRequestBase restCall) {
        restCall.addHeader("Accept", "application/json");
        restCall.addHeader("User-Agent", "TeleHeater/2.2.3");
        restCall.addHeader("Content-Type", "ISO-8859-1");
    }

    protected void addEntityToRequest(HttpPost put, String jsonString, byte[] md5Salt) throws Exception {
        final String contentType = "ISO-8859-1";
        byte[] data = encodeMessage(jsonString, contentType, md5Salt);
        ByteArrayEntity entity = new ByteArrayEntity(data);
        entity.setChunked(false);
        put.setEntity(entity);
    }

    protected void addRequestConfig(HttpRequestBase restCall) {
        final RequestConfig params = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
        restCall.setConfig(params);
    }

    protected HttpClient createClient() {
        HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3, false);
        HttpClient client = HttpClientBuilder.create().setRetryHandler(retryHandler).build();
        return client;
    }

    protected String convertResponseToString(HttpResponse response, byte[] md5Salt) throws Exception {
        byte[] responseBodyB64 = null;
        final String contentType = "ISO-8859-1";
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
        String result = null;
        if (responseBodyB64 != null) {
            result = decodeMessage(responseBodyB64, contentType, md5Salt);
        }
        return result;
    }

    /**
     * This function does the decoding for a new message from the device
     */
    private String decodeMessage(byte[] encoded, String charSet, byte[] md5Salt) throws Exception {
        String retString = null;
        byte[] decodedB64 = Base64.decodeBase64(encoded);
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
            throw new Exception(e);
        }
    }

    /**
     * This function does the encoding for a new message to the device
     */
    public byte[] encodeMessage(String data, String charSet, byte[] md5Salt) throws Exception {
        byte[] encryptedDataB64 = null;

        try {
            // --- create cipher
            byte[] bdata = data.getBytes(charSet);
            final Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(md5Salt, "AES"));
            logger.debug("Create padding..");
            int bsize = cipher.getBlockSize();
            logger.debug("Add Padding and Encrypt AES..");
            final byte[] encryptedData = cipher.doFinal(addZeroPadding(bdata, bsize, charSet));
            logger.debug("Encrypt B64..");
            try {
                encryptedDataB64 = Base64.encodeBase64(encryptedData);
            } catch (Exception e) {
                logger.error("Base64encoding not possible: {}", e.getMessage());
            }
            return encryptedDataB64;
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            // failure to authenticate
            logger.error("Exception on encoding: {}", e);
            throw new Exception(e);
        }
    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    @Test
    public void checkEncoding() throws Exception {
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

    @Test
    public void checkIfRequestEntityCanConvertToNormalString() throws Exception {
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
