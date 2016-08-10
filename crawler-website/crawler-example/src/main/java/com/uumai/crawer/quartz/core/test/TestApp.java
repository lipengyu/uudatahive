package com.uumai.crawer.quartz.core.test;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.Download.DownloadType;

public class TestApp extends QuartzLocalDebugAppMaster{ // AbstractAppMaster { 
	
	@Override
	public void dobusiness() throws Exception {
		createonetask("http://amazon.com");
	}
	
	public void createonetask(String url) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
 
 		tasker.setDownloadType(DownloadType.java_download);
		tasker.setUrl(url);
		tasker.addResultItem("test","test");
 		
//		tasker.setStoreTableName("test");
		putDistributeTask(tasker);
	}

	public static void main(String[] args) throws Exception{
	
	     	TestApp master = new TestApp();
 			master.init();
 	 
	
				master.start();
		 
		}
	
}
