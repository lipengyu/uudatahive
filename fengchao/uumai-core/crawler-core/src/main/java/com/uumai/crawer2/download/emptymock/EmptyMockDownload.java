package com.uumai.crawer2.download.emptymock;

import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.Download;

/**
 * Created by rock on 9/2/15.
 */
public class EmptyMockDownload implements Download {
    @Override
    public CrawlerResult download(CrawlerTasker tasker) throws Exception {
        CrawlerResult result=new CrawlerResult();
        result.setRawText(tasker.getUrl());
        result.setReturncode(200);
        return result;
    }
}
