package com.uumai.crawer.quartz.localdebug;

import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.localdebug.LocalDebugAppMaster;

public class QuartzLocalDebugAppMaster extends LocalDebugAppMaster{
	
	
    public void putDistributeTask(CrawlerTasker crawlerTasker)  throws Exception {
    	QuartzLocalDebugCrawlerWorker localDebugCrawlerWorker=    new QuartzLocalDebugCrawlerWorker(crawlerTasker);
        localDebugCrawlerWorker.download();
        localDebugCrawlerWorker.pipeline();
    }

//	@Override
//	public void dobusiness() throws Exception {
//		super.dobusiness();
//	}

}
