package com.uumai.crawer.quartz.jiudian.ctrip;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.localdebug.QuartzLocalDebugAppMaster;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer2.download.CrawlerProxy;

public class JiudianTasker  extends QuartzLocalDebugAppMaster { //QuartzLocalDebugAppMaster{
	@Override
	public void dobusiness() throws Exception {
 		createonetask(2,"2016-01-22","2016-01-23",1);
		

	}
	
	public void createonetask(int cityId,String StartTime,String  DepTime,int page) throws Exception{
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		String url="http://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx?cityId="+cityId+"&StartTime="+StartTime+"&DepTime="+DepTime+"&operationtype=NEWHOTELORDER&IsOnlyAirHotel=F&htlPageView=0&hotelType=F&hasPKGHotel=F&requestTravelMoney=F&isusergiftcard=F&useFG=F&priceRange=-2&promotion=F&prepay=F&IsCanReserve=F&OrderBy=99&hidTestLat=0%257C0&HideIsNoneLogin=T&isfromlist=T&ubt_price_key=htl_search_result_promotion&isHuaZhu=False&abForHuaZhu=True&markType=0&a=0&contrast=0&page="+page+"&contyped=0";
		
 //        tasker.setProxy(new CrawlerProxy("cn-proxy.jp.oracle.com", 80));

//		tasker.setDownloadType(DownloadType.firefox_download);
		tasker.setUrl(url);
//		tasker.addRequestHeader("Referer", "http://hotels.ctrip.com/hotel/beijing2");
//		tasker.setRequestmethod("POST");
		
//		tasker.addSeleniumAction("sendKeys", "id=DCityName1", "上海(SHA)");
//		tasker.addSeleniumAction("sendKeys", "id=ACityName1", "北京(BJS)");
//		tasker.addSeleniumAction("sendKeys", "id=DDate1", "2015-12-20");
//		tasker.addSeleniumAction("click", "id=btnReSearch", null);
		tasker.addResultItem("cityId",cityId);
		tasker.addResultItem("StartTime",StartTime);
		tasker.addResultItem("DepTime",DepTime);
		tasker.addResultItem("page",page);
 		
//		tasker.setStoreTableName("ctrip_jiudian");
//		tasker.addXpath_all("flight", "//div[@class='info-flight']/allText()");
		tasker.addJsonpath("json", "*");
		
		putDistributeTask(tasker);
	}

	public static void main(String[] args) throws Exception{
	
		JiudianTasker master = new JiudianTasker();
 			master.init();
 			 
				master.start();
			 
		}

}
