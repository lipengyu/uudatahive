package com.uumai.crawer2.download;

import org.apache.http.HttpHost;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by kanxg on 14-12-21.
 */
public class CrawlerProxy implements Serializable {

    private String ip;
    private int port;


    public CrawlerProxy(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Proxy getproxy(){
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }

    public HttpHost gethttpclientproxy(){
        return new HttpHost(ip, port, "http");
    }

    public String getProxyIpAndPortString(){
        return ip+":"+port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "CrawlerProxy{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}

