package com.uumai.crawer2.download.httpclient;

import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.CrawlerProxy;
import com.uumai.crawer2.download.Download;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by kanxg on 14-12-22.
 */
public class HttpClientDownload implements Download {
    private CloseableHttpClient httpclient ;

    public HttpClientDownload(){
        httpclient = HttpConnectionManager.getHttpClient();
    }

    private CrawlerResult download(String urlStr,HttpHost proxy) throws Exception {
        CrawlerResult crawlerResult=new CrawlerResult();


        try {
            //CloseableHttpClient httpclient = HttpClients.createDefault();
            //CloseableHttpClient httpclient = HttpConnectionManager.getHttpClient();
            //httpclient = HttpConnectionManager.getHttpClient();

            HttpGet httpGet = new HttpGet(urlStr);
            if(proxy!=null){
                RequestConfig config = RequestConfig.custom()
                        .setProxy(proxy)
                        .build();
                httpGet.setConfig(config);
            }
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try {
                //System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();

                // do something useful with the response body
                // and ensure it is fully consumed
                String html = EntityUtils.toString(entity1);
                crawlerResult.setRawText(html);
            } finally {
                //response1.close();
                //httpclient.close();
                try {
                    if (response1 != null) {
                        //ensure the connection is released back to pool
                        EntityUtils.consume(response1.getEntity());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //httpGet.releaseConnection();
                response1.close();
            }
        } catch (Exception ex) {
           ex.printStackTrace();
        }


        return crawlerResult;

     }
    public CrawlerResult download(CrawlerTasker tasker) throws Exception {

        if(tasker.getProxy()!=null){

            return download(tasker.getUrl(),tasker.getProxy().gethttpclientproxy());
        }
        return download(tasker.getUrl(),null);
    }
    public static void main(String[] args) throws Exception {

        CrawlerProxy proxy=new CrawlerProxy("127.0.0.1", 8087);
        HttpClientDownload download = new HttpClientDownload();
        String html = download.download("http://www.abercrombie.com/shop/us",proxy.gethttpclientproxy()).getRawText();
//        String html = download.download("http://www.abercrombie.com/shop/us/mens-tops-new-arrivals/bear-run-baseball-jacket-4003080_02",null);

        System.out.print(html);
    }
}

