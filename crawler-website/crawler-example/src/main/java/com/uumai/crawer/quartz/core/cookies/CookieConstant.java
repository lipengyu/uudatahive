package com.uumai.crawer.quartz.core.cookies;

import java.io.File;
import java.util.List;

import com.uumai.crawer.util.CookieUtil;
import com.uumai.crawer.util.UumaiProperties;
import com.uumai.crawer2.CookieManager.CookieHelper;
import com.uumai.crawer2.CookieManager.CrawlerCookie;

public class CookieConstant {

//	public static String xueqiu_cookie = CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/xueqiu_cookies.txt"));

	public static List<CrawlerCookie> amazon_cookie = new  CookieHelper().readcookiefromfile(new File(UumaiProperties.getUUmaiHome()+ "/cookies/jd_cookies.txt"));; //=new CookieHelper().readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/jd_cookies.txt"));
			
//			CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/amazon_cookies.txt"));
//	public static String amazoncn_cookie=CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/amazoncn_cookies.txt"));

	public static List<CrawlerCookie> jd_cookie=new  CookieHelper().readcookiefromfile(new File(UumaiProperties.getUUmaiHome() + "/cookies/jd_cookies.txt"));

	public static List<CrawlerCookie> jdlianmengapi_cookie=new  CookieHelper().readcookiefromfile(new File(UumaiProperties.getUUmaiHome() + "/cookies/jdlianmengapi_cookies.txt"));

 	
 
	
//	public static String fiveonejob_cookie=CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/51job_cookies.txt"));

//	public static String linkedin_cookie=CookieUtil.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/linkedin_cookies.txt"));

 
}
