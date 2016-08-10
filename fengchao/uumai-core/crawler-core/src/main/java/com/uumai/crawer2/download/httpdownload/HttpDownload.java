package com.uumai.crawer2.download.httpdownload;

import com.uumai.crawer.util.UumaiProperties;
import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawer2.CookieManager.CookieHelper;
import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.CrawlerProxy;
import com.uumai.crawer2.download.Download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by kanxg on 14-12-18.
 */
public class HttpDownload implements Download {

    private CrawlerTasker tasker;

    public HttpDownload(){
        java.net.CookieManager cm = new java.net.CookieManager();
        java.net.CookieHandler.setDefault(cm);
    }
    private void setCookie(HttpURLConnection urlConnection, List<CrawlerCookie> cookies){
        if(cookies!=null){
            String setcooki="";
            for(CrawlerCookie cookie:cookies){
                setcooki=setcooki + cookie.getName() +"=" + cookie.getValue()+";";
            }
//                setcooki=setcooki+";path=/;";
            if(setcooki.endsWith(";")){
                setcooki=setcooki.substring(0,setcooki.length()-1);
            }

            urlConnection.setRequestProperty("Cookie", setcooki);

        }
    }
    private void setHeader(HttpURLConnection urlConnection){
        //set default value
        setDefaultHeader(urlConnection,"User-Agent",
                "Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0");
//        urlConnection.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");
        //urlConnection.setRequestProperty("Transfer-Encoding", "chunked" );
//        urlConnection.setRequestProperty("Content-Length", "200");
        //urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
//        urlConnection.setRequestProperty("Content-Type",
//                "application/json; charset=utf-8");
        setDefaultHeader(urlConnection, "Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");

//         setDefaultHeader(urlConnection, "Content-Type",
//                        "application/json; charset=UTF-8");
        setDefaultHeader(urlConnection,"Accept-Encoding", "gzip,deflate");
//        setDefaultHeader(urlConnection,"Referer", "google.com");

        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000*2);

        if(this.tasker.getRequestHeaderList()==null ) return;
        if(this.tasker.getRequestHeaderList().size()==0 ) return;

