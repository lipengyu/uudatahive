package com.uumai.crawer.quartz.jipiao.chunqiu;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.result.QuartzResult;
import com.uumai.crawer.quartz.util.QuartzQueryItem;
 import com.uumai.crawer.util.JodaTime;
import com.uumai.crawer.util.UumaiTime;
 
public class ChunqiuFlight extends QuartzLocalDebugAppMaster {
 
    static  DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");//自定义日期格式

 
@Override
public void dobusiness()  throws Exception{
 
   
 
		String flight="大连 上海";
		if(flight!=null){
			String[] flightStr=flight.split(" ");
			String OriCity="&OriCity="+flightStr[0];
			String DestCity="&DestCity="+flightStr[1];
			for(int i=1;i<30;i++){
				DateTime today=new JodaTime().getNow();
				String fdate=today.plusDays(i).toString(fmt);
				sendtask("http://flights.ch.com/default/SearchByTime","SType=0&IfRet=false&MType=0&ANum=1&CNum=0&INum=0&PostType=0"+OriCity+DestCity+"&FDate="+fdate,fdate);
			}
			
		}
		
 
	
 }

private void sendtask(String url,String postdata, String fdate) throws Exception{
	   QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
//	   tasker.setCookies(cookie);
//    tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
    tasker.setUrl(url);
  //    tasker.setDownloadType(DownloadType.selenium_download);
     tasker.setRequestmethod("POST");
    tasker.setPostdata(postdata);
//    tasker.setStoreTableName("chunqiu_flight");
    tasker.addResultItem("fdate", fdate);
    for(int i=0;i<=10;i++){
    	tasker.addJsonpath("number", "$.Packages["+i+"][0].No");
    	tasker.addJsonpath("Departure", "$.Packages["+i+"][0].Departure");
    	tasker.addJsonpath("Arrival", "$.Packages["+i+"][0].Arrival");

    	tasker.addJsonpath("DepartureStation", "$.Packages["+i+"][0].DepartureStation");
    	tasker.addJsonpath("ArrivalStation", "$.Packages["+i+"][0].ArrivalStation");

    	tasker.addJsonpath("DepartureCode", "$.Packages["+i+"][0].DepartureCode");
    	tasker.addJsonpath("ArrivalCode", "$.Packages["+i+"][0].ArrivalCode");
    	
    	tasker.addJsonpath("DepartureTime", "$.Packages["+i+"][0].DepartureTime");
    	tasker.addJsonpath("ArrivalTime", "$.Packages["+i+"][0].ArrivalTime");
    	tasker.addJsonpath("price1", "$.Packages["+i+"][0].CabinInfos[0].Cabins[0].CabinPrice");
    	tasker.addJsonpath("price2", "$.Packages["+i+"][0].CabinInfos[1].Cabins[0].CabinPrice");
    	tasker.addJsonpath("price3", "$.Packages["+i+"][0].CabinInfos[2].Cabins[0].CabinPrice");

        tasker.addXpath_newrow();
    	 

    }
//    tasker.addJsonpath_all("number", "$.Packages[*][0].No");
//	tasker.addJsonpath_all("Departure", "$.Packages[*][0].Departure");
//	tasker.addJsonpath_all("Arrival", "$.Packages[*][0].Arrival");
//
//	tasker.addJsonpath_all("DepartureStation", "$.Packages[*][0].DepartureStation");
//	tasker.addJsonpath_all("ArrivalStation", "$.Packages[*][0].ArrivalStation");
//
//	tasker.addJsonpath_all("DepartureCode", "$.Packages[*][0].DepartureCode");
//	tasker.addJsonpath_all("ArrivalCode", "$.Packages[*][0].ArrivalCode");
//	
//	tasker.addJsonpath_all("DepartureTime", "$.Packages[*][0].DepartureTime");
//	tasker.addJsonpath_all("ArrivalTime", "$.Packages[*][0].ArrivalTime");
//	tasker.addJsonpath_all("price1", "$.Packages[*][0].CabinInfos[0].Cabins[0].CabinPrice");
//	tasker.addJsonpath_all("price2", "$.Packages[*][0].CabinInfos[1].Cabins[0].CabinPrice");
//	tasker.addJsonpath_all("price3", "$.Packages[*][0].CabinInfos[2].Cabins[0].CabinPrice");
	
    
    putDistributeTask(tasker);
}

public static void main(String[] args) throws Exception {
	
	
	ChunqiuFlight master=new ChunqiuFlight();
 
	master.init();
  
		master.start();
  
}
}