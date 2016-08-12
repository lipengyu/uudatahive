package com.uumai.crawer.quartz.gupiao.baidu;


import java.util.List;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.result.QuartzResult;
import com.uumai.crawer.util.UumaiTime;


public class BaiduSearch extends QuartzLocalDebugAppMaster {
 
	@Override
    public void dobusiness()  throws Exception{
 
        	
        	dotasker("SZ002006");	
 
    }
 
 
	
	private void dotasker( String searchtext) throws Exception {
 		String url="http://www.baidu.com/s?wd="+searchtext;
		QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
		 tasker.setUrl(url);
 	        tasker.addResultItem("searchtext", searchtext);
 	        tasker.addXpath("result", "//div[@class='nums']/text()");
	        putDistributeTask(tasker);
	}
	
	
	public static void main(String[] args) throws Exception{
 	  		new BaiduSearch().init().start();

	}
}
