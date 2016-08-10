package com.uumai.crawer.quartz.jobs.lagou;

import java.util.ArrayList;
import java.util.List;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
 import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.quartz.util.QuartzQueryItem;
 import com.uumai.crawer.util.UumaiTime;
 import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawer2.download.CrawlerProxy;

public class GongsiTasker  extends QuartzLocalDebugAppMaster { //QuartzLocalDebugAppMaster {// AbstractAppMaster {
 	
	@Override
	public void dobusiness() throws Exception {
  		
// 		 
 		
		
		
		 
 	}
	
 
 
	
	private void sendtask(int compid) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		// tasker.setCookies(cookie);
		// tasker.setUrl("http://data.eastmoney.com/zjlx/600307.html");
		String url="http://www.lagou.com/gongsi/"+compid+".html";
 
//		tasker.setRequestmethod("POST");
		tasker.setUrl(url);
		tasker.setFollingRedirect(false);
//		tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));

//		tasker.setEncoding("gbk");
 		// tasker.setDownloadType(DownloadType.selenium_download);
// 		tasker.setStoreTableName("lagou_gongsi");
		tasker.addResultItem("_id",compid);		

		
//		tasker.addXpath("html", "*");
		tasker.addXpath("companyname", "//div[@class='company_main']/h1/a/text()");
		tasker.addXpath("companyimage", "//img[@alt='公司Logo']/@src");
		tasker.addXpath("companurl", "//div[@class='company_main']/h1/a/@href");
		tasker.addXpath("companyword", "//div[@class='company_main']/div/text()");
		tasker.addXpath_all("company_products", "//div[@id='company_products']/div[2]/allText()");
		
		tasker.addXpath("company_intro_text", "//div[@class='company_intro_text']/allText()");
		tasker.addXpath_all("basic_container", "//div[@id='basic_container']//span/text()");
		tasker.addXpath("company_mangers_item", "//div[@class='company_mangers_item']/allText()");

		tasker.addXpath("companyaddress", "//p[@class='mlist_li_title']/allText()");
		tasker.addXpath("companyaddress_detail", "//p[@class='mlist_li_desc']/allText()");
		putDistributeTask(tasker);
		
	}

public static void main(String[] args) throws Exception {
	
		
		GongsiTasker master=new GongsiTasker();
	 
		master.init();
 	 
			master.start();
  
}

	

}
