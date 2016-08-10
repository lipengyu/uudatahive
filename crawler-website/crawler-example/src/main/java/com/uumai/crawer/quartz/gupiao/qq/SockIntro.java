package com.uumai.crawer.quartz.gupiao.qq;

import java.util.ArrayList;
import java.util.List;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.result.QuartzResult;
import com.uumai.crawer.quartz.util.QuartzQueryItem;
import com.uumai.crawer.util.UumaiTime;

public class SockIntro extends QuartzLocalDebugAppMaster {
 
	@Override
    public void dobusiness()  throws Exception{
           
		String symbol="SZ20006";
        	geteastmoney("http://stock.finance.qq.com/corp1/profile.php?zqdm="+symbol.substring(2), symbol );
        
        
        
        
 
     }
 

	private void geteastmoney(String url,String sockname) throws Exception{
		   QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
//		   tasker.setCookies(cookie);
//        tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
        tasker.setUrl(url);
         tasker.setEncoding("gbk");
 //        tasker.setDownloadType(DownloadType.selenium_download);
//         tasker.setStoreTableName("qq_stock_intro");
        tasker.addResultItem("name", sockname);
//        tasker.addXpath("html", "*");
        tasker.addXpath_all("shuxing", "//table[@class='list']//td/allText()");
        
  
        putDistributeTask(tasker);
	}
	
	public static void main(String[] args)  throws Exception{
		
		
		
 		SockIntro master=new SockIntro();
 
    	master.init();
   
    		master.start();
 	 	 
	}
}