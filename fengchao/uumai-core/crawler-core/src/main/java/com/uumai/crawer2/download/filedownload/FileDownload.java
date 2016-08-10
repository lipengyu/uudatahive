package com.uumai.crawer2.download.filedownload;

import com.uumai.crawer.util.filesystem.UumaiFileUtil;
import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.Download;

/**
 * Created by rock on 11/16/15.
 */
public class FileDownload implements Download {
    @Override
    public CrawlerResult download(CrawlerTasker tasker) throws Exception {
        UumaiFileUtil uumaiFileUtil=new UumaiFileUtil();

        CrawlerResult result=new CrawlerResult();
        result.setRawText(uumaiFileUtil.readfromcache(tasker.getSavefilename()));
        result.setReturncode(200);

        return result;
//        return null;
    }
}
