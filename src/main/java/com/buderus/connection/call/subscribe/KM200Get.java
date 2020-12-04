package com.buderus.connection.call.subscribe;

import com.buderus.connection.call.KM200RestAbstract;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KM200Get extends KM200RestAbstract {

    @Override
    public String doCall(String deviceUrl, String service, String jsonString, byte[] md5Salt) throws Exception {
        String result = null;
        HttpClient client = createClient();
        synchronized (client) {
            HttpGet get = new HttpGet("http://" + deviceUrl + service);

            addHeader(get);
            addRequestConfig(get);
            HttpResponse response = client.execute(get);
            try {
                result = convertResponseToString(response, md5Salt);
            } finally {
                get.releaseConnection();
            }
            return result;
        }
    }
}
