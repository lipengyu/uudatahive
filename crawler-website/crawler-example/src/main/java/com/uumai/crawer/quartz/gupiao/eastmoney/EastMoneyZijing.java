package com.uumai.crawer.quartz.gupiao.eastmoney;

import java.net.URL;
import java.util.List;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.result.QuartzResult;
import com.uumai.crawer.quartz.result.QuartzResultItem;
import com.uumai.crawer.quartz.result.QuartzXpathItem;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.Download.DownloadType;

/**
 * Created with IntelliJ IDEA.
 * User: rock
 * Date: 3/19/15
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */ 
public class EastMoneyZijing extends QuartzLocalDebugAppMaster {
 
	@Override
    public void dobusiness()  throws Exception{
  String symbol="SZ20006";
                	geteastmoney("http://data.eastmoney.com/zjlx/"+symbol.substring(2)+".html", symbol );
        	 
        
        

         
       
    }

	private void geteastmoney(String url,String sockname) throws Exception{
		   QuartzCrawlerTasker tasker=new QuartzCrawlerTasker();
//		   tasker.setCookies(cookie);
//        tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
        tasker.setUrl(url);
         tasker.setEncoding("gbk");
 //        tasker.setDownloadType(DownloadType.selenium_download);
//         tasker.setStoreTableName("eastmoney_temp");
        tasker.addResultItem("股票代码", sockname);
        
//        tasker.addXpath("日期", "//table[@id='dt_1']/tbody/tr[1]/td[1]/text()");
//        tasker.addXpath("收盘价", "//table[@id='dt_1']/tbody/tr[1]/td[2]/span/text()");
//        tasker.addXpath("涨跌幅", "//table[@id='dt_1']/tbody/tr[1]/td[3]/span/text()");
//        tasker.addXpath("主力净流入", "//table[@id='dt_1']/tbody/tr[1]/td[4]/span/text()");
//        tasker.addXpath("主力净流入比", "//table[@id='dt_1']/tbody/tr[1]/td[5]/span/text()");
//        tasker.addXpath("超大单净流入", "//table[@id='dt_1']/tbody/tr[1]/td[6]/span/text()");
//        tasker.addXpath("超大单净流入比", "//table[@id='dt_1']/tbody/tr[1]/td[7]/span/text()");
//        tasker.addXpath("大单净流入", "//table[@id='dt_1']/tbody/tr[1]/td[8]/span/text()");
//        tasker.addXpath("大单净流入比", "//table[@id='dt_1']/tbody/tr[1]/td[9]/span/text()");
//        tasker.addXpath("中单净流入", "//table[@id='dt_1']/tbody/tr[1]/td[10]/span/text()");
//        tasker.addXpath("中单净流入比", "//table[@id='dt_1']/tbody/tr[1]/td[11]/span/text()");
//        tasker.addXpath("小单净流入", "//table[@id='dt_1']/tbody/tr[1]/td[12]/span/text()");
//        tasker.addXpath("小单净流入比", "//table[@id='dt_1']/tbody/tr[1]/td[13]/span/text()");
        
        tasker.addXpath_all("日期", "//table[@id='dt_1']/tbody/*/td[1]/text()");
        tasker.addXpath_all("收盘价", "//table[@id='dt_1']/tbody/*/td[2]/span/text()");
        tasker.addXpath_all("涨跌幅", "//table[@id='dt_1']/tbody/*/td[3]/span/text()");
        tasker.addXpath_all("主力净流入", "//table[@id='dt_1']/tbody/*/td[4]/span/text()");
        tasker.addXpath_all("主力净流入比", "//table[@id='dt_1']/tbody/*/td[5]/span/text()");
        tasker.addXpath_all("超大单净流入", "//table[@id='dt_1']/tbody/*/td[6]/span/text()");
        tasker.addXpath_all("超大单净流入比", "//table[@id='dt_1']/tbody/*/td[7]/span/text()");
        tasker.addXpath_all("大单净流入", "//table[@id='dt_1']/tbody/*/td[8]/span/text()");
        tasker.addXpath_all("大单净流入比", "//table[@id='dt_1']/tbody/*/td[9]/span/text()");
        tasker.addXpath_all("中单净流入", "//table[@id='dt_1']/tbody/*/td[10]/span/text()");
        tasker.addXpath_all("中单净流入比", "//table[@id='dt_1']/tbody/*/td[11]/span/text()");
        tasker.addXpath_all("小单净流入", "//table[@id='dt_1']/tbody/*/td[12]/span/text()");
        tasker.addXpath_all("小单净流入比", "//table[@id='dt_1']/tbody/*/td[13]/span/text()");
        

 
        putDistributeTask(tasker);
	}
	
	public static void main(String[] args)  throws Exception{
		
		
		
 		EastMoneyZijing master=new EastMoneyZijing();
    	master.init();
   
    		master.start();
 	 	 
	}
 
}
