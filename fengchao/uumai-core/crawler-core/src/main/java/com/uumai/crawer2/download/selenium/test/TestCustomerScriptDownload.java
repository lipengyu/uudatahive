package com.uumai.crawer2.download.selenium.test;

import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.CrawlerProxy;
import com.uumai.crawer2.download.Download;
import com.uumai.crawer2.download.selenium.SeleniumDownloader;

/**
 * Created by rock on 12/9/15.
 */
public class TestCustomerScriptDownload {

    public static void main(String[] args) throws  Exception{
//        UumaiProperties.init("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/crawler-example/deploy/resources/uumai.properties");

        SeleniumDownloader downloader=new SeleniumDownloader();
        CrawlerTasker tasker=new CrawlerTasker();
        tasker.setDownloadType(Download.DownloadType.chrome_download);
        tasker.setUrl("https://www.igola.com/flights/ZH/bjs-hkg_2015-12-09*2016-01-06_1*RT*Economy_0");
        tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));
        tasker.setSeleniumScriptBase(new TestSeleniumScriptBase());
//        tasker.setCookies(CookieUtil.loadCookie("amazon"));
        String html=downloader.download(tasker).getRawText();
        System.out.println("html:"+ html);

    }
}
