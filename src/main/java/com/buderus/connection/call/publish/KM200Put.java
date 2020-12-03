package com.buderus.connection.call.publish;

import com.buderus.connection.call.KM200RestAbstract;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KM200Put extends KM200RestAbstract {

    @Override
    public String doCall(String deviceUrl, String service, String jsonString, byte[] md5Salt) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost put = new HttpPost("http://" + deviceUrl + service);

        addHeader(put);
        //addRequestConfig(put);
        // Create some NameValuePair for HttpPost parameters
        addEntityToRequest(put, jsonString, md5Salt);
        HttpResponse response = client.execute(put);
        String result = convertResponseToString(response, md5Salt);
        put.releaseConnection();
        return result;
    }
}
