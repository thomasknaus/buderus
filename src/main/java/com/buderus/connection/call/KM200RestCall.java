package com.buderus.connection.call;

import com.buderus.connection.call.publish.KM200Post;
import com.buderus.connection.call.subscribe.KM200Get;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

@Service
@PropertySources({
        @PropertySource(value = "file:${user.home}/conf/application-buderus.properties",  ignoreResourceNotFound = false)
})
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
    private KM200Post postInfo;

    @PostConstruct
    public void init() {
        md5Salt = DatatypeConverter.parseHexBinary(md5Key);
    }

    public String doGetRequest(String service) throws Exception {
        return km200Measurements.doCall(deviceUrl, service, null, md5Salt);
    }

    public String doPostRequest(String service, String jsonString) throws Exception {
        return postInfo.doCall(deviceUrl, service, jsonString, md5Salt);
    }

    public byte[] getMd5Salt() {
        return md5Salt;
    }
}