        for(String requestHeaderStr: this.tasker.getRequestHeaderList()){
            String[] requestHeader=requestHeaderStr.split(":");
            if(requestHeader==null||requestHeader.length!=2) continue;
            if(!"null".equalsIgnoreCase(requestHeader[1])){
                if("setConnectTimeout".equalsIgnoreCase(requestHeader[0])) {
                    urlConnection.setConnectTimeout(new Integer(requestHeader[1]));

                }else if("setReadTimeout".equalsIgnoreCase(requestHeader[0])) {
                    urlConnection.setReadTimeout(new Integer(requestHeader[1]));

                }else{
                    urlConnection.setRequestProperty(requestHeader[0], requestHeader[1]);
                }
            }
        }
    }

    /**
     * if cusomer not set this header, use default value
     * if customer set this header:
     *      if value==null,   clean default value;
     *      else  set value from customer
     * @param urlConnection
     * @param header
     * @param value
     */

    private void setDefaultHeader(HttpURLConnection urlConnection,String header,String value){
        boolean customersetthisheader=false;
        if(this.tasker.getRequestHeaderList()!=null && this.tasker.getRequestHeaderList().size()!=0  ){
            for(String requestHeaderStr: this.tasker.getRequestHeaderList()) {
                String[] requestHeader = requestHeaderStr.split(":");
                if(requestHeader==null||requestHeader.length!=2) continue;
                     if(requestHeader[0].equalsIgnoreCase(header)) {
                         customersetthisheader = true;
                         break;
                     }
            }
        }
        if(!customersetthisheader) {
            urlConnection.addRequestProperty(header, value);
        }
    }
    private CrawlerResult download(String urlStr,List<CrawlerCookie> cookies,Proxy proxy,String incomingencode,boolean follingRedirect) throws Exception {

        URL url = new URL(urlStr);
        HttpURLConnection urlConnection ;
        if(proxy==null){
            urlConnection=(HttpURLConnection) url
                    .openConnection();
        }else{
            urlConnection=(HttpURLConnection) url
                    .openConnection(proxy);
        }
//        urlConnection.setDoInput(true);
//
//        urlConnection.setDoOutput(true);

        setHeader(urlConnection);


//        urlConnection.setInstanceFollowRedirects(true);
//        HttpURLConnection.setFollowRedirects(true);

        setCookie(urlConnection,cookies);

        urlConnection.connect();

        int return_code=urlConnection.getResponseCode();
        //System.out.println("return_code"+return_code);
        CrawlerResult crawlerResult=new CrawlerResult();
        crawlerResult.setReturncode(return_code);
        CookieHelper cookieHelper=new CookieHelper();
        crawlerResult.setCookies(cookies);
        crawlerResult.addNewCookies(cookieHelper.parseCookies(urlConnection));
        if (return_code >= 300) {
            System.out.println("failed: url:" + url);
            if(return_code== 301 ||return_code==302){
                String reurl=urlConnection.getHeaderField("Location");
                System.out.println("redirect url"+urlStr  +"  >>> " +reurl );
                if(follingRedirect){
                    return this.download(reurl,crawlerResult.getCookies(),proxy,incomingencode,false);
//                URL u = new URL(urlConnection.getHeaderField("Location"));
//                if(proxy==null){
//                    urlConnection=(HttpURLConnection) u
//                            .openConnection();
//                }else{
//                    urlConnection=(HttpURLConnection) u
//                            .openConnection(proxy);
//                }
//                String redcookies=urlConnection.getRequestProperty("Cookie");
//                System.out.println("redcookies:" + redcookies);
                }else{
                    return crawlerResult;
                }


            }else {

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    byte[] buf = new byte[4096];
                     InputStream es = urlConnection.getErrorStream();
                    int ret = 0;
                    // read the response body
                    while ((ret = es.read(buf)) > 0) {
                        os.write(buf, 0, ret);
                    }
                    // close the errorstream
                    es.close();

                    System.out.println("failed: return_code: " + return_code);

//                    System.out.println("failed: return_code" + return_code+",Error:"+ new String(os.toByteArray()));
                    throw new Exception("Error when download:"+return_code +",Error:"+ new String(os.toByteArray()));


            }
        }

        //InputStream is= urlConnection.getInputStream();

        String encoding=urlConnection.getContentType();
        if(encoding!=null){
            if(encoding.indexOf("charset=")==-1){
                encoding=null;
            }else{
                encoding=encoding.substring(encoding.indexOf("charset=")+8).trim();
            }
        }



        InputStream is=null;
        if ("gzip".equals(urlConnection.getContentEncoding())) {
            is = new GZIPInputStream(
                    urlConnection.getInputStream());
        }else{
            is=urlConnection.getInputStream();
        }

        //is = urlConnection.getInputStream();
        BufferedReader reader = null;
        if(incomingencode==null){
            if (encoding==null){
                reader=new BufferedReader(
                        new InputStreamReader(is,"utf-8"));
            }else{
                reader=new BufferedReader(
                        new InputStreamReader(is,encoding));
            }
        }else{
            reader=new BufferedReader(
                    new InputStreamReader(is,incomingencode));
        }


        String s;
        StringBuilder result = new StringBuilder();
        while (((s = reader.readLine()) != null)) {
            result.append(s);
        }

        reader.close();

        //urlConnection.disconnect();

        //System.out.println("result= " + result.toString());
        //System.out.println("cookie:"+ parseCookies(urlConnection));

        crawlerResult.setRawText(result.toString());
        return crawlerResult;

//        Html html =new Html(result.toString());
//
//        String title =html.xpath("//h2[@class='name']/text()").toString();
//
//        is.close();
//        return title;

//        return Jsoup.clean(result.toString(), Whitelist.basic());

    }


    private CrawlerResult doPost(String urlStr,String postdata,List<CrawlerCookie> cookies, Proxy proxy,String incomingencode,boolean follingRedirect)  throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection ;
        if(proxy==null){
            urlConnection=(HttpURLConnection) url
                    .openConnection();
        }else{
            urlConnection=(HttpURLConnection) url
                    .openConnection(proxy);
        }
//        urlConnection.setDoInput(true);
//
//        urlConnection.setDoOutput(true);
//        urlConnection.setRequestProperty("Connection", "Keep-Alive");
//
//        urlConnection.setRequestProperty("User-Agent",
//                "Mozilla/5.0 (compatible; MSIE 6.0; Windows NT)");
//        urlConnection.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");
//        urlConnection.setRequestProperty("Transfer-Encoding", "chunked" );
//
//        urlConnection.setRequestProperty("Content-Length", "200");
        setHeader(urlConnection);
        setCookie(urlConnection,cookies);


        urlConnection.setRequestMethod("POST");// 设置请求方法为post
        urlConnection.setDoOutput(true);// 设置此方法,允许向服务器输出内容


         // post请求的参数
         // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
        OutputStream out = urlConnection.getOutputStream();// 获得一个输出流,向服务器写数据
        out.write(postdata.getBytes("UTF-8"));
        out.flush();
        out.close();


        int return_code=urlConnection.getResponseCode();
        //System.out.println("return_code"+return_code);

