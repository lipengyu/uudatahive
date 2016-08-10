package com.uumai.crawer.util;

import com.uumai.zookeeperclient.UUmaiZkClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: rock
 * Date: 3/11/15
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class UumaiProperties {
//    private static Properties properties;

//    public static String configRootPath;

    private static Map<String, String> uumai_properties=new HashMap<String, String>();

//        public static String getConfigRootPath(){
//            if(configRootPath==null)
//                init();
//            return configRootPath;
//        }

    public static String getUUmaiHome(){
        return System.getProperty("user.home")+ "/uumai";
    }

    public static String getFengchaoHome(){
        return System.getProperty("fengchao.home");
    }

    public static String getLocalIP(String name) {
        String ip = "";
        try {
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals(name)) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address)
                            continue;
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
        return ip;
    }
    
    public static String getIpaddress(){
    	 return getLocalIP("eth0");
    }

    public static String getHostName(){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            return address.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void init(String filePath){
//        properties = new Properties();
//        InputStream input = null;
//        try {
////        	String path=System.getenv("UUMAI_HOME");
////        	if(path==null){
//            //System.getProperty("uumai.home");
////        	}
//
//            input = new BufferedInputStream(new FileInputStream(filePath));
//
//            //            input= UumaiProperties.class.getClassLoader()
////                    .getResourceAsStream("uumai.properties");
//            properties.load(input);// 加载属性文件
////            System.out.println("url:" + properties.getProperty("url"));
////            System.out.println("username:" + properties.getProperty("username"));
////            System.out.println("password:" + properties.getProperty("password"));
////            System.out.println("database:" + properties.getProperty("database"));
//        } catch (IOException io) {
//            io.printStackTrace();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        configRootPath=new File(filePath).getParent();

//    }
//    public static void init(){

//        	String path=System.getProperty("uumai.home");
//
//        	if(path==null){
//        		//path=System.getenv("UUMAI_HOME");
//        		path="/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/build";
//        	}
//
////            if(path==null){
////                System.out.println("no uumai.properties found, will use default value.");
////                properties = new Properties();
////                return;
////            }
//
//        configRootPath=path;
//
//        String filePath = path + File.separator
//                    + "uumai.properties";
//
//            init(filePath);
//    }

    private synchronized  static String loadfromZookeeper(String key){
        UUmaiZkClient client=new UUmaiZkClient();
        String value=client.readconifg("/uumai/config/"+key);
        if(value==null) value="";
        uumai_properties.put(key,value);
        client.close();
        return value;
    }
    public static String readconfig(String key, String defaultvalue,boolean usingCache){
        //get from zookeeper
        String value= null;
        if(usingCache){
            value= uumai_properties.get(key);
            if(value==null){
                value=loadfromZookeeper(key);
            }
        }else{
            value=loadfromZookeeper(key);
        }


        if("".equals(value)){
            return defaultvalue;
        }else{
            return value;
        }

        //get from properies files
//         if(properties==null){
//            init();
//        }
//         value=properties.getProperty(key);
//        if(value==null){
//            return       defaultvalue;
//        }
//        return    value;
    }

    public static String readconfig(String key, String defaultvalue){
        return readconfig(key,defaultvalue,true);
    }

    public static void main(String[] args) throws Exception {
        String value=UumaiProperties.readconfig("uumai.mongodb.MONGO_URI","default");


        System.out.println("value is " + value);
//        System.out.println("value is " + UumaiProperties.getIpaddress());
//    	Map<String, String> map = System.getenv();  
//        for(Iterator<String> itr = map.keySet().iterator();itr.hasNext();){  
//            String key = itr.next();  
//            System.out.println(key + "=" + map.get(key));  
//        }     
//        Properties props = System.getProperties();  
//        props.list(System.out);

//        for(int i=0;i<20;i++){
//            String mainclass=UumaiProperties.readconfig("uumai.crawler.worker"+i+".appslave.mainclass",null);
//            System.out.println("mainclass is " + mainclass);
//        }

//         Map<String, String> uumai_properties=new HashMap<String, String>();
//         System.out.println("=" + uumai_properties.get("test"));
//         uumai_properties.put("test", null);
//         System.out.println("=" + uumai_properties.get("test"));
    }
}
