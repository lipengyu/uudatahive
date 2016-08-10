package com.uumai.crawer2.download;

import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;

/**
 * Created by kanxg on 14-12-22.
 */
public interface Download {


    public enum DownloadType{
        java_download, httpclient_download,
        firefox_download, chrome_download, htmlunit_download,phantomjs_download,
        emptymockdown,
        openscript_download,
        file_download,
        shell_download
    }
    public CrawlerResult download(CrawlerTasker tasker) throws Exception ;

}
