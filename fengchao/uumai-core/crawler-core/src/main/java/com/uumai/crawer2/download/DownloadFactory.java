package com.uumai.crawer2.download;

import com.uumai.crawer2.download.emptymock.EmptyMockDownload;
import com.uumai.crawer2.download.filedownload.FileDownload;
import com.uumai.crawer2.download.httpclient.HttpClientDownload;
import com.uumai.crawer2.download.httpdownload.HttpDownload;
import com.uumai.crawer2.download.selenium.SeleniumDownloader;
import com.uumai.crawer2.download.shelldownload.ShellDownload;

/**
 * Created by rock on 8/17/15.
 */
public class DownloadFactory {
    public static synchronized  Download getnewDownload(Download.DownloadType type){
//        if(type== Download.DownloadType.java_download){
//            return new HttpDownload();
//        }else
//
        if(type== Download.DownloadType.httpclient_download){
            return new HttpClientDownload();
        }else if(type== Download.DownloadType.chrome_download){
            return new SeleniumDownloader();
        }else if(type== Download.DownloadType.firefox_download){
            return new SeleniumDownloader();
        }else if(type== Download.DownloadType.openscript_download){

        }else if(type== Download.DownloadType.emptymockdown){
            return new EmptyMockDownload();
        }else if(type== Download.DownloadType.file_download){
            return new FileDownload();
        }else if(type== Download.DownloadType.shell_download){
            return new ShellDownload();
        }else if(type== Download.DownloadType.htmlunit_download){
            return new SeleniumDownloader();
        }else if(type== Download.DownloadType.phantomjs_download){
            return new SeleniumDownloader();
        }
        return new HttpDownload();
    }
}
