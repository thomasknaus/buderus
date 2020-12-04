package com.buderus.connection.call.publish;

import com.buderus.connection.call.KM200RestAbstract;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

@Service
public class KM200Post extends KM200RestAbstract {

    @Override
    public String doCall(String deviceUrl, String service, String jsonString, byte[] md5Salt) throws Exception {
        String result = null;
        HttpClient client = createClient();

        synchronized (client) {

            HttpPost put = new HttpPost("http://" + deviceUrl + service);

            addHeader(put);
            //addRequestConfig(put);
            // Create some NameValuePair for HttpPost parameters
            addEntityToRequest(put, jsonString, md5Salt);
            HttpResponse response = client.execute(put);
            try {
                result = convertResponseToString(response, md5Salt);
            } finally {
                put.releaseConnection();
            }
            return result;
        }
    }
}
