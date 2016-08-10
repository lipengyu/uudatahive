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
import com.uumai.crawer2.download.Download.DownloadType;

public class JipiaoTasker extends QuartzLocalDebugAppMaster { //QuartzLocalDebugAppMaster{
 
	@Override
	public void dobusiness() throws Exception {
 		String datatime="2016-01-25";
		checkflight("SHA","CAN","2016-01-25");
		
 
		

	}
	private void checkflight(String flight_name,String flight,String DDate1) throws Exception{
		String[] s=flight.replace("http://flights.ctrip.com/schedule/", "").replace(".html", "").split("\\.");
		if(s==null||s.length!=2) return;
		createonetask(flight_name,s[0].toUpperCase(),s[1].toUpperCase(),DDate1);
		
	}
	
 
	
	public void createonetask(String flight_name,String DCity1,String ACity1,String DDate1) throws Exception{
 		
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		String url="http://flights.ctrip.com/domesticsearch/search/SearchFirstRouteFlights?DCity1="+DCity1+"&ACity1="+ACity1+"&SearchType=S&DDate1="+DDate1+"&IsNearAirportRecommond=0";
		
          tasker.setProxy(new CrawlerProxy("cn-proxy.sg.oracle.com", 80));

//		tasker.setDownloadType(DownloadType.firefox_download);
		tasker.setUrl(url);
		tasker.addRequestHeader("Referer", "http://flights.ctrip.com/booking/SHA-BJS1-day-1.html");
//		tasker.addSeleniumAction("sendKeys", "id=DCityName1", "上海(SHA)");
//		tasker.addSeleniumAction("sendKeys", "id=ACityName1", "北京(BJS)");
//		tasker.addSeleniumAction("sendKeys", "id=DDate1", "2015-12-20");
//		tasker.addSeleniumAction("click", "id=btnReSearch", null);
		tasker.addResultItem("url",url);
		tasker.addResultItem("flight_name",flight_name);
		tasker.addResultItem("flight_time",DDate1);
 		
//		tasker.setStoreTableName("ctrip_jipiao");
//		tasker.addXpath_all("flight", "//div[@class='info-flight']/allText()");
		tasker.addJsonpath("json", "*");
		
		putDistributeTask(tasker);
	}

	public static void main(String[] args)throws Exception{
	
		JipiaoTasker master = new JipiaoTasker();
 			master.init();
	 
				master.start();
		 
		}
	

}
