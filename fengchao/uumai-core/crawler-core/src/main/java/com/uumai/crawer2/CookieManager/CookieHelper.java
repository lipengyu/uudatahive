package com.uumai.crawer2.CookieManager;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.util.*;

/**
 * Created by rock on 12/7/15.
 */
public class CookieHelper {

    public   List<CrawlerCookie> parseCookies(HttpURLConnection conn) {
        List<CrawlerCookie> cookies=new ArrayList<CrawlerCookie>();
        String headerName=null;
        for (int i=1; (headerName = conn.getHeaderFieldKey(i))!=null; i++) {
            if (headerName.equals("Set-Cookie")) {
                String cookie = conn.getHeaderField(i);
//                System.out.println("cookie:" + cookie);
                if(cookie.indexOf(";")>=0 && cookie.indexOf("=")>0) {
                    cookie = cookie.substring(0, cookie.indexOf(";"));
                    String cookieName = cookie.substring(0, cookie.indexOf("="));
                    String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                    CrawlerCookie crawlerCookie = new CrawlerCookie();
                    crawlerCookie.setName(cookieName);
                    crawlerCookie.setValue(cookieValue);
                    cookies.add(crawlerCookie);
                }
            }
        }

        return cookies;
    }

    public   List<CrawlerCookie> parseCookies(WebDriver webDriver) {
        List<CrawlerCookie> cookies=new ArrayList<CrawlerCookie>();

        Set<Cookie> allCookies = webDriver.manage().getCookies();
        for (Cookie loadedCookie : allCookies) {
//            System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
            CrawlerCookie crawlerCookie=new CrawlerCookie();
            crawlerCookie.setName(loadedCookie.getName());
            crawlerCookie.setValue(loadedCookie.getValue());
            crawlerCookie.setPath(loadedCookie.getPath());
            crawlerCookie.setHttpOnly(loadedCookie.isHttpOnly());
            crawlerCookie.setDomain(loadedCookie.getDomain());
            crawlerCookie.setSecure(loadedCookie.isSecure());
            crawlerCookie.setExpiry(loadedCookie.getExpiry());
            cookies.add(crawlerCookie);
        }


        return cookies;
    }

