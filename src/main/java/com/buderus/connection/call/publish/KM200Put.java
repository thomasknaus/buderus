package com.buderus.connection.call.publish;

import com.buderus.connection.call.KM200RestAbstract;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
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
    public String doCall(String deviceUrl, String service, Map<String, Object> values, byte[] md5Salt) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut("http://" + deviceUrl + service);

        addHeader(put);

        // Create some NameValuePair for HttpPost parameters
        addEntityToRequest(put, values);
        HttpResponse response = client.execute(put);
        return convertResponseToString(response, md5Salt);
    }
}