//        if(return_code!=200){
//            throw new Exception("Error when parse");
//        }
        CookieHelper cookieHelper =new CookieHelper();
        CrawlerResult crawlerResult=new CrawlerResult();
        crawlerResult.setReturncode(return_code);
        crawlerResult.setCookies(cookies);
        crawlerResult.addNewCookies(cookieHelper.parseCookies(urlConnection));


        if (return_code >= 300) {
            System.out.println("failed: url:" + url);
            if(return_code== 301 ||return_code==302){
                String reurl=urlConnection.getHeaderField("Location");
                System.out.println("redirect url"+urlStr  +"  >>> " +reurl );
                if(follingRedirect){
                    return this.download(reurl,crawlerResult.getCookies(),proxy,incomingencode,true);
//                URL u = new URL(urlConnection.getHeaderField("Location"));
//                if(proxy==null){
//                    urlConnection=(HttpURLConnection) u
//                            .openConnection();
//                }else{
//                    urlConnection=(HttpURLConnection) u
//                            .openConnection(proxy);
//                }
//                String redcookies=urlConnection.getRequestProperty("Cookie");
//                System.out.println("redcookies:" + redcookies);
                }else{
                    return crawlerResult;
                }


            }else {
                try {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    byte[] buf = new byte[4096];
                    InputStream es = urlConnection.getErrorStream();
                    int ret = 0;
                    // read the response body
                    while ((ret = es.read(buf)) > 0) {
                        os.write(buf, 0, ret);
                    }
                    // close the errorstream
                    es.close();

                    System.out.println("failed: return_code: " + return_code);

//                    System.out.println("failed: return_code" + return_code+",Error:"+ new String(os.toByteArray()));
                    throw new Exception("Error when download:"+return_code +",Error:"+ new String(os.toByteArray()));

                } catch(IOException ex) {
                    throw new Exception("Error when download:"+return_code+",Error:"+ex.getMessage());
                }
            }
        }




        String encoding=urlConnection.getContentType();
        if(encoding!=null){
            if(encoding.indexOf("charset=")==-1){
                encoding=null;
            }else{
                encoding=encoding.substring(encoding.indexOf("charset=")+8).trim();
            }
        }

        InputStream is=null;
        if ("gzip".equals(urlConnection.getContentEncoding())) {
            is = new GZIPInputStream(
                    urlConnection.getInputStream());
        }else{
            is=urlConnection.getInputStream();
        }

        BufferedReader reader = null;
        if(incomingencode==null){
            if (encoding==null){
                reader=new BufferedReader(
                        new InputStreamReader(is,"utf-8"));
            }else{
                reader=new BufferedReader(
                        new InputStreamReader(is,encoding));
            }
        }else{
            reader=new BufferedReader(
                    new InputStreamReader(is,incomingencode));
        }


        String s;
        StringBuilder result = new StringBuilder();
        while (((s = reader.readLine()) != null)) {
            result.append(s);
        }

        //System.out.println("result= " + result.toString());
        crawlerResult.setRawText(result.toString());
        return crawlerResult;

