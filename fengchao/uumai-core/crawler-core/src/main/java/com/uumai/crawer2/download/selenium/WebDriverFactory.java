package com.uumai.crawer2.download.selenium;

import com.uumai.crawer.util.UumaiProperties;
import com.uumai.crawer2.download.Download;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by rock on 8/18/15.
 */
public class WebDriverFactory {

    public  static int sleepTime=1000;

    static{
//        System.getProperties().setProperty("webdriver.chrome.driver",
//                 +   UumaiProperties.readconfig("webdriver.chrome.driver", "/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/driver/chromedriver"));

        System.getProperties().setProperty("webdriver.chrome.driver",
                UumaiProperties.getUUmaiHome() + "/driver/chromedriver");

        System.getProperties().setProperty("phantomjs.binary.path",
                UumaiProperties.getUUmaiHome() + "/driver/phantomjs");
    }


    public static synchronized  WebDriver getDriver(Download.DownloadType type,String proxyIpAndPort){
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        if(proxyIpAndPort!=null){
            // Add the WebDriver proxy capability.
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyIpAndPort)
                    .setFtpProxy(proxyIpAndPort)
                    .setSslProxy(proxyIpAndPort);
            capabilities.setCapability(CapabilityType.PROXY, proxy);
            // 以下三行是为了避免localhost和selenium driver的也使用代理，务必要加，否则无法与iedriver通讯
            capabilities.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
            System.setProperty("http.nonProxyHosts", "localhost");

        }



        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");
//        options.addArguments("--no-startup-window");
//        options.addArguments("silent-launch");

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setJavascriptEnabled(true);

        WebDriver e=null;

        if(type== Download.DownloadType.firefox_download){
            e = new FirefoxDriver(capabilities);
        }else if(type== Download.DownloadType.chrome_download){
            e = new ChromeDriver(capabilities);
        }else if(type== Download.DownloadType.htmlunit_download){
            e = new HtmlUnitDriver(capabilities);
        }else  {  //if(type== Download.DownloadType.phantomjs_download){
            e = new PhantomJSDriver(capabilities);
        }

        return e;
    }
}
