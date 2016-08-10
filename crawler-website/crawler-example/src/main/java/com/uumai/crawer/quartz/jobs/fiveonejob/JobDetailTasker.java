package com.uumai.crawer.quartz.jobs.fiveonejob;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.core.cookies.CookieConstant;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.util.QuartzQueryItem;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.Download;

public class JobDetailTasker extends QuartzLocalDebugAppMaster {
	Set<String> asinSet = new HashSet<String>();
 	 
	
	@Override
	public void dobusiness() throws Exception {
		 
 
 
		
		
		
 		 
 	}
 	 
 
	
	private void sendtask(String jobid,String url) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		// tasker.setCookies(cookie);
		// tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
// 		tasker.setCookies(CookieConstant.fiveonejob_cookie);
 		
		
 		tasker.setUrl(url);
		tasker.setEncoding("gbk");
  		// tasker.setDownloadType(DownloadType.selenium_download);
// 		tasker.setStoreTableName("51job_detail");
		tasker.addResultItem("_id",new Integer(jobid));		
 
		tasker.addResultItem("url", url);
		
		tasker.addXpath("title","//li[@class='tCompany_job_name']/allText()");
		tasker.addXpath("desc","//div[@class='tCompany_text']/allText()");
//		tasker.addJsonpath("resultCode", "jingdong_service_promotion_getcode_responce.queryjs_result.resultCode");
//		tasker.addJsonpath("resultMessage", "jingdong_service_promotion_getcode_responce.queryjs_result.resultMessage");
//		tasker.addJsonpath("url", "jingdong_service_promotion_getcode_responce.queryjs_result.url");

		putDistributeTask(tasker);
		
	}

public static void main(String[] args) throws Exception {
	
	
	JobDetailTasker master=new JobDetailTasker();
 
	master.init();
  
		master.start();
  
}

}
