package com.uumai.zookeeperclient.watch;

import com.uumai.zookeeperclient.uitl.ZookeeperFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by rock on 8/18/15.
 */
public class WatchZKClient   extends  Thread implements Watcher{
    private ZooKeeper zk ;
    private String path;
    private String current_data_value;

    public WatchZKClient(String path){
        this.path=path;
    }

    public void connect() throws Exception {
        zk=new ZooKeeper(ZookeeperFactory.ZOOKEEPER_SERVER, ZookeeperFactory.ZOOKEEPER_sessionTimeout, this);
        System.out.println("connect to zookeeper server.");
        zk.exists(path,true);
        zk.getChildren(path,true);
        this.current_data_value=getCurrentNodeData();
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("UumaiZKclient get event:"+event.getType());
        if(event.getType()== Watcher.Event.EventType.NodeDataChanged) {
            try {
                String data=getCurrentNodeData();
                if(data!=null&&!data.equals(current_data_value))
                    NodeDataChanged(data);
                current_data_value=data;
                zk.exists(path,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.getType()== Event.EventType.NodeCreated){
            try{
                String data=getCurrentNodeData();
                if(data!=null) current_data_value=data;
                NodeCreated(data);
                zk.exists(path,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.getType()== Event.EventType.NodeDeleted){
            try{
                current_data_value=null;
                NodeDeleted();
                zk.exists(path,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.getType()== Event.EventType.NodeChildrenChanged){
            try{
                NodeChildrenChanged();
                zk.getChildren(path,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("not support event type!");
        }

    }

    protected void NodeChildrenChanged() {
        System.out.println("process data children change on path:"+ path+", new value:");
    }
    protected void NodeCreated(String data){
        System.out.println("process data create on path:"+ path+", new value:"+data);
    }

    protected void NodeDeleted(){
        System.out.println("process data delete on path:"+ path);
    }

    protected void NodeDataChanged(String data){
        System.out.println("process data change on path:"+ path+", new value:"+data);
    }

    public List<String> getChindren(){
        try {
            if(zk.exists(path,null)!=null){
                List<String> data=zk.getChildren(path,false,null) ;
//            System.out.println(path + " data value :"+data);
                return data;
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCurrentNodeData(){
        try {
            if(zk.exists(path,null)!=null){
                String data=new String(zk.getData(path,false,null)) ;
//            System.out.println(path + " data value :"+data);
                return data;
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCurrent_data_value() {
        return current_data_value;
    }
}