//        Html html =new Html(result.toString());
//
//        String title =html.xpath("//h2[@class='name']/text()").toString();
//
//        is.close();
//        return title;
    }

    @Override
    public CrawlerResult download(CrawlerTasker tasker) throws Exception {
        this.tasker=tasker;
        if(tasker.getRequestmethod().equals("POST")){
            if(tasker.getProxy()!=null){

                return doPost(tasker.getUrl(), tasker.getPostdata(),tasker.getCookies(),tasker.getProxy().getproxy(),tasker.getEncoding(),tasker.isFollingRedirect());
            }
            return doPost(tasker.getUrl(),tasker.getPostdata(), tasker.getCookies(),null,tasker.getEncoding(), tasker.isFollingRedirect());

        }else{
            if(tasker.getProxy()!=null){

                return download(tasker.getUrl(),tasker.getCookies(),tasker.getProxy().getproxy(),tasker.getEncoding(),tasker.isFollingRedirect());
            }
            return download(tasker.getUrl(),tasker.getCookies(),null,tasker.getEncoding(),tasker.isFollingRedirect());
        }

    }


        public static void main(String[] args) throws Exception {

//            UumaiProperties.init("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/crawler-example/deploy/resources/uumai.properties");
//            CookieHelper cookieHelper=new CookieHelper();
//
//            List<CrawlerCookie> cookies =cookieHelper.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/jd_cookies.txt"));



//            CrawlerProxy proxy=new CrawlerProxy("cn-proxy.jp.oracle.com", 80);
//            HttpDownload download = new HttpDownload();
////           String cookie= null; //CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/amazon_cookies.txt"));
////            System.out.println("cookie:" + cookie);
//
//           // cookie=cookie+ ";x-main=hWFbJv@Hz9ICVH8@g0QoHZXhrfaPkyLZQGTDeb5WjX9mdKd4UVFge7ruB5F?1GI8";
//          //  cookie=cookie+";at-main=5|+zO5nd/NtkMkEwoQUrqbX9kHNxMQKpY0mo3M7TbXj5EXxkfohTRkgtFaCmaGjIDQMpoGKYmUiJZvvQ6b22G77FcURXYsPHjgryDynhUooENWhZVv0E6LetVfUZ1I1KZ33Sp3c2axH+V32K8WqCJjN3g8ePKYUua78mlgjXV7qQpgQ6LXP/n+AlKAWijmYcPQxfo4I0I2wDddJQ7hhTdsCfX/dbtVZ5LAU3zfzCzP9uFwJTUquB4GACyL9GXRRbuJmPYcS9xNPEberIPquBauCJ6ic7V2uEbf";
//           // cookie=cookie+";x-wl-uid=18eYIcRVAo8nejmSETBjY5JuuubbrR+NA9QrYkvEctW+jpjm4YfD4NO97Rpat1vrGbdk18Bz2/2wJ/k6rl2ODTh47P7IznMXijJTREgWVeesFj99nvjnuabSJ2dp05Z6hMGZMneP4pC8=";
//           // cookie=cookie+";ad-id=A6cenTLJsE3qjB599ZzVxVs";
//           // cookie=cookie+";csm-hit=1HR0QWFBP7FBDCDF98WA+s-1HHYKC4FPH0K4S0HXKGM|1437703820784";
//          //  cookie=cookie+";test_cookie=CheckForPermission";
//
////            String html = download.download("http://category.dangdang.com/?ref=www-0-C",cookies,proxy.getproxy(),null);
//            String url="ttp://wap.cmbbnc.com/add_1.asp";
//            CrawlerTasker crawlerTasker=new CrawlerTasker();
//            crawlerTasker.addRequestHeader("User-Agent"," Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
//
//            download.tasker=crawlerTasker;
//            String html = download.download(url ,null,proxy.getproxy(),null,true).getRawText();
//
//            System.out.print("html:"+html);
//        String html = download.download("http://www.abercrombie.com/shop/us/mens-tops-new-arrivals/bear-run-baseball-jacket-4003080_02",null);

//            String cookie="skin=noskin;x-wl-uid=11Wqlo91tmIxNzPsOammYXbX8xoDAnGP5brdpd1L1zELtQYprdCGZ59WuXN8xstxOO5n6EEYcXoSYTzHfyurNjWx21hnDu6icqk55OUZMDVBSGWOB3hArf4N8bhZMLpekZrlyZGQUZWM=;session-id-time=2082787201l;session-id=176-7013548-3228859;";
//            String cookie="ubid-main=186-0357180-0469409;session-id=177-4540123-2088309;session-id-time=2082787201l;session-token=yBrpY9jipo55LbvA6bbAiz6...4z42ORYEcktXNMCMgBfHk=";


           // String url="http://www.amazon.com/Calvin-Klein-Dress-Shirt-32-33/dp/B0058YU990/ref=lh_ni_t?ie=UTF8&psc=1&smid=ATVPDKIKX0DER";
//            String cookie="";
//            String url="http://dyn.keepa.com/product/?domain=1&asin=B005QHR816&type=light&source=0";
//            String html=download.download(url,cookie,null);
            //System.out.print("html:"+html);

//

//            String cookie=CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/xueqiu_cookies.txt"));
//            String html =  download.download("http://xueqiu.com/stock/forchartk/stocklist.json?symbol=SZ000568&period=1week&type=normal",cookie,proxy.getproxy(),null);
           // System.out.print("html:"+html);
            CrawlerTasker tasker=new CrawlerTasker();
            HttpDownload download = new HttpDownload();


            String url = "http://proxy.mimvp.com/api/fetch.php?orderid=860160428134258822&num=500&http_type=1,2&anonymous=5&ping_time=1&transfer_time=5&result_fields=1,2,3,4,5,6,7,8,9&result_format=json";
//            tasker.setRequestmethod("POST");
//            tasker.setPostdata("__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUKLTI2NTI5MTc0NA8WDh4IUGFnZVNpemUCMh4FUmFuZ2UCtAEeDlNlbGVjdGVkRmllbGRzBSlBUkVBLFRPUERFR1JFRSxXT1JLWUVBUixXT1JLRlVOMSxTRU5EREFURR4JUGFnZUluZGV4AgEeDVBhZ2VMb2FkQ29pZHMFBzE5OTE3NDkeDVBhZ2VMb2FkRGl2ZHNlHgxUb3RhbFJlY29yZHMCoI0GFgICAQ9kFhYCBA8PFhAeElNwZWNpYWxDb250cm9sTmFtZQUHSk9CTkFNRR4NU2VhcmNoQnRuRmxhZwUDMV81HgVGb2RJRAUBMR4WQ2FuZGlkYXRlRGVmYXVsdFNlYXJjaGUeFElzSGFzQ29tcGFueURpdmlzaW9uZx4IU1FMV2hlcmVlHghTUUxUYWJsZWUeBFRleHRlZBYeZg9kFgJmD2QWBAIBDxBkEBUBEuivt%2BmAieaLqeaQnOe0ouWZqBUBABQrAwFnFgFmZAIDDw8WAh8OBQbliKDpmaQWAh4Hb25jbGljawUXcmV0dXJuIGNvbmZpcm1EZWxDb25kKClkAgEPDxYCHw4FE%2BacgOi%2FkeaQnOe0ouadoeS7tjpkZAICDw8WBB4HVG9vbFRpcAUa5LiK5rW3K%2BehleWjq%2BS7peS4iivnrpfms5UfDgUa5LiK5rW3K%2BehleWjq%2BS7peS4iivnrpfms5VkZAIDDw8WAh8OBQbmn6Xor6IWAh8PBRZyZXR1cm4gRGlmZmVyRnJvbVRvKCk7ZAIEDw8WAh8OBRjkv67mlLnmm7TlpJrmn6Xor6LmnaHku7ZkZAIFD2QWAmYPZBYCAgEPEA8WBh4NRGF0YVRleHRGaWVsZAUETkFNRR4ORGF0YVZhbHVlRmllbGQFBENPSUQeC18hRGF0YUJvdW5kZ2QQFQIG5LiN6ZmQEumYv%2BmHjOW3tOW3tOmbhuWbohUCAAcxOTkxNzQ5FCsDAmdnFgFmZAIGD2QWAmYPZBYCAgEPEGQQFQEG5LiN6ZmQFQEAFCsDAWdkZAIHD2QWAmYPZBYCAgMPZBYKAgcPZBYEZg8PFgQfDgUN6YCJ5oupL%2BS%2FruaUuR8QBQ3pgInmi6kv5L%2Bu5pS5ZGQCAQ8WAh4FVmFsdWUFDemAieaLqS%2Fkv67mlLlkAg8PEA9kFgIeCG9uQ2hhbmdlBUFyZXR1cm4gaXNNQkEoJ2N0cmxTZXJhY2hfVG9wRGVncmVlRnJvbScsJ2N0cmxTZXJhY2hfVG9wRGVncmVlVG8nKWRkZAIUD2QWBGYPDxYEHw4FDemAieaLqS%2Fkv67mlLkfEAUN6YCJ5oupL%2BS%2FruaUuWRkAgEPFgIfFAUN6YCJ5oupL%2BS%2FruaUuWQCFw9kFgRmDw8WBB8OBQ3pgInmi6kv5L%2Bu5pS5HxAFDemAieaLqS%2Fkv67mlLlkZAIBDxYCHxQFDemAieaLqS%2Fkv67mlLlkAiEPZBYEZg8PFgQfDgUN6YCJ5oupL%2BS%2FruaUuR8QBQ3pgInmi6kv5L%2Bu5pS5ZGQCAQ8WAh8UBQ3pgInmi6kv5L%2Bu5pS5ZAIIDw8WAh8OBQbmn6Xor6JkZAILDw8WAh4JTWF4TGVuZ3RoAgpkZAIND2QWAmYPZBYEAgIPDxYCHw4FDO%2B8u%2BehruWumu%2B8vWRkAgQPEGRkFgBkAg4PZBYCZg9kFgRmDw8WAh8OBRLor7fpgInmi6nmlbDmja7vvIFkZAIBDw8WAh8OBQzvvLvnoa7lrprvvL1kZAIPD2QWAmYPZBYGAgEPDxYCHw4FDO%2B8u%2BehruWumu%2B8vWRkAgIPDxYCHw4FBuivreiogGRkAgMPEGQQFQID5LiOA%2BaIlhUCATEBMBQrAwJnZ2RkAhAPZBYCZg9kFgICAQ8WBB4FdmFsdWUFBuS%2FneWtmB8PBVBpZighSXNOdWxsU2VhcmNoKCdjdHJsU2VyYWNoX3R4dFNlYXJjaE5hbWUnLCdjdHJsU2VyYWNoX2RkbFNlYXJjaE5hbWUnKSkgcmV0dXJuO2QCEQ9kFgJmD2QWAgICDxYCHxcFBuehruWummQCBQ9kFgoCAQ8WAh4MVXNlckJ0bldpZHRoGwAAAAAAgEpAAQAAAGQCAw8WAh8YGwAAAAAAgEpAAQAAAGQCBQ8WAh8YGwAAAAAAQFBAAQAAAGQCBw8WAh8YGwAAAAAAQFNAAQAAAGQCCQ8WAh4Fc3R5bGUFFGZsb2F0OmxlZnQ7ZGlzcGxheTo7FgICAQ8WAh8YGwAAAAAAQFRAAQAAAGQCBg8WAh8ZBQ1kaXNwbGF5Om5vbmU7ZAIHDxYCHxkFDmRpc3BsYXk6YmxvY2s7FgoCAQ8QZGQWAQIEZAICDw8WCB4ESXNFTmgeClBQYWdlSW5kZXgCAR8AAjIfBgKgjQZkFgZmDw8WBB4IQ3NzQ2xhc3MFEWN0cmxQYWdpbmF0aW9uQnQwHgRfIVNCAgJkZAIBDw8WBB8cBRFjdHJsUGFnaW5hdGlvbkJ0MB8dAgJkZAICDw8WBB8cBRFjdHJsUGFnaW5hdGlvbkJ0MR8dAgJkZAIDDw8WAh4ISW1hZ2VVcmwFTWh0dHA6Ly9pbWcwMS41MWpvYmNkbi5jb20vaW1laGlyZS9laGlyZTIwMDcvZGVmYXVsdC9pbWFnZS9pbmJveC9saXN0X292ZXIuZ2lmZGQCBA8PFgIfHgVOaHR0cDovL2ltZzAxLjUxam9iY2RuLmNvbS9pbWVoaXJlL2VoaXJlMjAwNy9kZWZhdWx0L2ltYWdlL2luYm94L2RldGFpbF9vdXQuZ2lmZGQCBw8PFggfGmgfGwIBHwACMh8GAqCNBmQWDgIBDw8WCh8OBQMgMSAeD0NvbW1hbmRBcmd1bWVudAUBMR8QBQExHxwFEWN0cmxQYWdpbmF0aW9uQnQwHx0CAmRkAgIPDxYKHw4FAyAyIB8fBQEyHxAFATIfHAURY3RybFBhZ2luYXRpb25CdDEfHQICZGQCAw8PFgofDgUDIDMgHx8FATMfEAUBMx8cBRFjdHJsUGFnaW5hdGlvbkJ0MB8dAgJkZAIEDw8WCh8OBQMgNCAfHwUBNB8QBQE0HxwFEWN0cmxQYWdpbmF0aW9uQnQwHx0CAmRkAgUPDxYKHw4FAyA1IB8fBQE1HxAFATUfHAURY3RybFBhZ2luYXRpb25CdDAfHQICZGQCBg8PFgIfDmVkZAIHDw8WAh8OBQMuLi5kZAIID2QWAmYPZBYMAg0PFgIfGBsAAAAAAIBKQAEAAABkAg8PFgIfGBsAAAAAAIBFQAEAAABkAhEPFgIfGBsAAAAAAIBMQAEAAABkAhMPFgIfGBsAAAAAAIBMQAEAAABkAhUPFgIfGBsAAAAAAAA%2FQAEAAABkAhcPFgIfGBsAAAAAAEBQQAEAAABkAgkPEGQQFRQG5qCH562%2BBuaAp%2BWIqwblubTpvoQJ5bGF5L2P5ZywBuWtpuWOhgzlt6XkvZzlubTpmZAG6IGM6IO9BuS4k%2BS4mgbmiLflj6MG6KGM5LiaDOebruWJjeaciOiWqgzmnJ%2FmnJvmnIjolqoM6IGU57O755S16K%2BdCeWtpuagoeWQjQlJVOaKgOiDvTEJSVTmioDog70yB%2BivreiogDEM566A5Y6G5p2l5rqQEueugOWOhuabtOaWsOaXtumXtAzmipXpgJLml7bpl7QVFAdMQUJFTElEA1NFWANBR0UEQVJFQQlUT1BERUdSRUUIV09SS1lFQVIIV09SS0ZVTjEIVE9QTUFKT1IFSFVLT1UNV09SS0lORFVTVFJZMQ1DVVJSRU5UU0FMQVJZDEVYUEVDVFNBTEFSWQpNT0JJRVBIT05FCVRPUFNDSE9PTAdJVFRZUEUxB0lUVFlQRTIDRkwxBlNPVVJDRQ5MQVNUTU9ESUZZREFURQhTRU5EREFURRQrAxRnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2RkAgsPEA8WBh8RBQVWQUxVRR8SBQRDT0RFHxNnZBAVCQ0tLeivt%2BmAieaLqS0tCeWMuemFjeW6pgblp5PlkI0M6IGM5L2N5ZCN56ewCeWxheS9j%2BWcsAblrabljoYM5bel5L2c5bm06ZmQBuiBjOiDvQzmipXpgJLml7bpl7QVCQANTUFUQ0hJTkdfQUxMMQVDTkFNRQdKT0JOQU1FBEFSRUEJVE9QREVHUkVFCFdPUktZRUFSCFdPUktGVU4xCFNFTkREQVRFFCsDCWdnZ2dnZ2dnZ2RkAg4PEA8WBh8RBQVWQUxVRR8SBQRDT0RFHxNnZBAVCQ0tLeivt%2BmAieaLqS0tCeWMuemFjeW6pgblp5PlkI0M6IGM5L2N5ZCN56ewCeWxheS9j%2BWcsAblrabljoYM5bel5L2c5bm06ZmQBuiBjOiDvQzmipXpgJLml7bpl7QVCQANTUFUQ0hJTkdfQUxMMQVDTkFNRQdKT0JOQU1FBEFSRUEJVE9QREVHUkVFCFdPUktZRUFSCFdPUktGVU4xCFNFTkREQVRFFCsDCWdnZ2dnZ2dnZ2RkAhEPEA8WBh8RBQVWQUxVRR8SBQRDT0RFHxNnZBAVCQ0tLeivt%2BmAieaLqS0tCeWMuemFjeW6pgblp5PlkI0M6IGM5L2N5ZCN56ewCeWxheS9j%2BWcsAblrabljoYM5bel5L2c5bm06ZmQBuiBjOiDvQzmipXpgJLml7bpl7QVCQANTUFUQ0hJTkdfQUxMMQVDTkFNRQdKT0JOQU1FBEFSRUEJVE9QREVHUkVFCFdPUktZRUFSCFdPUktGVU4xCFNFTkREQVRFFCsDCWdnZ2dnZ2dnZ2RkAhYPDxYCHg1PbkNsaWVudENsaWNrBS9qYXZhc2NyaXB0OnJldHVybiBPcGVuSW5ib3hDb21tb25MYXllcignVE9IUicpO2RkAhkPDxYCHyAFMGphdmFzY3JpcHQ6cmV0dXJuIE9wZW5JbmJveENvbW1vbkxheWVyKCdUT0hSUycpO2RkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYyBRRjdHJsU2VyYWNoJGNoa1R3b0FsbAUZY3RybFNlcmFjaCRjaGtUd29TZWxlY3QkMAUWY3RybFNlcmFjaCRjaGtTZWxlY3QkMAUSY3RybFNlcmFjaCRjaGtGTCQwBRZjdHJsU2VyYWNoJGNoa19kZWZhdWx0BQdpbWdEaXMxBQdpbWdEaXMyBQxjYnhDb2x1bW5zJDAFDGNieENvbHVtbnMkMQUMY2J4Q29sdW1ucyQyBQxjYnhDb2x1bW5zJDMFDGNieENvbHVtbnMkNAUMY2J4Q29sdW1ucyQ1BQxjYnhDb2x1bW5zJDYFDGNieENvbHVtbnMkNwUMY2J4Q29sdW1ucyQ4BQxjYnhDb2x1bW5zJDkFDWNieENvbHVtbnMkMTAFDWNieENvbHVtbnMkMTEFDWNieENvbHVtbnMkMTIFDWNieENvbHVtbnMkMTMFDWNieENvbHVtbnMkMTQFDWNieENvbHVtbnMkMTUFDWNieENvbHVtbnMkMTYFDWNieENvbHVtbnMkMTcFDWNieENvbHVtbnMkMTgFDWNieENvbHVtbnMkMTkFDWNieENvbHVtbnMkMTkFCFNvcnRBc2MxBQlTb3J0RGVzYzEFCVNvcnREZXNjMQUIU29ydEFzYzIFCVNvcnREZXNjMgUJU29ydERlc2MyBQhTb3J0QXNjMwUJU29ydERlc2MzBQlTb3J0RGVzYzMFEmN0bEV4cG9ydDEkcmRiV29yZAUSY3RsRXhwb3J0MSRyZGJXb3JkBRNjdGxFeHBvcnQxJHJkYkV4Y2VsBRNjdGxFeHBvcnQxJHJkYkV4Y2VsBRJjdGxFeHBvcnQxJHJkYkh0bWwFEmN0bEV4cG9ydDEkcmRiSHRtbAUUY3RsRXhwb3J0MSRyZGJBY2Nlc3MFFGN0bEV4cG9ydDEkcmRiQWNjZXNzBRZjdGxFeHBvcnQxJHJkYkRvd25sb2FkBRZjdGxFeHBvcnQxJHJkYkRvd25sb2FkBRNjdGxFeHBvcnQxJHJkYkVtYWlsBRNjdGxFeHBvcnQxJHJkYkVtYWlsBQpjaGtJbmNsSGlz&MainMenuNew1%24CurMenuID=MainMenuNew1_imgApp%7Csub3&hidTab=&ctrlSerach%24ddlSearchName=&ctrlSerach%24dropCoid=&ctrlSerach%24dropDivision=&ctrlSerach%24hidSearchID=1%2C2%2C3%2C4%2C5%2C6%2C7%2C8%2C9&ctrlSerach%24KEYWORD=--%E5%8F%AF%E9%80%89%E6%8B%A9%E2%80%9C%E5%B7%A5%E4%BD%9C%E3%80%81%E9%A1%B9%E7%9B%AE%E3%80%81%E8%81%8C%E5%8A%A1%E3%80%81%E5%AD%A6%E6%A0%A1%E2%80%9D%E5%85%B3%E9%94%AE%E5%AD%97--&ctrlSerach%24KEYWORDTYPE=1&ctrlSerach%24AREA%24Text=%E9%80%89%E6%8B%A9%2F%E4%BF%AE%E6%94%B9&ctrlSerach%24AREA%24Value=&ctrlSerach%24SEX=2&ctrlSerach%24TopDegreeFrom=&ctrlSerach%24TopDegreeTo=&ctrlSerach%24WORKFUN1%24Text=%E9%80%89%E6%8B%A9%2F%E4%BF%AE%E6%94%B9&ctrlSerach%24WORKFUN1%24Value=&ctrlSerach%24WORKINDUSTRY1%24Text=%E9%80%89%E6%8B%A9%2F%E4%BF%AE%E6%94%B9&ctrlSerach%24WORKINDUSTRY1%24Value=&ctrlSerach%24WorkYearFrom=0&ctrlSerach%24WorkYearTo=99&ctrlSerach%24TOPMAJOR%24Text=%E9%80%89%E6%8B%A9%2F%E4%BF%AE%E6%94%B9&ctrlSerach%24TOPMAJOR%24Value=&ctrlSerach%24AgeFrom=&ctrlSerach%24AgeTo=&ctrlSerach%24txtUserID=-%E5%A4%9A%E4%B8%AA%E7%AE%80%E5%8E%86ID%E7%94%A8%E7%A9%BA%E6%A0%BC%E9%9A%94%E5%BC%80-&ctrlSerach%24txtMobile=&ctrlSerach%24txtName=&ctrlSerach%24txtMail=&ctrlSerach%24hidTwoValue=&ctrlSerach%24radFandF=0&ctrlSerach%24txtSearchName=&dropRecentResumeRange=180&pagerBottom%24btnNum1=+1+&pagerBottom%24txtGO=2&cbxColumns%243=AREA&cbxColumns%244=TOPDEGREE&cbxColumns%245=WORKYEAR&cbxColumns%246=WORKFUN1&cbxColumns%2419=SENDDATE&Keyword1=&1=SortAsc1&Keyword2=&2=SortAsc2&Keyword3=&3=SortAsc3&inboxTypeFlag=5&ShowFrom=1&hidEvents=&hidSeqID=&hidUserID=&hidFolder=EMP&BAK2INT=&hidJobID=&SubmitValue=&hidDisplayType=0&hidOrderByCol=&hidSearchHidden=&hidBatchBtn=&hidProcessType=&hidEngineCvlogIds=7835430274%7C0%7C1%2C7835425477%7C0%7C2%2C7835425010%7C0%7C3%2C7835424645%7C0%7C4%2C7835423003%7C0%7C5%2C7835418744%7C0%7C6%2C7835410311%7C0%7C7%2C7835402986%7C0%7C8%2C7835401711%7C0%7C9%2C7835399726%7C0%7C10%2C7835398713%7C0%7C11%2C7835398582%7C0%7C12%2C7835395319%7C0%7C13%2C7835384773%7C0%7C14%2C7835382539%7C0%7C15%2C7835382361%7C0%7C16%2C7835369129%7C0%7C17%2C7835363746%7C0%7C18%2C7835362574%7C0%7C19%2C7835359889%7C0%7C20%2C7835358489%7C0%7C21%2C7835348298%7C0%7C22%2C7835347782%7C0%7C23%2C7835346174%7C0%7C24%2C7835343090%7C0%7C25%2C7835338600%7C0%7C26%2C7835337097%7C0%7C27%2C7835335934%7C0%7C28%2C7835328748%7C0%7C29%2C7835328168%7C0%7C30%2C7835327729%7C0%7C31%2C7835324383%7C0%7C32%2C7835323080%7C0%7C33%2C7835322506%7C0%7C34%2C7835319882%7C0%7C35%2C7835317235%7C0%7C36%2C7835314006%7C0%7C37%2C7835313081%7C0%7C38%2C7835312896%7C0%7C39%2C7835310983%7C0%7C40%2C7835310457%7C0%7C41%2C7835309524%7C0%7C42%2C7835305490%7C0%7C43%2C7835305451%7C0%7C44%2C7835302587%7C0%7C45%2C7835302116%7C0%7C46%2C7835301022%7C0%7C47%2C7835299237%7C0%7C48%2C7835297117%7C0%7C49%2C7835294671%7C0%7C50%2C7835291057%7C0%7C51%2C7835288121%7C0%7C52%2C7835288028%7C0%7C53%2C7835286593%7C0%7C54%2C7835282685%7C0%7C55%2C7835281870%7C0%7C56%2C7835275040%7C0%7C57%2C7835265588%7C0%7C58%2C7835265117%7C0%7C59%2C7835263090%7C0%7C60%2C7835262921%7C0%7C61%2C7835247132%7C0%7C62%2C7835245239%7C0%7C63%2C7835242824%7C0%7C64%2C7835240795%7C0%7C65%2C7835236802%7C0%7C66%2C7835234605%7C0%7C67%2C7835227443%7C0%7C68%2C7835227081%7C0%7C69%2C7835226001%7C0%7C70%2C7835225417%7C0%7C71%2C7835212212%7C0%7C72%2C7835210409%7C0%7C73%2C7835197919%7C0%7C74%2C7835186313%7C0%7C75%2C7835175136%7C0%7C76%2C7835173199%7C0%7C77%2C7835172154%7C0%7C78%2C7835166947%7C0%7C79%2C7835165193%7C0%7C80%2C7835159115%7C0%7C81%2C7835158251%7C0%7C82%2C7835154344%7C0%7C83%2C7835151968%7C0%7C84%2C7835151349%7C0%7C85%2C7835148944%7C0%7C86%2C7835147585%7C0%7C87%2C7835143898%7C0%7C88%2C7835143429%7C0%7C89%2C7835143203%7C0%7C90%2C7835139807%7C0%7C91%2C7835136815%7C0%7C92%2C7835136357%7C0%7C93%2C7835135937%7C0%7C94%2C7835133283%7C0%7C95%2C7835131243%7C0%7C96%2C7835125624%7C0%7C97%2C7835123485%7C0%7C98%2C7835123042%7C0%7C99%2C7835121188%7C0%7C100&hidUserDistinct=0");
//            List<CrawlerCookie> zhaopin_cookie=new  CookieHelper().readcookiefromfile(new File(UumaiProperties.getUUmaiHome() + "/cookies/cloudinfotech_51job_cookies.txt"));
//            tasker.setCookies(zhaopin_cookie);
            tasker.setUrl(url);

            tasker.setTaskerOwner("rock");

             tasker.setTaskerName("uumai_quartz_cmbbnc_test");

//            tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));
            String html=download.download(tasker).getRawText();
            System.out.print("html:"+html);


        }
}
