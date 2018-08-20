package cn.org.upthink.common.util;

import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpApiUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpApiUtil.class);

    /**
     * 请求接口封装
     * @param requestUrl
     * @param method
     * @param headerMap
     * @param paramMap
     * @return
     */
    public static String doActioon(String requestUrl,
                                   String method,
                                   Map<String, Object> headerMap,
                                   Map<String, Object> paramMap){
        AsyncHttpClient asyncHttpClient = null;
        try{
            asyncHttpClient = new DefaultAsyncHttpClient();
            BoundRequestBuilder builder = asyncHttpClient.prepareGet(requestUrl);
            if("HEAD".equalsIgnoreCase(method)){
                builder = asyncHttpClient.prepareHead(requestUrl);
                logger.info("------------------->HEAD");
            } else if("GET".equalsIgnoreCase(method)){
                builder = asyncHttpClient.prepareGet(requestUrl);
                logger.info("------------------->GET");
            } else if("POST".equalsIgnoreCase(method)){
                builder = asyncHttpClient.preparePost(requestUrl);
                logger.info("------------------->POST");
            } else if("PUT".equalsIgnoreCase(method)){
                builder = asyncHttpClient.preparePut(requestUrl);
                logger.info("------------------->PUT");
            } else if("DELETE".equalsIgnoreCase(method)){
                builder = asyncHttpClient.prepareDelete(requestUrl);
                logger.info("------------------->DELETE");
            } else if("PATCH".equalsIgnoreCase(method)){
                builder = asyncHttpClient.preparePatch(requestUrl);
            } else if("TRACE".equalsIgnoreCase(method)){
                builder = asyncHttpClient.prepareTrace(requestUrl);
            } else if("OPTIONS".equalsIgnoreCase(method)){
                builder = asyncHttpClient.prepareOptions(requestUrl);
            }

            logger.info("######.method="+method);
            /**header请求头参数*/
            if(headerMap!=null && !headerMap.isEmpty()){
                for (Map.Entry<String, Object> entry : headerMap.entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            /**请求参数*/
            List<Param> paramList = new ArrayList<Param>();
            if(paramMap!=null && !paramMap.isEmpty()){
                for (Map.Entry<String, Object> entry : paramMap.entrySet()){
                    paramList.add(new Param(entry.getKey(), entry.getValue()==null?"":entry.getValue()+""));
                }
            }
            builder.addQueryParams(paramList);

            ListenableFuture<Response> future = builder.execute(new AsyncCompletionHandler<Response>(){

                @Override
                public Response onCompleted(Response response) throws Exception {
                    logger.info("######.onCompleted");
                    logger.info("######.statusCode="+response.getStatusCode());
                    return response;
                }

                @Override
                public void onThrowable(Throwable t){
                    logger.info("######.onThrowable，e="+t.getMessage());
                    // Something wrong happened.
                }
            });
            Response rtnResponse = future.get();
            logger.info("===========>>>"+rtnResponse.getResponseBody());
            return rtnResponse.getResponseBody();
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if(asyncHttpClient!=null){
                try {
                    asyncHttpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "{'success':false, 'message':'请求操作异常'}";
    }

}
