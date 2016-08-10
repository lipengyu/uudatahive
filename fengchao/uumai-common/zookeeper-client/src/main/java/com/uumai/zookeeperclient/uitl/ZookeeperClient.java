package com.uumai.zookeeperclient.uitl;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by rock on 7/9/15.
 */
public class ZookeeperClient {

    private ZooKeeper zk = null;

    public ZookeeperClient() throws Exception {
        zk = ZookeeperFactory.getinstance();
    }
    public ZookeeperClient(String server) throws Exception {
        zk = ZookeeperFactory.getinstance(server);
    }
    public long getSessionId() {
        return zk.getSessionId();
    }
    public String create(String path,String value) throws Exception {
        return zk.create(path, getByte(value), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    public String createWithSession(String path,String value) throws Exception {
        return zk.create(path, getByte(value), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }
    public boolean exists(String path) throws Exception {
        return zk.exists(path,null)!=null;
    }
        public void delete(String path) throws Exception {
        if(exists(path)) {
            List<String> children=getChildren(path);
            if(children!=null&&children.size()!=0){
                for(String child:children){
                    delete(path+"/"+child);
                }
            }

            zk.delete(path, -1);
        }
    }

    public String getData(String path) throws Exception {
        if(exists(path))
                return new String(zk.getData(path, null,null));

        return null;
    }
    public void setData(String path,String data) throws Exception {
        if(exists(path))
            zk.setData(path, getByte(data), -1);
     }

    public List<String> getChildren(String path) throws Exception {
        if(exists(path))
            return zk.getChildren(path,false);

        return null;
    }

    private  byte[] getByte(String data){
        byte[] dataByte=null;
        if(data!=null){
            dataByte=data.getBytes();
        }else{
            dataByte=new byte[0];
        }
        return dataByte;
    }
    public void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String createNodes(String path, String data, CreateMode createMode) throws Exception {
//            //创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
//            zk.create("/root", "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            //在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
//            zk.create("/root/childone", "childone".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        return  zk.create(path, getByte(data), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
    }

    public void updateNodes(String path, String data) throws Exception {
        if(!exists(path)){
            createNodes(path,data,CreateMode.PERSISTENT);
        }else{
            setData(path, data);
        }
    }




    public static void main(String[] args) {
        try {
            ZookeeperClient util = new ZookeeperClient();
//            util.createNodes("/uumai",null,CreateMode.PERSISTENT);
//            util.createNodes("/uumai/config",null,CreateMode.PERSISTENT);
//            util.createNodes("/uumai/config/resourcepoolsize",null,CreateMode.PERSISTENT);
//            util.createNodes("/uumai/config/resourcepoolsize/amazon.com","10",CreateMode.PERSISTENT);
//            List<String> children= util.getChildren("/uumai/config/resourcepoolsize");
//            for(String child:children){
//                System.out.println("child:" + child);
//            }
//
            util.createNodes("/uumai/session",null,CreateMode.PERSISTENT);
            String data=util.getData("/uumai/config/uumai.mongodb.MONGO_URI");
            System.out.println("data:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
