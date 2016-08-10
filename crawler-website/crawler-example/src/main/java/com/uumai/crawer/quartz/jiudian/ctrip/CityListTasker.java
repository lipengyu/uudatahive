package com.uumai.crawer.quartz.jiudian.ctrip;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.util.UumaiTime;

public class CityListTasker  extends QuartzLocalDebugAppMaster { //QuartzLocalDebugAppMaster{
	@Override
	public void dobusiness() throws Exception {
 		createonetask("http://hotels.ctrip.com/domestic-city-hotel.html");
		

	}
	
	public void createonetask(String url) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
 		
 //        tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));

//		tasker.setDownloadType(DownloadType.firefox_download);
		tasker.setUrl(url);
//		tasker.addRequestHeader("Referer", "http://hotels.ctrip.com/hotel/beijing2");
//		tasker.setRequestmethod("POST");
		
//		tasker.addSeleniumAction("sendKeys", "id=DCityName1", "上海(SHA)");
//		tasker.addSeleniumAction("sendKeys", "id=ACityName1", "北京(BJS)");
//		tasker.addSeleniumAction("sendKeys", "id=DDate1", "2015-12-20");
//		tasker.addSeleniumAction("click", "id=btnReSearch", null);
 
 		
//		tasker.setStoreTableName("ctrip_jiudian_city");
		tasker.addXpath_all("hotel_name", "//dl[@class='pinyin_filter_detail layoutfix']//a/text()");
		tasker.addXpath_all("hotel_link", "//dl[@class='pinyin_filter_detail layoutfix']//a/@href");
 		
		putDistributeTask(tasker);
	}

	public static void main(String[] args) throws Exception{
	
		CityListTasker master = new CityListTasker();
 			master.init();
 	
				master.start();
			 
		}


}