    public List<CrawlerCookie> readcookiefromfile(File file){

        List<CrawlerCookie> cookies=new ArrayList<CrawlerCookie>();

        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            while (true){
                String linetext=reader.readLine();
                if(linetext==null) break;
                String[] splittexts= linetext.split("\t");
                if(splittexts.length==7){
//                    System.out.println("cookie key:"+splittexts[splittexts.length-2]);
//                    System.out.println("cookie value:"+splittexts[splittexts.length-1]);

                    CrawlerCookie cookie=new CrawlerCookie();
                    cookie.setDomain(splittexts[0]);
                    cookie.setHttpOnly(new Boolean(splittexts[1]));
                    cookie.setPath(splittexts[2]);
                    cookie.setSecure(new Boolean(splittexts[3]));
                    cookie.setExpiry(new Date(new Long(splittexts[4]+"000")));
                    cookie.setName(splittexts[5]);
                    cookie.setValue(splittexts[6]);
                    cookies.add(cookie);
                }else if(splittexts.length==6){
                    CrawlerCookie cookie=new CrawlerCookie();
                    cookie.setDomain(splittexts[0]);
                    cookie.setHttpOnly(new Boolean(splittexts[1]));
                    cookie.setPath(splittexts[2]);
                    cookie.setSecure(new Boolean(splittexts[3]));
                    cookie.setName(splittexts[4]);
                    cookie.setValue(splittexts[5]);
                    cookies.add(cookie);
                }

            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return cookies;

    }

    public List<CrawlerCookie> readcookiefromString(String filecontent) {
        List<CrawlerCookie> cookies=new ArrayList<CrawlerCookie>();
        String[] sline= filecontent.split("\n");
        for(String s:sline){
            String[] splittexts= s.split("\t");
            if(splittexts.length==7){
//                    System.out.println("cookie key:"+splittexts[splittexts.length-2]);
//                    System.out.println("cookie value:"+splittexts[splittexts.length-1]);

                CrawlerCookie cookie=new CrawlerCookie();
                cookie.setDomain(splittexts[0]);
                cookie.setHttpOnly(new Boolean(splittexts[1]));
                cookie.setPath(splittexts[2]);
                cookie.setSecure(new Boolean(splittexts[3]));
                cookie.setExpiry(new Date(new Long(splittexts[4]+"000")));
                cookie.setName(splittexts[5]);
                cookie.setValue(splittexts[6]);
                cookies.add(cookie);
            }else if(splittexts.length==6){
                CrawlerCookie cookie=new CrawlerCookie();
                cookie.setDomain(splittexts[0]);
                cookie.setHttpOnly(new Boolean(splittexts[1]));
                cookie.setPath(splittexts[2]);
                cookie.setSecure(new Boolean(splittexts[3]));
                cookie.setName(splittexts[4]);
                cookie.setValue(splittexts[5]);
                cookies.add(cookie);
            }
        }
        return cookies;
    }

    public List<CrawlerCookie> parseCookieFromCrul(String filecontent) {
        List<CrawlerCookie> cookies=new ArrayList<CrawlerCookie>();
        String[] sline= filecontent.split(";");
        for(String s:sline){
            String[] splittexts= s.split("=");
            if(splittexts.length==2){
                CrawlerCookie cookie=new CrawlerCookie();
                cookie.setName(splittexts[0].trim());
                cookie.setValue(splittexts[1].trim());
                cookies.add(cookie);
            }
        }
        return cookies;

    }
        public static void main(String[] args) throws  Exception {
            String cook=" _sh_ssid_=1465036662472; _e_m=1465036662477; _sid_=1465036662467201780671528; __rf__=\"ifPguCPi+XEd2m7kLsL/TJHpqT80IwNaPpEIFni1/tlz8WSmkd/jRhZoxaeKh/LQIlDj8g/XKxauWuk0LpzhazFj0kxj5NoDn1yR3CEvTz9q9/nHn7AmSe57Advitpqj\"; Hm_lvt_66fcb71a7f1d7ae4ae082580ac03c957=1465036663; Hm_lpvt_66fcb71a7f1d7ae4ae082580ac03c957=1465037252; JSESSIONID=kiasbb4sixng1lvvsgcu1zetm; _ci_=seKbXdbD2qfPYFxkbpmwHwbkxABZeoSn9v5W7xVAdNmNSQWJcpuO2E9039uUKnp6; __i__=\"3SbFlByu/+jR2Mx2DdrrZJrGAB3PhUfIcOG2nBNxjWM=\"; __usx__=\"Ow0jWslaqQqTDgz8FTibXlHc3/ryWmOaBuZWeacUjsA=\"; __up__=\"ETKfDPdAhWvuAJi1vHyv3IFDBfTNZyS03IXVE+mSxgM=\"; _exp_=\"CNVezA4mmKUR2ixtOYRw3aYsR8RXE0YiEHVrERNvqN8=\"; __ut__=\"PV6J5FZv00CBPlCeOZKyNZHzco6GOgSD1wOk9ldLOaI=\"; __atet__=\"73bjyQcMLBr9fivq3EmbQP8ydwr+3OG7vjmxtyhjZn8=\"; __p__=\"pBDdqyQU3DjtJYSuVpPKuEzbK2Ljp7scQTxJ21ihvnODxiIVf1dU4Q==\"; __un__=\"nJixEOSlTf5eqEwobB8/BP6oSeWOflqQcph2a/UaldeigPvqQhHVLvRqzHKIO0EdRBRcAMAzjMA=\"";
        Date d=new Date();
        long t= d.getTime();

        CookieHelper cookieHelper=new CookieHelper();

        List<CrawlerCookie> cookies =cookieHelper.readcookiefromfile(new File("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/crawler-website/crawler-quartz-client/deploy/resources/jd_cookies.txt"));

    }
}
