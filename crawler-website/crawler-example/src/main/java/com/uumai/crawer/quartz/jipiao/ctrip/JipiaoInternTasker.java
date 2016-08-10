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

public class JipiaoInternTasker extends QuartzLocalDebugAppMaster { //QuartzLocalDebugAppMaster{
 
	@Override
	public void dobusiness() throws Exception {
 		String datatime="2016-01-25";
		checkflight("SHA","CAN","2016-01-25");
 
	}
	private void checkflight(String flight_name,String flight,String DDate1) throws Exception{
		String[] s=flight.replace("http://flights.ctrip.com/international/Schedule/", "").replace(".html", "").split("-");
		if(s==null||s.length!=2) return;
		createonetask(flight_name,s[0].toUpperCase(),s[1].toUpperCase(),DDate1);
		
	}
	
 
	
	public void createonetask(String flight_name,String DCity1,String ACity1,String DDate1) throws Exception{
 		
		//get key from http://flights.ctrip.com/international/abidjan-athens-abj-ath
//		condition = '{"FlightWay":"S","SegmentList":[{"DCityCode":"ABJ","ACityCode":"ATH","DCity":"Abidjan
//				|阿比让(ABJ)|3265|ABJ|0","ACity":"Athens|雅典(ATH)|710|ATH|120","DepartDate":"2016-2-1"}],"TransferCityID"
//				:0,"Quantity":1,"TransNo":"5416012514000068431","SearchRandomKey":"","IsAsync":1,"RecommendedFlightSwitch"
//				:1,"SearchKey":"E3E74DC48EB3A93C37899952BBEECD9BD5E6F8CADDEFB24680A2D524D385542BA0FE01DF8D3B0B50","MultiPriceUnitSwitch"
//				:1,"TransferCitySwitch":false,"EngineFlightFltNo":"D","EngineFlightRT":"C","SearchStrategySwitch":1,"MaxSearchCount"
//				:3,"RowNum":"1500"}';
	
	
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		String url="http://flights.ctrip.com/international/AjaxRequest/SearchFlights/AsyncSearchResultHandler.ashx";
		
         tasker.setProxy(new CrawlerProxy("cn-proxy.sg.oracle.com", 80));

//		tasker.setDownloadType(DownloadType.firefox_download);
		tasker.setUrl(url);
		tasker.setRequestmethod("POST");
		tasker.setPostdata("SearchMode=TokenSearch&condition=%7B%22FlightWay%22%3A%22S%22%2C%22SegmentList%22%3A%5B%7B%22DCityCode%22%3A%22"+DCity1+"%22%2C%22ACityCode%22%3A%22"+ACity1+"%22%2C%22DCity%22%3A%22Shanghai%7C%E4%B8%8A%E6%B5%B7(SHA)%7C2%7CSHA%7C480%22%2C%22ACity%22%3A%22Hong%20Kong%7C%E9%A6%99%E6%B8%AF(HKG)%7C58%7CHKG%7C480%22%2C%22DepartDate%22%3A%22"+DDate1+"%22%7D%5D%2C%22TransferCityID%22%3A0%2C%22Quantity%22%3A1%2C%22TransNo%22%3A%225116012514000047145%22%2C%22SearchRandomKey%22%3A%22%22%2C%22IsAsync%22%3A1%2C%22RecommendedFlightSwitch%22%3A1%2C%22SearchKey%22%3A%227E2DC7E0978CBEDF808058625142017375B4C84536555E0D84A336B726ECE9D5D05D4346AA03923A%22%2C%22MultiPriceUnitSwitch%22%3A1%2C%22TransferCitySwitch%22%3Afalse%2C%22EngineFlightFltNo%22%3A%22D%22%2C%22EngineFlightRT%22%3A%22C%22%2C%22SearchStrategySwitch%22%3A1%2C%22MaxSearchCount%22%3A3%2C%22RowNum%22%3A%221500%22%7D&SearchToken=2");
		tasker.addRequestHeader("Referer", "http://flights.ctrip.com/international/shanghai-hongkong-sha-hkg");
 
		tasker.addResultItem("url",url);
		tasker.addResultItem("flight_name",flight_name);
		tasker.addResultItem("flight_time",DDate1);
 		
//		tasker.setStoreTableName("ctrip_jipiao_inter");
//		tasker.addXpath_all("flight", "//div[@class='info-flight']/allText()");
		tasker.addJsonpath("json", "*");
		
		putDistributeTask(tasker);
	}

	public static void main(String[] args) throws Exception{
	
		JipiaoInternTasker master = new JipiaoInternTasker();
 			master.init();
 	
				master.start();
 
		}
	

}
