package com.springboot.demo.code;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpClientDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://39.104.99.66/api/safetyKnowledge/list");
        post.addHeader("Connection","close");
        post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        post.addHeader("Content-Type","application/json; charset=utf-8");
        post.setEntity(new StringEntity("{}"));
        HttpEntity entity = null;
        try {
            CloseableHttpResponse res = client.execute(post);
            if(res != null){
                entity = res.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }
}
