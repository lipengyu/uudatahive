package com.uumai.crawer.quartz.jipiao.ctrip;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.util.QuartzQueryItem;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.CrawlerProxy;

public class JipiaoListTasker  extends QuartzLocalDebugAppMaster { //QuartzLocalDebugAppMaster{
 
	@Override
	public void dobusiness() throws Exception {
 		createonetask("http://flights.ctrip.com/schedule","ROOT");
  
		

		
	}
 
	
	public void createonetask(String url,String from) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
 		
 //        tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));

//		tasker.setDownloadType(DownloadType.firefox_download);
		tasker.setUrl(url);
//		tasker.addRequestHeader("Referer", "http://flights.ctrip.com/booking/SHA-BJS1-day-1.html");
//		tasker.addSeleniumAction("sendKeys", "id=DCityName1", "上海(SHA)");
//		tasker.addSeleniumAction("sendKeys", "id=ACityName1", "北京(BJS)");
//		tasker.addSeleniumAction("sendKeys", "id=DDate1", "2015-12-20");
//		tasker.addSeleniumAction("click", "id=btnReSearch", null);
		tasker.addResultItem("from",from);
 		
//		tasker.setStoreTableName("ctrip_jipiaolist");
		tasker.addXpath_all("flight_name", "//div[@class='m']/a/text()");
		tasker.addXpath_all("flight", "//div[@class='m']/a/@href");
 		
		putDistributeTask(tasker);
	}

	public static void main(String[] args) throws Exception{
	
		JipiaoListTasker master = new JipiaoListTasker();
 			master.init();
 	 
				master.start();
		 
		}

}
