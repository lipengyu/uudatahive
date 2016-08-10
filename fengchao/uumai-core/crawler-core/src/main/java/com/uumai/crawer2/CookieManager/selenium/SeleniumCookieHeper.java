package com.uumai.crawer2.CookieManager.selenium;

import com.uumai.crawer2.CookieManager.CookieHelper;
import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawer2.download.CrawlerProxy;
import com.uumai.crawer2.download.Download;
import com.uumai.crawer2.download.selenium.WebDriverFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by rock on 12/2/15.
 */
public class SeleniumCookieHeper {

    private Download.DownloadType type;
    private CrawlerProxy proxy;

    public  SeleniumCookieHeper(Download.DownloadType type,CrawlerProxy proxy){
        this.type=type;
        this.proxy=proxy;
    }
    public List<CrawlerCookie> getcookies(String url) throws Exception {
        List<String> urllist=new ArrayList<String>();
        urllist.add(url);
        return this.getcookies(urllist);
    }

    public List<CrawlerCookie> getcookies(List<String> urllist) throws Exception {
         // public Page download(Request request, Task task) {

        WebDriver webDriver=null;

        try {
            if(this.proxy==null){
                webDriver = WebDriverFactory.getDriver(this.type, null);
            }else{
                webDriver = WebDriverFactory.getDriver(this.type, this.proxy.getProxyIpAndPortString());
            }

            WebDriver.Options manage = webDriver.manage();

//            if(tasker.getCookies()!=null){
//                //             split cookie string
//                URL url = new URL(tasker.getUrl());
//                webDriver.get(url.getProtocol() + "://" + url.getHost());
////                webDriver.get("about:blank");
//                for(String cookiesStr:tasker.getCookies().split(";")){
//                    String[] cookieStr=cookiesStr.split("=");
//                    Cookie cookie = new Cookie(cookieStr[0],cookieStr[1]);
//                    manage.addCookie(cookie);
//                }
//
//            }


            if(urllist!=null) {
                for (String url : urllist) {
                    webDriver.get(url);
                    webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                }
            }
            // Wait for the page to load, timeout after 10 seconds
//            (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
//                public Boolean apply(WebDriver d) {
//                    return d.getTitle().toLowerCase().startsWith("cheese!");
//                }
//            });

                return  new CookieHelper().parseCookies(webDriver);

        } catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }  finally {
            try {
                webDriver.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    public static void main(String[] args) throws  Exception{

        SeleniumCookieHeper downloader=new SeleniumCookieHeper( Download.DownloadType.firefox_download,new CrawlerProxy("cn-proxy.jp.oracle.com", 80));
  //        String html=downloader.getcookies(urlist, Download.DownloadType.chrome_download,);
        List<String> urllist=new ArrayList<String>();
        urllist.add("http://www.amazon.com");
//        urllist.add("http://xueqiu.com/1130548918");

        List<CrawlerCookie> cookie=downloader.getcookies(urllist);
        for(CrawlerCookie c:cookie) {
            System.out.println("cookie:" + c);
        }

    }
}
