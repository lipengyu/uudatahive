package com.uumai.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by rock on 2/8/16.
 */
public class CuratorDao {
    private CuratorFramework client=null;
    public void start(){
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

        client = builder.connectString("192.168.11.56:2180")
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace("test")
                .defaultData(null)
                .build();
        client.start();
    }

    public void close(){
        client.close();
    }
}
