package com.uumai.zookeeperclient;

import com.uumai.zookeeperclient.uitl.ZookeeperClient;

import java.util.List;

/**
 * Created by rock on 11/2/15.
 */
public class    UUmaiZkClient {
    public  ZookeeperClient util ;

    public UUmaiZkClient(){
        try {
            util = new ZookeeperClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUmaiZkClient(String server){
        try {
            util = new ZookeeperClient(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getChildren(String path)   {
        try {
            return util.getChildren(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void listAll(String path) throws Exception {
        String value=util.getData(path);
        System.out.println("path:" + path + ", value:" + value);
        List<String> children=util.getChildren(path);
        for(String child:children){
//            System.out.println("child path:" + child );
            listAll(path+"/"+child);
        }
    }

    public String readconifg(String path){
        try {
            return util.getData(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void close(){
        try {
            util.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void init_uumai_zkconfig() throws Exception {
        util.updateNodes("/uumai", null);
        util.updateNodes("/uumai/config", null);
        util.updateNodes("/uumai/config/uumai.core.queue.class", "redis");           //activemq
        util.updateNodes("/uumai/config/uumai.redis.serverip", "rock-cdc-server");
        util.updateNodes("/uumai/config/uumai.activemq.serverip", "rock-cdc-server");
        util.updateNodes("/uumai/config/uumai.mongodb.MONGO_URI", "mongodb://rock-cdc-server:27017");
        util.updateNodes("/uumai/config/uumai.hdfs.uri", "hdfs://rock-cdc-server:9000");
//        util.updateNodes("/uumai/config/uumai.elasticsearch.serverip", "rock-cdc-server");
        //util.updateNodes("/uumai/config/uumai.crawler.retrytime","3");
        //util.updateNodes("/uumai/config/uumai.crawler.maxRetryTimes_distributed","3");
        util.updateNodes("/uumai/config/uumai.crawler.defaultpoolsize", "10");
        util.updateNodes("/uumai/config/uumai.crawler.queuepool.reloadkeyinterval", "60");  //seconds
        util.updateNodes("/uumai/config/uumai.crawler.CrawlerProxy.ip", "cn-proxy.jp.oracle.com");
        util.updateNodes("/uumai/config/uumai.crawler.CrawlerProxy.port", "80");

//        util.updateNodes("/uumai/config/webdriver.chrome.driver", "/uumai/driver/chromedriver");

        //quartz config
        util.updateNodes("/uumai/config/uumai.crawler.worker.appslave.mainclass", null);
        util.updateNodes("/uumai/config/uumai.crawler.worker.appslave.mainclass/1", "com.uumai.crawer.quartz.QuartzAppSlave");

        util.updateNodes("/uumai/config/uumai.crawler.worker.appslave.poolsize", null);
        util.updateNodes("/uumai/config/uumai.crawler.worker.appslave.poolsize/1", "10");

        util.updateNodes("/uumai/config/uumai.crawler.worker.appslave.rmiport", null);
        util.updateNodes("/uumai/config/uuTimeoutWatchThreadmai.crawler.worker.appslave.rmiport/1", "8999");

        //resourcepoolsize config
//        util.updateNodes("/uumai/config/uumai.crawler.quartz.maxCrawlerDeepath", "10");

        util.updateNodes("/uumai/config/resourcepoolsize",null);
        util.updateNodes("/uumai/config/resourcepoolwaittime",null);
        util.updateNodes("/uumai/config/resourcepoolsize/default", "-1");
        util.updateNodes("/uumai/config/resourcepoolwaittime/default", "-1");


        util.updateNodes("/uumai/config/resourcepoolsize/www.amazon.com", "8");
        util.updateNodes("/uumai/config/resourcepoolsize/www.keepa.com", "2");

        util.updateNodes("/uumai/config/resourcepoolsize/xueqiu.com", "2");
        util.updateNodes("/uumai/config/resourcepoolwaittime/xueqiu.com", "5000");

        util.updateNodes("/uumai/config/resourcepoolsize/data.eastmoney.com", "1");
        util.updateNodes("/uumai/config/resourcepoolwaittime/data.eastmoney.com", "5000");

        util.updateNodes("/uumai/config/resourcepoolsize/www.yhd.com", "4");
        util.updateNodes("/uumai/config/resourcepoolsize/list.suning.com", "4");
        util.updateNodes("/uumai/config/resourcepoolsize/product.suning.com", "4");

        util.updateNodes("/uumai/config/resourcepoolsize/list.gome.com.cn", "1");


        util.updateNodes("/uumai/config/resourcepoolsize/www.amazon.cn", "5");

        util.updateNodes("/uumai/config/resourcepoolsize/api.github.com", "1");

        util.updateNodes("/uumai/config/resourcepoolsize/www.uumai.net", "1");

        util.updateNodes("/uumai/config/resourcepoolsize/www.linkedin.com", "1");

        util.updateNodes("/uumai/config/resourcepoolsize/ehire.51job.com", "1");

//        util.updateNodes("/uumai/config/resourcepoolsize/p.3.cn", "1");
    }

    public void init_uumai_zkconfig_local() throws Exception {
        util.updateNodes("/uumai", null);
        util.updateNodes("/uumai/config", null);
        util.updateNodes("/uumai/config/uumai.core.queue.class", "redis");           //activemq
        util.updateNodes("/uumai/config/uumai.redis.serverip", "rock-cdc-server");
        util.updateNodes("/uumai/config/uumai.activemq.serverip", "rock-cdc-server");
        util.updateNodes("/uumai/config/uumai.mongodb.MONGO_URI", "mongodb://rock-cdc-server:27017");
        util.updateNodes("/uumai/config/uumai.hdfs.uri", "hdfs://rock-cdc-server:9000");
//        util.updateNodes("/uumai/config/uumai.elasticsearch.serverip", "rock-cdc-server");
        //util.updateNodes("/uumai/config/uumai.crawler.retrytime","3");
        //util.updateNodes("/uumai/config/uumai.crawler.maxRetryTimes_distributed","3");
        util.updateNodes("/uumai/config/uumai.crawler.defaultpoolsize", "10");
        util.updateNodes("/uumai/config/uumai.crawler.queuepool.reloadkeyinterval", "10");  //seconds
        util.updateNodes("/uumai/config/uumai.crawler.CrawlerProxy.ip", "cn-proxy.jp.oracle.com");
        util.updateNodes("/uumai/config/uumai.crawler.CrawlerProxy.port", "80");

        util.updateNodes("/uumai/config/webdriver.chrome.driver", "/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/driver/chromedriver");

        util.updateNodes("/uumai/config/resourcepoolsize.default", "-1");

    }
    private void cleanconfig() throws Exception {
        util.delete("/uumai/config");
    }

    public static void main(String[] args) {
//        UUmaiZkClient client=new UUmaiZkClient();
        UUmaiZkClient client=new UUmaiZkClient("rock-cdc-server:2181");

        try {
            client.cleanconfig();
            client.init_uumai_zkconfig();
//            client.listAll("/uumai");
//            client.util.delete("/uumai/config/resourcepoolsize/www.linkedin.com");
//            client.util.updateNodes("/uumai/config/resourcepoolsize.xueqiu.com", "12");


//            client.util.updateNodes("/test/21","a");
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}