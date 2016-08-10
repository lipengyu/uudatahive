package com.uumai.crawer2.localdebug;

import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.CrawlerWorker;

/**
 * Created by rock on 12/9/15.
 */
public class LocalDebugAppMaster extends Thread{

    public LocalDebugAppMaster init() {
        return this;
    }

    @Override
    public void run() {

        try {
            dobusiness();
        } catch (Exception e) {
             e.printStackTrace();
        }

    }
    public void dobusiness()  throws Exception {

    }

    protected void putDistributeTask(String host,CrawlerTasker crawlerTasker)  throws Exception {
        this.putDistributeTask(crawlerTasker);
    }
    public void putDistributeTask(CrawlerTasker crawlerTasker)  throws Exception {
        LocalDebugCrawlerWorker localDebugCrawlerWorker=    new LocalDebugCrawlerWorker(crawlerTasker);
        localDebugCrawlerWorker.download();
        localDebugCrawlerWorker.pipeline();
    }
    protected void waittaskfinished()  throws Exception {

    }




}
