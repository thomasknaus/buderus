/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.binary.Base64
 *  org.apache.commons.lang.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class KM200Cryption {
    private final Logger logger = LoggerFactory.getLogger(KM200Cryption.class);
    private final KM200Device remoteDevice;

    public KM200Cryption(KM200Device remoteDevice) {
        this.remoteDevice = remoteDevice;
    }

    private byte[] removeZeroPadding(byte[] bytes) {
        int i;
        for (i = bytes.length - 1; i >= 0 && bytes[i] == 0; --i) {
        }
        return Arrays.copyOf(bytes, i + 1);
    }

    private byte[] addZeroPadding(byte[] bdata, int bSize, String cSet) throws UnsupportedEncodingException {
        int encryptPadchar = bSize - bdata.length % bSize;
        byte[] padchars = new String(new char[encryptPadchar]).getBytes(cSet);
        byte[] paddedData = new byte[bdata.length + padchars.length];
        System.arraycopy(bdata, 0, paddedData, 0, bdata.length);
        System.arraycopy(padchars, 0, paddedData, bdata.length, padchars.length);
        return paddedData;
    }

    public String decodeMessage(byte[] encoded) {
        String retString = null;
        byte[] decodedB64 = null;
        decodedB64 = Base64.decodeBase64((byte[])encoded);
        try {
            if ((decodedB64.length & 0xF) != 0) {
                retString = new String(decodedB64, this.remoteDevice.getCharSet());
                return retString;
            }
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(2, new SecretKeySpec(this.remoteDevice.getCryptKeyPriv(), "AES"));
            byte[] decryptedData = cipher.doFinal(decodedB64);
            byte[] decryptedDataWOZP = this.removeZeroPadding(decryptedData);
            return new String(decryptedDataWOZP, this.remoteDevice.getCharSet());
        }
        catch (UnsupportedEncodingException | GeneralSecurityException e) {
            this.logger.debug("Exception on encoding: {}", (Throwable)e);
            return null;
        }
    }

    public byte[] encodeMessage(String data) {
        try {
            byte[] bdata = data.getBytes(this.remoteDevice.getCharSet());
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(1, new SecretKeySpec(this.remoteDevice.getCryptKeyPriv(), "AES"));
            int bsize = cipher.getBlockSize();
            this.logger.debug("Add Padding, encrypt AES and B64..");
            byte[] encryptedData = cipher.doFinal(this.addZeroPadding(bdata, bsize, this.remoteDevice.getCharSet()));
            try {
                return Base64.encodeBase64((byte[])encryptedData);
            }
            catch (IllegalArgumentException e) {
                this.logger.info("Base64encoding not possible: {}", (Object)e.getMessage());
            }
        }
        catch (UnsupportedEncodingException | GeneralSecurityException e) {
            this.logger.error("Exception on encoding: {}", (Throwable)e);
        }
        return null;
    }

    public void recreateKeys() {
        if (StringUtils.isNotBlank((String)this.remoteDevice.getGatewayPassword()) && StringUtils.isNotBlank((String)this.remoteDevice.getPrivatePassword()) && this.remoteDevice.getMD5Salt() != null) {
            byte[] md5K1 = null;
            byte[] md5K2Init = null;
            byte[] md5K2Private = null;
            byte[] bytesOfGatewayPassword = null;
            byte[] bytesOfPrivatePassword = null;
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e) {
                this.logger.error("No such algorithm, MD5: {}", (Object)e.getMessage());
                return;
            }
            try {
                bytesOfGatewayPassword = this.remoteDevice.getGatewayPassword().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                this.logger.error("No such encoding, UTF-8: {}", (Object)e.getMessage());
                return;
            }
            byte[] combParts1 = new byte[bytesOfGatewayPassword.length + this.remoteDevice.getMD5Salt().length];
            System.arraycopy(bytesOfGatewayPassword, 0, combParts1, 0, bytesOfGatewayPassword.length);
            System.arraycopy(this.remoteDevice.getMD5Salt(), 0, combParts1, bytesOfGatewayPassword.length, this.remoteDevice.getMD5Salt().length);
            md5K1 = md.digest(combParts1);
            md5K2Init = md.digest(this.remoteDevice.getMD5Salt());
            try {
                bytesOfPrivatePassword = this.remoteDevice.getPrivatePassword().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                this.logger.error("No such encoding, UTF-8: {}", (Object)e.getMessage());
                return;
            }
            byte[] combParts2 = new byte[bytesOfPrivatePassword.length + this.remoteDevice.getMD5Salt().length];
            System.arraycopy(this.remoteDevice.getMD5Salt(), 0, combParts2, 0, this.remoteDevice.getMD5Salt().length);
            System.arraycopy(bytesOfPrivatePassword, 0, combParts2, this.remoteDevice.getMD5Salt().length, bytesOfPrivatePassword.length);
            md5K2Private = md.digest(combParts2);
            byte[] cryptKeyInit = new byte[md5K1.length + md5K2Init.length];
            System.arraycopy(md5K1, 0, cryptKeyInit, 0, md5K1.length);
            System.arraycopy(md5K2Init, 0, cryptKeyInit, md5K1.length, md5K2Init.length);
            this.remoteDevice.setCryptKeyInit(cryptKeyInit);
            byte[] cryptKeyPriv = new byte[md5K1.length + md5K2Private.length];
            System.arraycopy(md5K1, 0, cryptKeyPriv, 0, md5K1.length);
            System.arraycopy(md5K2Private, 0, cryptKeyPriv, md5K1.length, md5K2Private.length);
            this.remoteDevice.setCryptKeyPriv(cryptKeyPriv);
        }
    }
}

