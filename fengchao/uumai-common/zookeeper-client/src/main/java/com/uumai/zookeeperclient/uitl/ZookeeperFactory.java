package com.uumai.zookeeperclient.uitl;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by rock on 7/9/15.
 */
public class ZookeeperFactory {
    public static String ZOOKEEPER_SERVER="uumai_zk_server:2181";        //UumaiProperties.readconfig("uumai.zookeeper.server","localhost:2181");
    public static int ZOOKEEPER_sessionTimeout=5000;

    public  static  synchronized  ZooKeeper getinstance(String ZOOKEEPER_SERVER) throws Exception {
        ZooKeeper  zk = new ZooKeeper(ZOOKEEPER_SERVER,ZOOKEEPER_sessionTimeout,new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
//                    System.out.println(event.getPath());MongoDao
//                    System.out.println(event.getType().name());
//                    System.out.println(event.getState().getIntValue());
            }
        });
        return zk;
    }

    public  static  synchronized  ZooKeeper getinstance() throws Exception{
        ZooKeeper  zk= new ZooKeeper(ZOOKEEPER_SERVER,ZOOKEEPER_sessionTimeout,new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
//                    System.out.println(event.getPath());
//                    System.out.println(event.getType().name());
//                    System.out.println(event.getState().getIntValue());
                }
            });

           return zk;
    }

//    //@nouse
//    public  static ZooKeeper getinstancewithWatch(Watcher watcher) throws Exception{
//            ZooKeeper   zk = new ZooKeeper("uumai_zookeeperserver:2181", 500000,watcher);
//
////            ZooKeeper   zk = new ZooKeeper(zookeeperserverip, 500000, new Watcher() {
////                // 监控所有被触发的事件
////                public void process(WatchedEvent event) {
////                    System.out.println(event.getPath());
////                    System.out.println(event.getType().name());
////                    //System.out.println(event.getState().getIntValue());
////                }
////            });
//         return zk;
//    }


}
