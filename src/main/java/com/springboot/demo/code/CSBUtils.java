package com.springboot.demo.code;


import com.alibaba.csb.sdk.ContentBody;
import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpCallerException;
import com.alibaba.csb.sdk.HttpParameters;
import com.alibaba.csb.sdk.internel.HttpClientHelper;
import com.alibaba.csb.sdk.security.SignUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CSBUtils {

    final static String ak = "24a2f2d75abf42498410a08b98e69f2c";
//    final static String ak = "3e7868db244e4793bc16157a5c779a05";
            final static String sk = "uxhoSC3UcwYTxt/ULkzeaN1EPN4=";
//    final static String sk = "G4FDNwb8hhHrcZSRwk3vXGgWSH0=";

//    final static String url = "https://ssc.mohrss.gov.cn/CSB/ecard";
//            final static String url = "https://124.127.91.146/CSB/ecard";
            final static String url = "https://59.252.162.146:443/CSB/ecard";
            static CloseableHttpClient curClient = null;
            static {
                try {
            curClient = HttpClients.custom().setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build()).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
//        getInfo();
        for (int i = 0; i < 1000; i++) {
            getInfo();
        }
//        getSignInfo();
        /*ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        int i = 0;
        while(i++ < 20){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    getSignInfo();
                }
            });
        }
        executor.shutdown();*/
    }


    public static String getInfo(){
        JSONObject json = new JSONObject();
        json.put("channelNo","9130900001");
        json.put("aab301","350800");//参保地编码
        json.put("aac002","350802199106281523");//证件号
        json.put("aac003","廖文苑");//姓名
//        json.put("aac002","350100198506020140");//证件号
//        json.put("aac003","张亚慧");//姓名
        json.put("aac067","15033454432");//手机号  https://ssc.mohrss.gov.cn/CSB/ecard
        HttpParameters.Builder builder = HttpParameters.newBuilder();
        builder.requestURL(url).api("sign_info").version("1.0.0").method("post").accessKey(ak).secretKey(sk);
        ContentBody body = new ContentBody(json.toString());
        builder.contentBody(body);
        String result = null;
        try {
            result = HttpCaller.invoke(builder.build());
            System.out.println(result);
        } catch (HttpCallerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getSignInfo(){
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        JSONObject json = new JSONObject();
        json.put("channelNo","9130900001");
        json.put("aab301","430200");//参保地编码
        json.put("aac002","350100198506020140");//证件号
        json.put("aac003","张亚慧");//姓名
        json.put("aac067","15033454432");//手机号  https://ssc.mohrss.gov.cn/CSB/ecard
        String res = null;
        try{
            Map<String,String> headerParamsMap = new HashMap<>();
            long ts = System.currentTimeMillis();
            headerParamsMap.put("_api_name", "sign_info");
            headerParamsMap.put("_api_version", "1.0.0");
            headerParamsMap.put("_api_timestamp", String.valueOf(ts));
            headerParamsMap.put("_api_access_key", ak);
//            headerParamsMap.put("_api_signature", SignUtil.signMultiValueParams(newParamsMap, sk));
            headerParamsMap.put("_api_signature", SignUtil.sign(headerParamsMap, sk));
            HttpPost httpPost = HttpClientHelper.createPost(url, null, headerParamsMap, new ContentBody(json.toString()));
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(30*1000).setConnectTimeout(30*1000).setSocketTimeout(60*1000).build();
            httpPost.setConfig(config);
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            httpPost.addHeader("Connection", "close");
            httpPost.addHeader("Content-Type","application/json;charset=utf-8");
            httpPost.addHeader("Accept","*/*");
            response = curClient.execute(httpPost);
            if(response != null){
                entity = response.getEntity();
                res = EntityUtils.toString(entity);
                System.out.println(res);
            }
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            try {
                if(entity != null ) EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
}
