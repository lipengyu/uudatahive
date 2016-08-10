package com.uumai.crawer2.download.filedownload;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by rock on 5/11/16.
 */
public class TestFileHttpDownload {

    public static void main(String[] a){
        FileHttpDownload fileHttpDownload=new FileHttpDownload();
        //set pool size , default is 10
        //set cookie , get from Openscript
        //set proxy if in office
        //FileHttpDownload fileHttpDownload=new FileHttpDownload(100,null,(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cn-proxy.jp.oracle.com", 80))));

        //loop to send many taksers
        fileHttpDownload.sendtasker("http://www.linkedin.com/directory/country_listing/","/home/rock/uumai/a.html");
        fileHttpDownload.sendtasker("https://sa.linkedin.com/","/home/rock/uumai/b.html");
        //stop pool, will wait until all tasker finished
        fileHttpDownload.stoppool();
    }
}
