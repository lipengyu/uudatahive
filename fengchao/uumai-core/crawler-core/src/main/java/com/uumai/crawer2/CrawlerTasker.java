package com.uumai.crawer2;

import com.uumai.crawer.util.UumaiProperties;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer.util.io.SerializeUtil;
import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawer2.download.CrawlerProxy;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import com.uumai.crawer2.download.Download;
import com.uumai.crawer2.download.selenium.SeleniumScriptBase;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanxg on 14-12-18.
 */
public class CrawlerTasker  implements Comparable<Object> ,Serializable ,Writable {

    private static final long serialVersionUID = 1L;

//    int retrytime;

    private String taskerName;
    private String taskerSeries;
    private String taskerOwner;

    //for activeMQ
    private String JMSMessageID;
    //for redis keep origal message;
    private String RedisOrigMessage;
    private String RedisBackupKey;


    private String url;

    private  Download.DownloadType downloadType;

    private  String requestmethod="GET";
    private boolean follingRedirect=true;

    private  String postdata;
    private  String encoding;



    private String crawler_type;

    private CrawlerProxy proxy ;



    private List<CrawlerCookie> cookies;
    private boolean keepCookies=false;

    private String savefilename;
    private boolean useingcache;

    private List<String> requestHeaderList;

    private String[] failProcessLogic;


    private SeleniumScriptBase seleniumScriptBase;


    public  CrawlerTasker(){

    }

    public void init(){
//        retrytime= new Integer(UumaiProperties.readconfig("uumai.crawler.retrytime","-1"));
//        maxRetryTimes_distributed= new Integer(UumaiProperties.readconfig("uumai.crawler.maxRetryTimes_distributed","-1"));



        //check tasker name and series.
        if(this.getTaskerName()==null){
            this.setTaskerName(this.getClass().getPackage().toString());
        }
        if(this.getTaskerSeries()==null){
            this.setTaskerSeries(new UumaiTime().getNowString());
        }
        if(this.getDownloadType()==null){
            this.setDownloadType(Download.DownloadType.java_download);
        }
    }

    public void addRequestHeader(String header,String value){
        if("".equals(header)) return;
        if("".equals(value)) value=null;

        if(this.getRequestHeaderList()==null){
            this.requestHeaderList=new ArrayList<String>();
        }

        this.requestHeaderList.add(header+":"+value);
    }

    public Download.DownloadType getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(Download.DownloadType downloadType) {
        this.downloadType = downloadType;
    }

//    for distribute retry times
//    private int maxRetryTimes_distributed;
//    private int retryTimes_distributed=0;


//        public int getMaxRetryTimes_distributed() {
//		return maxRetryTimes_distributed;
//	}
//
//	public void setMaxRetryTimes_distributed(int maxRetryTimes_distributed) {
//		this.maxRetryTimes_distributed = maxRetryTimes_distributed;
//	}
//
//	public int getRetryTimes_distributed() {
//		return retryTimes_distributed;
//	}
//
//	public void setRetryTimes_distributed(int retryTimes_distributed) {
//		this.retryTimes_distributed = retryTimes_distributed;
//	}

    public boolean isUseingcache() {
        return useingcache;
    }

    public void setUseingcache(boolean useingcache) {
        this.useingcache = useingcache;
    }

    public String getSavefilename() {
        return savefilename;
    }

    public void setSavefilename(String savefilename) {
        this.savefilename = savefilename;
    }

    public List<CrawlerCookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<CrawlerCookie> cookies) {
        this.cookies = cookies;
    }
