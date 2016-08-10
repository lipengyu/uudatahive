package com.uumai.crawer.util.filesystem;

import com.uumai.crawer.util.UumaiProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: rock
 * Date: 3/12/15
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class UumaiFileUtil {
    UumaiHdfsUtil uumaiHdfsUtil=new UumaiHdfsUtil();
    UumaiMongoFileUtil uumaiMongoFileUtil=new UumaiMongoFileUtil();
    /**
     * this is called by CrawlerWorker to store and read html files.
     * Crawler will store it to HDFS or linux file system
     * @param filename
     * @return
     */
    public String readfromcache(String filename){
        if(filename.startsWith("hdfs:")){
            String uri=UumaiProperties.readconfig("uumai.hdfs.uri","hdfs://localhost:9000");
            return uumaiHdfsUtil.readfromcacheFromHDFS(uri, filename.substring(5));
        } else if(filename.startsWith("mongo:")){
            String uri=UumaiProperties.readconfig("uumai.mongodb.MONGO_URI", "mongodb://localhost:27017");
            return uumaiMongoFileUtil.readfrommongodb(uri, filename.substring(6));
        }
        else{
            File file = new File(filename);
            if(file.exists()){
                return readfromcacheFromLocal(file);
            }
        }
        return null;
    }

    public void save2file(String filename,String html){
        if(html==null||"".equals(html)){
            System.out.println("file :" + filename + " is no content, will ignore save!");
            return;
        }
        if(filename.startsWith("hdfs:")){
           String uri=UumaiProperties.readconfig("uumai.hdfs.uri","hdfs://localhost:9000");
            uumaiHdfsUtil.save2fileHDFS(uri, filename.substring(5), html);
        }else if(filename.startsWith("mongo:")){
             String uri=UumaiProperties.readconfig("uumai.mongodb.MONGO_URI", "mongodb://localhost:27017");
            uumaiMongoFileUtil.save2mongodb(uri, filename.substring(6), html);
        }else{
            save2fileLocal(filename,html);
        }
    }
    public boolean exist(String filename){
        if(filename.startsWith("hdfs:")){
            String uri=UumaiProperties.readconfig("uumai.hdfs.uri","hdfs://localhost:9000");
           return  uumaiHdfsUtil.exist(uri, filename.substring(5));
        }else if(filename.startsWith("mongo:")){
            String uri=UumaiProperties.readconfig("uumai.mongodb.MONGO_URI", "mongodb://localhost:27017");
           return uumaiMongoFileUtil.exist(uri, filename.substring(6));
        }else{
          return   existinLocal(filename);
        }

    }
    public void delete(String filename){
        if(!exist(filename)) return;
        if(filename.startsWith("hdfs:")){
            String uri=UumaiProperties.readconfig("uumai.hdfs.uri","hdfs://localhost:9000");
            try {
                uumaiHdfsUtil.deleteHdfsFile(uri, filename.substring(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(filename.startsWith("mongo:")){
            String uri=UumaiProperties.readconfig("uumai.mongodb.MONGO_URI", "mongodb://localhost:27017");
            try {
                uumaiMongoFileUtil.delete(uri, filename.substring(6));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
             delete(filename);
        }

    }
    public  boolean existinLocal(String filename){
        return new File(filename).exists();
    }
    public String readfromcacheFromLocal(File file){
        try {
            StringBuffer buffer=new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString() ;

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;

    }

 public void save2fileLocal(String filename,String html){

        try {
            File file = new File(filename);// 要写入的文本文件
            if (file.exists()) {
                return;
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);// 获取该文件的输出流
            writer.write(html);// 写内容
            writer.flush();// 清空缓冲区，立即将输出流里的内容写到文件里
            writer.close();// 关闭输出流，施放资源
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean readfromzipcache(File file){

        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
            ZipEntry ze;

            //2. 逐个处理条目
            while ((ze = zin.getNextEntry()) != null) {
                //测试： 输出条目的信息
                System.out.printf("条目信息： 名称%1$b, 大小%2$d, 压缩时间%3$d \n", ze.getName(), ze.getSize(), ze.getTime());

                //3. 操作文件内容-输出、处理
                FileInputStream fin = new FileInputStream(ze.getName());
                byte[] buffer = new byte[4096];
                int len;
                while ((len = fin.read(buffer)) != -1) {
                    System.out.print(new String(buffer, 0, len));
                }
                zin.closeEntry();
            }
            zin.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) throws Exception {
            UumaiFileUtil util=new UumaiFileUtil();
//        util.save2file("hdfs:/uumai/userdata/cloudinfotech/linkedin/test1.txt","tttt");
//    String text=util.readfromcacheFromLocal(new File("/home/rock/uumai/downloads/test.txt"));
//        util.save2fileHDFS("hdfs://rock-cdc-server:9000","/uumai/userdata/cloudinfotech/linkedin","tttt");
           //util.save2file("hdfs:/home/rock/kanxg/downloadfiles/amazon/test.txt","fafsdfasd");
//          String text=util.readfromcacheFromHDFS("hdfs://rock-cdc-server:9000","/home/rock/kanxg/downloadfiles/amazon/test.txt");
        //String text=util.readfromcache("hdfs:/home/rock/kanxg/downloadfiles/amazon/test.txt");
//             System.out.println("text is:" + text);
//        util.save2file("mongo:/uumai/userdata/cloudinfotech/linkedin/test1.txt","tttt");
        String text=util.readfromcache("mongo:/uumai/userdata/cloudinfotech/linkedin/test1.txt");
        System.out.println("text is:" + text);
    }

    }
