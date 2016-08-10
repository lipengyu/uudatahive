package com.uumai.zookeeperclient;

import com.uumai.zookeeperclient.uitl.ZookeeperClient;
import com.uumai.zookeeperclient.watch.WatchZKClient;

/**
 * Created by rock on 8/19/15.
 */
public class TestThread  extends  Thread{

    private WatchZKClient client;
    private String path;

    public  TestThread(String path){
        this.path=path;
    }

    public void init(){
        client=new WatchZKClient(path);
        try {
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String data=client.getCurrent_data_value();
        System.out.println("init data:" + data);
    }

    @Override
    public void run() {
        client.setDaemon(true);
        client.start();

//        int i=0;
        while(true){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            i=i+1;
//            System.out.println("i:"+i);
//            if(i==30) break;
        }
    }
    public static void main(String[] args) throws Exception{
        ZookeeperClient client=new ZookeeperClient();
        client.updateNodes("/test",null);
        client.updateNodes("/test/1",null);
        TestThread testThread=new TestThread("/test");
        testThread.init();
        testThread.start();



    }
}
