package com.uumai.crawer.quartz.jobs.lagou;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
 import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.CrawlerProxy;

public class SearchJobTasker extends QuartzLocalDebugAppMaster {// AbstractAppMaster {
 	
	@Override
	public void dobusiness() throws Exception {
  		
 		sendtask("java",1);

 		
		
		
		 
 	}
	
  
	
	private void sendtask(String searchtext, int page) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
 
		String url="http://www.lagou.com/jobs/positionAjax.json?px=new&city=上海&first=false&pn="+page+"&kd="+searchtext;
//		tasker.setRequestmethod("POST");
		tasker.setUrl(url);
//		tasker.setEncoding("gbk");
 		// tasker.setDownloadType(DownloadType.selenium_download);
		tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));
// 		tasker.setStoreTableName("lagou_searchjob");
		tasker.addResultItem("searchtext",searchtext);		
		tasker.addResultItem("page",page);		
 
		tasker.addJsonpath_all("positionId", "$.content.result[*].positionId");
		tasker.addJsonpath_all("companyId", "$.content.result[*].companyId");
//		tasker.addJsonpath("resultMessage", "jingdong_service_promotion_getcode_responce.queryjs_result.resultMessage");
//		tasker.addJsonpath("url", "jingdong_service_promotion_getcode_responce.queryjs_result.url");

		putDistributeTask(tasker);
		
	}

public static void main(String[] args) throws Exception {
	
		
	SearchJobTasker master=new SearchJobTasker();
	 
		master.init();
 	 
			master.start();
  
}

}