//    public int getRetrytime() {
//        return retrytime;
//    }
//
//    public void setRetrytime(int retrytime) {
//        this.retrytime = retrytime;
//    }

    public String getUUID(){
        return url;
    }

    public String getPostdata() {
        return postdata;
    }

    public void setPostdata(String postdata) {
        this.postdata = postdata;
    }

    public String getRequestmethod() {
        return requestmethod;
    }

    public void setRequestmethod(String requestmethod) {
        this.requestmethod = requestmethod;
    }


    public CrawlerProxy getProxy() {
        return proxy;
    }

    public void setProxy(CrawlerProxy proxy) {
        this.proxy = proxy;
    }

    public String getCrawler_type() {
        return crawler_type;
    }

    public void setCrawler_type(String crawler_type) {
        this.crawler_type = crawler_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getTaskerName() {
        return taskerName;
    }

    public void setTaskerName(String taskerName) {
        this.taskerName = taskerName;
    }

    public String getTaskerSeries() {
        return taskerSeries;
    }

    public void setTaskerSeries(String taskerSeries) {
        this.taskerSeries = taskerSeries;
    }


    public String getTaskerOwner() {
        return taskerOwner;
    }

    public void setTaskerOwner(String taskerOwner) {
        this.taskerOwner = taskerOwner;
    }

    public String getJMSMessageID() {
        return JMSMessageID;
    }

    public void setJMSMessageID(String JMSMessageID) {
        this.JMSMessageID = JMSMessageID;
    }

    public String getRedisOrigMessage() {
        return RedisOrigMessage;
    }

    public void setRedisOrigMessage(String redisOrigMessage) {
        RedisOrigMessage = redisOrigMessage;
    }

    public String getRedisBackupKey() {
        return RedisBackupKey;
    }

    public void setRedisBackupKey(String redisBackupKey) {
        RedisBackupKey = redisBackupKey;
    }

    public SeleniumScriptBase getSeleniumScriptBase() {
        return seleniumScriptBase;
    }

    public void setSeleniumScriptBase(SeleniumScriptBase seleniumScriptBase) {
        this.seleniumScriptBase = seleniumScriptBase;
    }
    public boolean isKeepCookies() {
        return keepCookies;
    }

    public void setKeepCookies(boolean keepCookies) {
        this.keepCookies = keepCookies;
    }

    public boolean isFollingRedirect() {
        return follingRedirect;
    }

    public void setFollingRedirect(boolean follingRedirect) {
        this.follingRedirect = follingRedirect;
    }

    public List<String> getRequestHeaderList() {
        return requestHeaderList;
    }


    public String[] getFailProcessLogic() {
        return failProcessLogic;
    }

    public void setFailProcessLogic(String[] failProcessLogic) {
        this.failProcessLogic = failProcessLogic;
    }

    public void addFailPorceeeLogis(String... failsolutioin){
        this.failProcessLogic=new String[failsolutioin.length];
        for(int i=0;i<failsolutioin.length;i++){
            failProcessLogic[i]=failsolutioin[i];
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
//        Text.writeString(dataOutput, this.name);
//        dataOutput.writeInt(this.value);
        dataOutput.writeUTF(this.taskerName);
        dataOutput.writeUTF(this.taskerSeries);
        dataOutput.writeUTF(this.taskerOwner);
        dataOutput.writeUTF(this.url);
        dataOutput.writeUTF(this.requestmethod);
        dataOutput.writeBoolean(this.follingRedirect);
        dataOutput.writeUTF(this.postdata);
        dataOutput.writeUTF(this.encoding);
        dataOutput.writeUTF(this.crawler_type);
        dataOutput.writeBoolean(this.keepCookies);
        dataOutput.writeUTF(this.savefilename);
        dataOutput.writeBoolean(this.useingcache);


        dataOutput.writeUTF(this.url);

//        Text.writeString(dataOutput, SerializeUtil.serialize(this));
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

//        this.name= Text.readString(dataInput);
//        this.value=dataInput.readInt();

        CrawlerTasker p1=(CrawlerTasker)SerializeUtil.unserialize(Text.readString(dataInput));
//        try {
//            BeanUtils.copyProperties(this,p1);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            throw new IOException("can't copy properties");
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//            throw new IOException("can't copy properties");
//        }
        this.setUrl(p1.getUrl());


    }



}
