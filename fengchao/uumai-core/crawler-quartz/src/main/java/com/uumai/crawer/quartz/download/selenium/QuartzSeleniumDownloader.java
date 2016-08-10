package com.uumai.crawer.quartz.download.selenium;


import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.CookieManager.CookieHelper;
import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawer2.download.CrawlerProxy;
import com.uumai.crawer2.download.Download;
import com.uumai.crawer2.download.selenium.WebDriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class QuartzSeleniumDownloader implements Download {

	public QuartzSeleniumDownloader() {

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
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            if(tasker.getCookies()!=null){
    //             split cookie string
                URL url = new URL(tasker.getUrl());
//                webDriver.get("about:blank");
                webDriver.get(url.getProtocol() + "://" + url.getHost());
                 manage.deleteAllCookies();
                for(CrawlerCookie crawlerCookie:tasker.getCookies()){
                    Cookie cookie = new Cookie(crawlerCookie.getName(),crawlerCookie.getValue(),null, //crawlerCookie.getDomain(),
                            crawlerCookie.getPath(),crawlerCookie.getExpiry(),crawlerCookie.isSecure(),crawlerCookie.isHttpOnly() );
                    manage.addCookie(cookie);
                }

            }
           
                webDriver.get(tasker.getUrl());
               

                QuartzCrawlerTasker quartzCrawlerTasker=(QuartzCrawlerTasker)tasker;

            //do actions
            if(quartzCrawlerTasker.getSeleniumActionses()!=null){
                new SeleniumActionBot(webDriver,quartzCrawlerTasker.getSeleniumActionses()).doactions();
            }
            
            
            
            
            // Google's search is rendered dynamically with JavaScript.
            // Wait for the page to load, timeout after 10 seconds
//            (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
//                public Boolean apply(WebDriver d) {
//                	WebElement TxtBoxContent =d.findElement(By.id("J_flightlist2"));
//                    String text=TxtBoxContent.getText();
////                    System.out.println("text:"+text);
//                    if(text==null) return false;
//                    text=text.trim();
//                    if("".equals(text)){
//                    	return false;
//                    }else{
//                    	return true;
//                    }
//                }
//            });
            
//            JavascriptExecutor jse = (JavascriptExecutor)webDriver;
            
//            Object val = jse.executeScript("return window.jsonCallback.data;");
//            System.out.println("data:"+val);
//            for(int i=0;i<30;i++){
//            jse.executeScript("window.scrollBy(0,250)", "");
//            }



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
//        UumaiProperties.init("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/crawler-example/deploy/resources/uumai.properties");

        QuartzSeleniumDownloader downloader=new QuartzSeleniumDownloader();
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();

        tasker.setDownloadType(DownloadType.firefox_download);
        tasker.setUrl("http://et.airchina.com.cn/InternetBooking/AirLowFareSearchExternal.do?&tripType=OW&searchType=FARE&flexibleSearch=false&directFlightsOnly=false&fareOptions=1.FAR.X&outboundOption.originLocationCode=PEK&outboundOption.destinationLocationCode=PVG&outboundOption.departureDay=25&outboundOption.departureMonth=12&outboundOption.departureYear=2015&outboundOption.departureTime=NA&guestTypes%5B0%5D.type=ADT&guestTypes%5B0%5D.amount=1&guestTypes%5B1%5D.type=CNN&guestTypes%5B1%5D.amount=0&pos=AIRCHINA_CN&lang=zh_CN&guestTypes%5B2%5D.type=INF&guestTypes%5B2%5D.amount=0");
        tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));
        tasker.addSeleniumAction("sleep", null, "60000");
//    	tasker.addSeleniumAction("sendKeys", "id=0", "北京首都国际机场");
//		tasker.addSeleniumAction("sendKeys", "id=1", "上海浦东机场");
//		tasker.addSeleniumAction("sendKeys", "id=deptDateShowGo", "2015-12-25");
//		tasker.addSeleniumAction("click", "id=portalBtn", null);
//		
//        tasker.setCookies(CookieUtil.loadCookie("amazon"));
        String html=downloader.download(tasker).getRawText();
            System.out.println("html:"+ html);

    }
}