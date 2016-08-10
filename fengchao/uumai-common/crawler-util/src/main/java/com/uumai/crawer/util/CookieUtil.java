package com.uumai.crawer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rock on 7/24/15.
 */
public class CookieUtil {

    static Map<String,String> cookielist=new HashMap<String, String>();

//    public static String loadCookie(String cookiekey){
//        String cookievalue=cookielist.get(cookiekey);
//        if(cookievalue==null){
//            File cookiefile=new File(UumaiProperties.getConfigRootPath()+File.separator +cookiekey +".cookie.txt");
//            if(!cookiefile.exists()) {
//                cookievalue="";
//            }else{
//                cookievalue=readcookiefromfile(cookiefile);
//            }
//            cookielist.put(cookiekey,cookievalue);
//        }
//        return cookievalue;
//    }

//    public static String readcookiefromfile(File file){
//        StringBuffer sb=new StringBuffer();
//        boolean firstline=true;
//        BufferedReader reader=null;
//        try{
//            reader=new BufferedReader(new FileReader(file));
//            while (true){
//                String linetext=reader.readLine();
//                if(linetext==null) break;
//                String[] splittexts= linetext.split("\t");
//                if(splittexts.length>1){
////                    System.out.println("cookie key:"+splittexts[splittexts.length-2]);
////                    System.out.println("cookie value:"+splittexts[splittexts.length-1]);
//
//                    if(!firstline)
//                        sb.append(";");
//
//                    sb.append(splittexts[splittexts.length-2]);
//                    sb.append("=");
//                    sb.append(splittexts[splittexts.length-1]);
//
//                    if(firstline) firstline=false;
//                }
//
//            }
//            reader.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return sb.toString();
//
//    }

    public static void main(String[] args) throws Exception {
//        UumaiProperties.init("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/crawler-example/deploy/resources/uumai.properties");
//        System.out.println(CookieUtil.loadCookie("amazon"));
        ;

    }
}
