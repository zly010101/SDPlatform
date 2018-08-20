package cn.org.upthink.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by rover on 2017-01-01.
 */
public class HttpRequestUtil {

    private static MyLog log = MyLog.getLog(HttpRequestUtil.class);

    public static String post(String url, Map<String, String> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        log.info("create httppost:" + url);
        HttpPost post = postForm(url, params);

        body = invoke(httpclient, post);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

    public static String get(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        log.info("create httppost:" + url);
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);

        httpclient.getConnectionManager().shutdown();

        return body;
    }


    private static String invoke(DefaultHttpClient httpclient,
                                 HttpUriRequest httpost) {

        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);

        return body;
    }

    private static String paseResponse(HttpResponse response) {
        log.info("get response from http server..");
        HttpEntity entity = response.getEntity();

        log.info("response status: " + response.getStatusLine());
        String charset = EntityUtils.getContentCharSet(entity);
        log.info(charset);

        String body = null;
        try {
            body = EntityUtils.toString(entity);
            log.info(body);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient,
                                            HttpUriRequest httpost) {
        log.info("execute post...");
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, String> params){

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();

        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            log.info("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    /**
     * 发送post请求到HIS
     *
     * @param url 请求的url
     * @param json 封装的json数据
     * @param requestHeader 请求头
     * @return 请求返回值，如果返回null表示请求超时
     * @throws Exception
     */
    public static Map<String, String> sendPostRequest(String url, String json, Map<String, String> requestHeader) throws Exception {
        log.info("******请求url******>"+url);
        HttpPost httppost = new HttpPost(url);
        Map<String, String> resultMap = new HashMap<>();
        String result = null;
        HttpResponse response;
        try {
            httppost.setEntity(new StringEntity(json, "utf-8"));
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
            //设置请求头
            if(requestHeader!=null && !requestHeader.isEmpty()){
                Set<String> keySet = requestHeader.keySet();
                for(String key : keySet) {
                    httppost.setHeader(key, requestHeader.get(key));
                }
            }
            response = new DefaultHttpClient().execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                Header[] headers = response.getHeaders("Set-Cookie");
                String netease_cookie = null;
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].getName().contains("Set-Cookie")) {
                        netease_cookie = headers[i].getValue();
                        if (netease_cookie.contains("kdservice-sessionid")){
                            String kdservice_sessionid = netease_cookie.split(";")[0];
                            resultMap.put("kdservice-sessionid", kdservice_sessionid.split("=")[1]);
                            log.info("====kdservice-sessionid===="+kdservice_sessionid);
                        }
                        if (netease_cookie.contains("ASP.NET_SessionId")){
                            String ASP_SessionId = netease_cookie.split(";")[0];
                            resultMap.put("ASP.NET_SessionId", ASP_SessionId.split("=")[1]);
                            log.info("====ASP.NET_SessionId===="+ASP_SessionId);
                        }
                    }
                }
                resultMap.put("result", result);
            } else {
                log.info("******sendPostRequest.请求接口 返回异常");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }

        return resultMap;
    }

    /**
     * 发送post请求到IM服务器
     *
     * @param url
     * @param json
     * @return 请求返回值，如果返回null表示请求超时
     * @throws Exception
     */
    public static String sendPostRequest(String url, String json) throws Exception {
        HttpPost httppost = new HttpPost(url);
        String result = null;
        HttpResponse response;
        try {
            httppost.setEntity(new StringEntity(json, "utf-8"));
            httppost.setHeader("Content-Type", "charset=UTF-8");
            response = new DefaultHttpClient().execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                log.info("******sendPostRequest.请求接口 返回异常");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    /**
     * post 请求，支持将部分参数放在header里
     * @param url
     * @param headMaps header的参数集合
     * @param params 传递的参数集合
     * @return
     */
    public static String post(String url, Map<String, String> headMaps, Map<String, String> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        log.info("create httppost:" + url);
        HttpPost post = postForm(url, params);
        //post.setHeader("token", token);
        //post.setHeader("sign", v);

        if(headMaps!=null && !headMaps.isEmpty()){
            Set<String> keySet = headMaps.keySet();
            for(String key : keySet) {
                post.setHeader(key, headMaps.get(key));
            }
        }

        body = invoke(httpclient, post);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

}
