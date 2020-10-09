package com.buderus.connection.call;

import com.buderus.connection.config.KM200Device;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.httpclient.HttpClient;

@Service
public class BuderusConnectionCall {

    private HttpClient client = null;
    private KM200Device device = null;

    private static final Logger logger = LoggerFactory.getLogger(BuderusConnectionCall.class);


    @Value("§{km200_gateway_host}")
    String host;
    @Value("§{km200_gateway_password}")
    String gatewayPassword;
    @Value("${km200_private_password}")
    String privatePassword;

    protected byte[] MD5Salt =null;
    protected byte[] cryptKeyInit =null;
    protected byte[] cryptKeyPriv =null;

    public HttpURLConnection getConnection() throws IOException {
        URL url = new URL("http://localhost:8083/swagger-ui.html");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private void RecreateKeys() {
        if (StringUtils.isNotBlank((String)this.gatewayPassword) && StringUtils.isNotBlank((String)this.privatePassword) && this.MD5Salt != null) {
            byte[] MD5_K1 = null;
            byte[] MD5_K2_Init = null;
            byte[] MD5_K2_Private = null;
            byte[] bytesOfGatewayPassword = null;
            byte[] bytesOfPrivatePassword = null;
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e) {
                logger.error("No such algorithm, MD5: {}", (Object)e.getMessage());
            }
            try {
                bytesOfGatewayPassword = this.gatewayPassword.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                logger.error("No such encoding, UTF-8: {}", (Object)e.getMessage());
            }
            byte[] CombParts1 = new byte[bytesOfGatewayPassword.length + this.MD5Salt.length];
            System.arraycopy(bytesOfGatewayPassword, 0, CombParts1, 0, bytesOfGatewayPassword.length);
            System.arraycopy(this.MD5Salt, 0, CombParts1, bytesOfGatewayPassword.length, this.MD5Salt.length);
            MD5_K1 = md.digest(CombParts1);
            MD5_K2_Init = md.digest(this.MD5Salt);
            try {
                bytesOfPrivatePassword = this.privatePassword.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                logger.error("No such encoding, UTF-8: {}", (Object)e.getMessage());
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
