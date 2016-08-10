package com.uumai.crawer.quartz.jobs.lagou;

import java.util.ArrayList;
import java.util.List;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.util.QuartzQueryItem;
 import com.uumai.crawer.util.UumaiTime;
 import com.uumai.crawer2.download.CrawlerProxy;

public class JobTasker extends QuartzLocalDebugAppMaster{ //QuartzLocalDebugAppMaster {// AbstractAppMaster {
 	
	@Override
	public void dobusiness() throws Exception {
  		
// 		sendtask(1374710,taskerserie);

 		 
		
		
		 
 	}
 
 
	private void sendtask(int positionId) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		// tasker.setCookies(cookie);
		// tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
		String url="http://www.lagou.com/jobs/"+positionId+".html";
//		tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));

//		tasker.setRequestmethod("POST");
		tasker.setUrl(url);
		tasker.setFollingRedirect(false);
//		tasker.setEncoding("gbk");
 		// tasker.setDownloadType(DownloadType.selenium_download);
// 		tasker.setStoreTableName("lagou_jobs");
		tasker.addResultItem("_id",positionId);		

		tasker.addXpath("title", "//dt[@class='clearfix join_tc_icon']/h1/@title");
		tasker.addXpath_all("job_request", "//dd[@class='job_request']/p[1]/span/text()");
		tasker.addXpath("zhiweiyouhuo", "//dd[@class='job_request']/p[2]/text()");
		tasker.addXpath("description", "//dd[@class='job_bt']/allText()");
		tasker.addXpath("jd_publisher", "//div[@class='publisher_name']/allText()");

		tasker.addXpath("companyid", "//input[@id='companyid']/@value");
		putDistributeTask(tasker);
		
	}

public static void main(String[] args) throws Exception {
	
		
	JobTasker master=new JobTasker();
	 
		master.init();
 	 
			master.start();
  
}

}
