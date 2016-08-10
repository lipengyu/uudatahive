package com.uumai.crawer2.download.remoteshelldownload;

import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.download.Download;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by rock on 4/10/16.
 */
public class RemoveShellDownload implements Download {
    @Override
    public CrawlerResult download(CrawlerTasker tasker) throws Exception {

        //copy jar from HDFS to local /tmp

        CrawlerResult result = new CrawlerResult();
        String[] cmd = {"/bin/sh", "-c", tasker.getUrl()};

        try {
            Process p0 = Runtime.getRuntime().exec(cmd);
            //读取标准输出流
            StringBuffer osb = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p0.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                osb.append(line);
            }
            //读取标准错误流
            StringBuffer esb = new StringBuffer();
            BufferedReader brError = new BufferedReader(new InputStreamReader(p0.getErrorStream()));
            String errline = null;
            while ((errline = brError.readLine()) != null) {
//                System.out.println(errline);
                esb.append(errline);
            }
            //waitFor()判断Process进程是否终止，通过返回值判断是否正常终止。0代表正常终止
            int returnkey = p0.waitFor();
            result.setReturncode(returnkey);
            result.setRawText(osb.toString() + esb.toString());
        } catch (Exception e1) {
            result.setRawText(e1.getMessage());
            result.setReturncode(-1);
        }
        return result;
    }
    private void copyHDFS2local(String hdfsurl,String localdir){

    }
    private void cleantasker(){
        //remove tmp dir

    }
}