package com.buderus.connection.call;

import com.buderus.connection.call.publish.KM200Put;
import com.buderus.connection.call.subscribe.KM200Get;
import org.apache.commons.httpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Service
public class KM200RestCall {

    private final Logger logger = LoggerFactory.getLogger(KM200RestCall.class);

    private HttpClient client = null;
    boolean connected;

    @Value("${device.url}")
    String deviceUrl;

    @Value("${md5.key}")
    String md5Key;

    byte[] md5Salt;
    String charSet;

    @Autowired
    private KM200Get km200Measurements;

    @Autowired
    private KM200Put postInfo;

    @PostConstruct
    public void init() {
        md5Salt = DatatypeConverter.parseHexBinary(md5Key);
    }

    public String doGetRequest(String service) throws IOException {
        return km200Measurements.doCall(deviceUrl, service, null, md5Salt);
    }

    public String doPostRequest(String service, String jsonString) throws IOException {
        return postInfo.doCall(deviceUrl, service, jsonString, md5Salt);
    }

    public byte[] getMd5Salt() {
        return md5Salt;
    }
}
