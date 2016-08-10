package com.uumai.crawer.quartz.jobs.fiveonejob;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.core.cookies.CookieConstant;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.Download;

public class FiveonejobTasker extends QuartzLocalDebugAppMaster {
 	
	@Override
	public void dobusiness() throws Exception {
		String searchtext="java";
 		sendtask(searchtext,1);
		
		
	 
 		
	}
	
	
	private void sendtask(String searchtext, int page) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		// tasker.setCookies(cookie);
		// tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
		String url="http://search.51job.com/jobsearch/search_result.php";
//		tasker.setCookies(CookieConstant.fiveonejob_cookie);
 
		tasker.setPostdata("fromJs=1&jobarea=000000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword="+searchtext+"&keywordtype=0&curr_page="+page+"&lang=c&stype=2&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9");
		tasker.setRequestmethod("POST");
		tasker.setUrl(url);
		tasker.setEncoding("gbk");
 		// tasker.setDownloadType(DownloadType.selenium_download);
// 		tasker.setStoreTableName("51job");
		tasker.addResultItem("searchtext",searchtext);		
		tasker.addResultItem("page",page);		

		tasker.addResultItem("url", url);
		
		tasker.addXpath_all("title","//div[@class='el']/p/a/text()");
		tasker.addXpath_all("link","//div[@class='el']/p/a/@href");
//		tasker.addJsonpath("resultCode", "jingdong_service_promotion_getcode_responce.queryjs_result.resultCode");
//		tasker.addJsonpath("resultMessage", "jingdong_service_promotion_getcode_responce.queryjs_result.resultMessage");
//		tasker.addJsonpath("url", "jingdong_service_promotion_getcode_responce.queryjs_result.url");

		putDistributeTask(tasker);
		
	}

public static void main(String[] args) throws Exception {
	
	
	FiveonejobTasker master=new FiveonejobTasker();
 
	master.init();
  
		master.start();
  
}

}
