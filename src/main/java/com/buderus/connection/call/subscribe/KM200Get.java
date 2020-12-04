package com.buderus.connection.call.subscribe;

import com.buderus.connection.call.KM200RestAbstract;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;

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
