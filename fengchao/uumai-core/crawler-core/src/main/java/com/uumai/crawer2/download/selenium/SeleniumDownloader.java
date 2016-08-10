package com.uumai.crawer2.download.selenium;

import com.uumai.crawer2.CookieManager.CookieHelper;
import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.CrawlerProxy;
import com.uumai.crawer2.download.Download;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class SeleniumDownloader implements Download {

	public SeleniumDownloader() {

	}


	@Override
    public CrawlerResult download(CrawlerTasker tasker) throws Exception {

   // public Page download(Request request, Task task) {

		WebDriver webDriver=null;

        try {
            CrawlerProxy proxy=tasker.getProxy();
            if(proxy==null){
                webDriver = WebDriverFactory.getDriver(tasker.getDownloadType(), null);
            }else{
                webDriver = WebDriverFactory.getDriver(tasker.getDownloadType(), proxy.getProxyIpAndPortString());
            }

            WebDriver.Options manage = webDriver.manage();

            if(tasker.getCookies()!=null){
    //             split cookie string
                URL url = new URL(tasker.getUrl());
//                webDriver.get("about:blank");
                webDriver.get(url.getProtocol() + "://" + url.getHost());
                webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                manage.deleteAllCookies();
                for(CrawlerCookie crawlerCookie:tasker.getCookies()){
                    Cookie cookie = new Cookie(crawlerCookie.getName(),crawlerCookie.getValue(),null, //crawlerCookie.getDomain(),
                            crawlerCookie.getPath(),crawlerCookie.getExpiry(),crawlerCookie.isSecure(),crawlerCookie.isHttpOnly() );
                    manage.addCookie(cookie);
                }

            }


            if(tasker.getSeleniumScriptBase()!=null){
                SeleniumScriptBase seleniumScriptBase=tasker.getSeleniumScriptBase();
                seleniumScriptBase.setDriver((UumaiSeleniumWebDriver)webDriver);
                seleniumScriptBase.doaction();
            }else{
                webDriver.get(tasker.getUrl());
                webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }

//            if(webDriver instanceof PhantomJSDriver) {
//                PhantomJSDriver phantomDriver=(PhantomJSDriver)webDriver;
//                boolean enabled = phantomDriver.getCapabilities().isJavascriptEnabled();
//                System.out.println("enabled:"+enabled);
//            }



            //do actions



//                (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver d) {
//                        return d.getTitle()!=null;
//                    }
//                });
//            try {
//                Thread.sleep(WebDriverFactory.sleepTime);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            CrawlerResult crawlerResult=new CrawlerResult();

            CookieHelper cookieHelper=new CookieHelper();
            crawlerResult.setCookies(cookieHelper.parseCookies(webDriver));

            WebElement webElement = webDriver.findElement(By.xpath("/html"));
            String content = webElement.getAttribute("innerHTML");
            crawlerResult.setRawText(content);

            crawlerResult.setReturncode(200);
            return crawlerResult;

        } catch(Exception ex){
           ex.printStackTrace();
            throw  ex;
        }  finally {
            try {
//                webDriver.close();
                webDriver.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }



    public static void main(String[] args) throws  Exception{

        SeleniumDownloader downloader=new SeleniumDownloader();
        CrawlerTasker tasker=new CrawlerTasker();
        tasker.setDownloadType(DownloadType.firefox_download);
        tasker.setUrl("http://item.jd.com/1644261435.html");
        tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));
        CookieHelper cookieHelper=new CookieHelper();

        List<CrawlerCookie> cookies =cookieHelper.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/jd_cookies.txt"));

        tasker.setCookies(cookies);
        String html=downloader.download(tasker).getRawText();
            System.out.println("html:"+ html);

    }
}
