package com.uumai.crawer.quartz.gupiao.baidu;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.util.UumaiTime;

public class BaiduNewsSearch extends  QuartzLocalDebugAppMaster  {//  QuartzLocalDebugAppMaster{ 
 
	@Override
    public void dobusiness()  throws Exception{
 		
		dotasker("SZ300104");
		
  
    }
 
 
	
	private void dotasker( String searchtext) throws Exception {
 		String url="http://news.baidu.com/ns?cl=2&rn=20&tn=news&word="+searchtext;
		QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
		 tasker.setUrl(url);
// 	        tasker.setStoreTableName("baidu_news");
 	        tasker.addResultItem("searchtext", searchtext);
 	       tasker.addXpath("result", "//span[@class='nums']/text()");
	        putDistributeTask(tasker);
	}
	
	
	public static void main(String[] args) throws Exception{
 		BaiduNewsSearch master=new BaiduNewsSearch();
     	master.init();
      		master.start();
 	  
	}
}