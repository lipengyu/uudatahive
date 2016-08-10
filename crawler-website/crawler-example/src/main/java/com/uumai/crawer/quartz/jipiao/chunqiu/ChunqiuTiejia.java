package com.uumai.crawer.quartz.jipiao.chunqiu;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.CrawlerProxy;

public class ChunqiuTiejia extends QuartzLocalDebugAppMaster {

 
 
@Override
public void dobusiness()  throws Exception{
  	sendtask("http://flights.ch.com/tejia");
 
}

private void sendtask(String url) throws Exception{
	   QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
//	   tasker.setCookies(cookie);
//    tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
    tasker.setUrl(url);
 //    tasker.setDownloadType(DownloadType.selenium_download);
//     tasker.setStoreTableName("chunqiu_tejia");
    for(int i=1;i<=14;i++){
    	for(int j=1;j<=5;j++){
        	tasker.addXpath("flight", "//table[@class='b0'][1]//tr[@class='flight']["+i+"]/td["+j+"]/a/text()");
        	tasker.addXpath("link", "//table[@class='b0'][1]//tr[@class='flight']["+i+"]/td["+j+"]/a/@href");
        	tasker.addXpath_newrow();
    	}

    }
    for(int i=1;i<=8;i++){
    	for(int j=1;j<=5;j++){
        	tasker.addXpath("flight", "//table[@class='b0'][2]//tr[@class='flight']["+i+"]/td["+j+"]/a/text()");
        	tasker.addXpath("link", "//table[@class='b0'][2]//tr[@class='flight']["+i+"]/td["+j+"]/a/@href");
        	tasker.addXpath_newrow();
    	}

    }
    putDistributeTask(tasker);
}

public static void main(String[] args) throws Exception {
	
	
	ChunqiuTiejia master=new ChunqiuTiejia();
	master.init();
  
		master.start();
   
}



}
