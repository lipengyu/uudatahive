package com.uumai.crawer.util.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: rock
 * Date: 3/17/15
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Shell {
    public int exec(String command){
        String[] cmd = { "/bin/sh","-c",command};

        int returnkey=0;
        try {
            Process p0 = Runtime.getRuntime().exec(cmd);
            //读取标准输出流
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(p0.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            //读取标准错误流
            BufferedReader brError = new BufferedReader(new InputStreamReader(p0.getErrorStream()));
            String errline = null;
            while ((errline = brError.readLine()) != null) {
                System.out.println(errline);
            }
            //waitFor()判断Process进程是否终止，通过返回值判断是否正常终止。0代表正常终止
            returnkey=p0.waitFor();

        } catch (Exception e1) {
            e1.printStackTrace();
            return returnkey=-1 ;
        }
        return returnkey;
    }

    public static   void main(String[] args) {
        Shell shell=new Shell();
       // String[] cmd = { "/bin/sh", "-c", "mvn install  ; mkdir /installation/upgrade/" };
        System.out.println(shell.exec("ping www.baidu.com"));
    }

}
