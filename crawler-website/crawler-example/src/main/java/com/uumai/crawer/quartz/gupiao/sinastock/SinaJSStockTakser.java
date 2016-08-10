package com.uumai.crawer.quartz.gupiao.sinastock;

import java.util.List;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.result.QuartzResult;
import com.uumai.crawer.util.UumaiTime;

public class SinaJSStockTakser extends QuartzLocalDebugAppMaster {
 
	@Override
    public void dobusiness()  throws Exception{
  
        	String symbol ="SZ20001";
                	dotask("http://hq.sinajs.cn/list="+symbol, symbol);
        	 
        
        
        
     }

	private void dotask(String url,String sockname)throws Exception{
		   QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
//		   tasker.setCookies(cookie);
//        tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
        tasker.setUrl(url);
 //        tasker.setEncoding("gbk");
 //        tasker.setDownloadType(DownloadType.selenium_download);
//         tasker.setStoreTableName("sinastock");	
        tasker.addResultItem("stock", sockname);
        
        tasker.addXpath("all", "*");
        
        putDistributeTask(tasker);
	}
	
	public static void main(String[] args)  throws Exception{

		SinaJSStockTakser master=new SinaJSStockTakser();
    	master.init();
   
    		master.start();
 	 	 
	}

}
