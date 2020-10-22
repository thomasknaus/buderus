package com.buderus.connection.call;

import com.buderus.connection.config.util.ThingStatus;
import com.google.common.net.InetAddresses;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class BuderusConnectionCall extends GatewayHandler {

    @Value("${buderus.host}")
    String buderusHost;

    @Value("${buderus.password}")
    String buderusPassword;

    @Value("${private.password}")
    String privatePassword;

    @Value("${private.key}")
    String privateKey;

    @Value("${max.nbr.repeats}")
    String maxNbrRepeats;

    @Value("${md5.key}")
    String md5key;

    private static final Logger logger = LoggerFactory.getLogger(BuderusConnectionCall.class);

    @PostConstruct
    public void init() {
        initializeClass();
    }

    protected final byte[] MD5Salt = new byte[]{
            (byte) 0x86, (byte) 0x78, (byte) 0x45, (byte) 0xe9, (byte) 0x7c, (byte) 0x4e, (byte) 0x29, (byte) 0xdc,
            (byte) 0xe5, (byte) 0x22, (byte) 0xb9, (byte) 0xa7, (byte) 0xd3, (byte) 0xa3, (byte) 0xe0, (byte) 0x7b,
            (byte) 0x15, (byte) 0x2b, (byte) 0xff, (byte) 0xad, (byte) 0xdd, (byte) 0xbe, (byte) 0xd7, (byte) 0xf5,
            (byte) 0xff, (byte) 0xd8, (byte) 0x42, (byte) 0xe9, (byte) 0x89, (byte) 0x5a, (byte) 0xd1, (byte) 0xe4};
    protected byte[] cryptKeyInit = null;
    protected byte[] cryptKeyPriv = null;

    public void initialize() {
        if (!this.getDevice().getInited() && !this.isInitialized()) {
            this.logger.debug("Update KM50/100/200 gateway configuration, it takes a minute....");
            this.getConfiguration();
            if (this.getDevice().isConfigured().booleanValue()) {
                if (!this.checkConfiguration()) {
                    return;
                }
            } else {
                this.updateStatus(ThingStatus.OFFLINE);
                this.logger.info("The KM50/100/200 gateway configuration is not complete");
                return;
            }
            this.readCapabilities();
            this.updateStatus(ThingStatus.ONLINE);
        }
    }

    private void getConfiguration() {
        String ip = this.buderusHost;
        if (StringUtils.isNotBlank((String) ip)) {
            try {
                InetAddresses.forString((String) ip);
            } catch (IllegalArgumentException illegalArgumentException) {
                this.logger.debug("IP4_address is not valid!: {}", (Object) ip);
            }
            this.getDevice().setIP4Address(ip);
        }
        this.logger.debug("No ip4_address configured!");
        this.getDevice().setMD5Salt(this.md5key);
        this.getDevice().setGatewayPassword(this.buderusPassword);
        this.getDevice().setPrivatePassword(this.privatePassword);
        this.logger.debug("Set max. number of repeats to: {} seconds.", (Object) maxNbrRepeats);
        this.getRemoteDevice().setMaxNbrRepeats(Integer.valueOf(maxNbrRepeats));

        RecreateKeys();
        this.getDevice().setCryptKeyInit(this.cryptKeyInit);
        this.getDevice().setCryptKeyPriv(this.cryptKeyPriv);
    }

    private boolean checkConfiguration() {
        JsonObject nodeRoot = this.getRemoteDevice().getServiceNode("/gateway/DateTime");
        if (nodeRoot == null || nodeRoot.isJsonNull()) {
            this.logger.debug("Communication is not possible!");
            this.updateStatus(ThingStatus.OFFLINE);
            return false;
        }
        this.logger.debug("Test of the communication to the gateway was successful..");
        try {
            nodeRoot.get("type").getAsString();
            nodeRoot.get("id").getAsString();
        } catch (JsonParseException e) {
            this.logger.debug("The data is not readable, check the key and password configuration! {}", (Object) e.getMessage());
            this.updateStatus(ThingStatus.OFFLINE);
            return false;
        }
        return true;
    }

    private void RecreateKeys() {
        if (StringUtils.isNotBlank((String) this.buderusPassword) && StringUtils.isNotBlank((String) this.privatePassword) && this.MD5Salt != null) {
            byte[] MD5_K1 = null;
            byte[] MD5_K2_Init = null;
            byte[] MD5_K2_Private = null;
            byte[] bytesOfGatewayPassword = null;
            byte[] bytesOfPrivatePassword = null;
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                logger.error("No such algorithm, MD5: {}", (Object) e.getMessage());
            }
            try {
                bytesOfGatewayPassword = this.buderusPassword.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("No such encoding, UTF-8: {}", (Object) e.getMessage());
            }
            byte[] CombParts1 = new byte[bytesOfGatewayPassword.length + this.MD5Salt.length];
            System.arraycopy(bytesOfGatewayPassword, 0, CombParts1, 0, bytesOfGatewayPassword.length);
            System.arraycopy(this.MD5Salt, 0, CombParts1, bytesOfGatewayPassword.length, this.MD5Salt.length);
            MD5_K1 = md.digest(CombParts1);
            MD5_K2_Init = md.digest(this.MD5Salt);
            try {
                bytesOfPrivatePassword = this.privatePassword.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("No such encoding, UTF-8: {}", (Object) e.getMessage());
            }
            byte[] CombParts2 = new byte[bytesOfPrivatePassword.length + this.MD5Salt.length];
            System.arraycopy(this.MD5Salt, 0, CombParts2, 0, this.MD5Salt.length);
            System.arraycopy(bytesOfPrivatePassword, 0, CombParts2, this.MD5Salt.length, bytesOfPrivatePassword.length);
            MD5_K2_Private = md.digest(CombParts2);
            this.cryptKeyInit = new byte[MD5_K1.length + MD5_K2_Init.length];
            System.arraycopy(MD5_K1, 0, this.cryptKeyInit, 0, MD5_K1.length);
            System.arraycopy(MD5_K2_Init, 0, this.cryptKeyInit, MD5_K1.length, MD5_K2_Init.length);
            this.cryptKeyPriv = new byte[MD5_K1.length + MD5_K2_Private.length];
            System.arraycopy(MD5_K1, 0, this.cryptKeyPriv, 0, MD5_K1.length);
            System.arraycopy(MD5_K2_Private, 0, this.cryptKeyPriv, MD5_K1.length, MD5_K2_Private.length);
        }
    }
}
