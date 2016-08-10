package com.uumai.crawer.util.filesystem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Created by rock on 4/22/15.
 */
public class UumaiHdfsUtil {

    private FileSystem getFileSystem(String uri) throws Exception {
        Configuration config = new Configuration();
        config.setBoolean("fs.hdfs.impl.disable.cache", true);
        return FileSystem.get(URI.create(uri), config);
    }

    public String readfromcacheFromHDFS(String uri, String filename) {
        FileSystem fs = null;
        FSDataInputStream is = null;
        StringBuffer sb = new StringBuffer();
        try {

            fs = getFileSystem(uri);

            // check if the file exists
            Path path = new Path(filename);
            if (fs.exists(path)) {
                is = fs.open(path);
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String s = null;
                while (true) {
                    s = rd.readLine();
                    if (s == null) {
                        break;
                    } else {
                        sb.append(s);
//                        System.out.println(s);
                    }
                }
                is.close();
                fs.close();

                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
//            try {
//                if(fs!=null)     fs.close();
//            } catch (IOException e) {
//            }
        }


        return null;
    }

    public void save2fileHDFS(String uri, String filename, String html) {
        FileSystem hdfs = null;
        FSDataOutputStream os = null;

        try {

            hdfs = getFileSystem(uri);


            os = hdfs.create(new Path(filename));

            os.write(html.getBytes("UTF-8"));

            os.close();

            hdfs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
            }
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }
    }

    public void deleteHdfsFile(String uri, String hdfspath) throws IOException {
        FileSystem hdfs = null;

        try {
            hdfs = getFileSystem(uri);

            // 获取要删除的文件
            Path delefPath = new Path(hdfspath);
            boolean isDeleted = false;

            // 检查文件是否存在，若存在，递归删除
            if (hdfs.exists(delefPath)) {
                isDeleted = hdfs.delete(delefPath, true);
                // 递归删除
            } else {
                isDeleted = false;
                System.out.println("文件不存在：删除失败");
            }
            System.out.println("Delete?" + isDeleted);

            hdfs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }


    }

    public void uploadToHdfs(String local, String uri, String hdfspath)
            throws IOException {
        FileSystem hdfs = null;
        FSDataOutputStream os = null;

        try {

            hdfs = getFileSystem(uri);

            // 读取本地文件
            FileInputStream fis = new FileInputStream(new File(local));
            os = hdfs.create(new Path(hdfspath));
            // 复制
            IOUtils.copyBytes(fis, os, 4096, true);

            fis.close();
            os.close();
            hdfs.close();

            System.out.println("拷贝完成...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
            }
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }


    }

    public boolean exist(String uri, String hdfspath){
        FileSystem hdfs = null;

        try {
            hdfs = getFileSystem(uri);

            Path delefPath = new Path(hdfspath);
            return hdfs.exists(delefPath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }
        return false;
    }

    public void readFromHdfs(String uri,String hdfspath , String dest)
            throws IOException {
        FileSystem hdfs = null;
        FSDataInputStream hdfsInStream = null;

        try {

            hdfs = getFileSystem(uri);

            // 打开文件流
            hdfsInStream = hdfs.open(new Path(hdfspath));

            // 写入本地文件系统
            OutputStream out = new FileOutputStream(dest);

            byte[] ioBuffer = new byte[1024];

            // 按行读取
            int readLen = hdfsInStream.read(ioBuffer);

            while (-1 != readLen) {
                out.write(ioBuffer, 0, readLen);
//                System.out.println(new String(ioBuffer));
                readLen = hdfsInStream.read(ioBuffer);

            }

            out.close();

            hdfsInStream.close();

            hdfs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (hdfsInStream != null) hdfsInStream.close();
            } catch (IOException e) {
            }
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }




    }

    public FileStatus[]     getDirectoryFromHdfs(String uri,String hdfspath) throws IOException {
        FileSystem hdfs = null;
        FileStatus[] fileList=null;
        try {

            hdfs = getFileSystem(uri);

            fileList = hdfs.listStatus(new Path(hdfspath));
            hdfs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }
        return fileList;
    }

    public void moveToLocal(String uri,String path,String localpath) throws  Exception{
        FileStatus[] filelist=getDirectoryFromHdfs(uri,path);
        for(FileStatus fs:filelist){
                System.out.println("name:" + fs.getPath().getName()
                        + "\t\tsize:" + fs.getLen());
                copyToLocal(uri,true,fs,localpath);

        }
    }
    public void copyToLocal(String uri,boolean deletesrc,FileStatus fs,String localpath) throws  Exception{
        FileSystem hdfs = null;
        try {

            hdfs = getFileSystem(uri);
            hdfs.copyToLocalFile(deletesrc,fs.getPath(),new Path(localpath));

             hdfs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (hdfs != null) hdfs.close();
            } catch (IOException e) {
            }
        }

    }

    public  void cleansmallfile(String uri,String path, long filesize) throws  Exception{
        FileStatus[] filelist=getDirectoryFromHdfs(uri,path);
        for(FileStatus fs:filelist){
            if(fs.isDirectory()){
                cleansmallfile(uri,fs.getPath().toString(),filesize);
            }else{
                if(fs.getLen()<filesize){
                    System.out.println("name:" + fs.getPath().getName()
                            + "\t\tsize:" + fs.getLen());
                    deleteHdfsFile(uri,fs.getPath().toString());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        UumaiHdfsUtil UumaiHdfsUtil=new UumaiHdfsUtil();
//        UumaiHdfsUtil.uploadToHdfs("/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/crawler-core/pom.xml","hdfs://rock-cdc-server:9000","/home/rock/kanxg/downloadfiles/amazon1/pom.xml");
       //UumaiHdfsUtil.readFromHdfs("hdfs://rock-cdc-server:9000","/hadoop/a.a","/home/rock/tmp/a.a");
        String url="hdfs://rock-cdc-server:9000";
        String path="/uumai/userdata/cloudinfotech/linkedin/";
        UumaiHdfsUtil.cleansmallfile(url,path,50*1024);

//        UumaiHdfsUtil.moveToLocal(url,"/uumai/userdata/linkedin/cn_0511/","/home/rock/uumai/linkedin/cn/");

    }
}
