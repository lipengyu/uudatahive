package com.uumai.crawer2.CookieManager;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rock on 12/11/15.
 */
public class CrawlerCookie implements Serializable {
    private  String name;
    private  String value;
    private  String path;
    private  String domain;
    private  Date expiry;
    private  boolean isSecure;
    private  boolean isHttpOnly;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public void setSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }

    public void setHttpOnly(boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }


    @Override
    public String toString() {
        return "CrawlerCookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", path='" + path + '\'' +
                ", domain='" + domain + '\'' +
                ", expiry=" + expiry +
                ", isSecure=" + isSecure +
                ", isHttpOnly=" + isHttpOnly +
                '}';
    }
}
