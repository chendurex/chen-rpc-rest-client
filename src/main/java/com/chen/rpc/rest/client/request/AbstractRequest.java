package com.chen.rpc.rest.client.request;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 封装HTTP请求，包括基本的创建连接、销毁连接、解析内容等
 * 子类仅需根据不同的方法方式实现发送请求然后把请求结果流返回
 * @author chen
 * 2017/11/13 16:29
 */
public abstract class AbstractRequest implements RpcRequest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String execute() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpRequestBase httpRequest = createRequest();
        log.debug("当前发送的请求路径：{}", httpRequest.getURI());
        setReqProp(httpRequest);
        return parser(httpclient.execute(httpRequest));
    }

    /**
     * 构造真正的发送请求
     * 由子类负责实现，GET方法就实现GET，POST则实现POST
     * @return HttpRequestBase
     * @throws IOException
     */
    protected abstract HttpRequestBase createRequest() throws IOException;

    private void setReqProp(HttpRequestBase request) {
        request.setHeader("accept", "*/*");
        request.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        request.setHeader("Accept-Charset", "utf-8");
        request.setHeader("Charset", "UTF-8");
        request.setHeader("Content-Type","application/json;charset=UTF-8");
        int timeout = 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        request.setConfig(requestConfig);
    }

    private String parser(CloseableHttpResponse response) throws Exception {
        int status = response.getStatusLine().getStatusCode();
        if (200 != status) {
            throw new RuntimeException("未按照预期获取正确的数据，返回的状态码为：[" +
                     status + "]，消息内容为：" + response.getStatusLine().getReasonPhrase());
        }
        BufferedReader responseReader = null;
        try {
            StringBuilder sb = new StringBuilder();
            String readLine;
            responseReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            return sb.toString();
        }  finally{
            try{
                if(responseReader!=null){
                    responseReader.close();
                }
                response.close();
            }
            catch(IOException ex){
                log.error("", ex);
            }
        }
    }
}
