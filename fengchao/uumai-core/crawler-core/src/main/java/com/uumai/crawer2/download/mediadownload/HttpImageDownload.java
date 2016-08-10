package com.uumai.crawer2.download.mediadownload;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpHost;

/**
 * Created by rock on 1/17/16.
 */
public class HttpImageDownload {

    private static String cookie="__uuid=1452860341172.75; gr_user_id=1b8f68ff-1e91-43e9-b135-6c7f474a5f50; _uuid=45397E08FFA8420B3A5434706777DCC2; Hm_lvt_a2647413544f5a04f00da7eee0d5e200=1452860342,1453009619; user_login=641400585%40qq.com; user_kind=2; user_photo=559a41170cf234f5b592aa5403a.jpg; _fecdn_=1; __uv_seq=14; user_name=%E5%BC%A0%E4%BD%B3%E8%8E%B9; user_id=20888723; lt_auth=6uYMP3VQzQ%2BtsnDaiGVZ468f3d2vBm3P8HsKh0tVh9HvX6W24PziSwOPqbMExBEhwxMmf8ULNbX9%0D%0ANez9y3ZP60YW%0D%0A; verifycode=7b2134a119e4478d8975e3596568e855; __tlog=1452860341173.48%7C00000000%7C00000000%7C00000000%7C00000000; __session_seq=24; _mscid=00000000; Hm_lpvt_a2647413544f5a04f00da7eee0d5e200=1453043262; beta2_msg_closed=1; JSESSIONID=AE60A6338102892C5755C1570E7B7890; gr_session_id=2a4b22c1-d0ce-4340-9782-02cf566ac5f4";


    public static void main(String[] args) {

        HttpImageDownload download=new HttpImageDownload();

//        download.downloadPicture("http://img12.360buyimg.com/da/jfs/t2092/235/2038857536/59672/af6f6abc/56989958N1767c3be.jpg",
//                null,null,"/home/rock/Downloads/jd.jpg");
//        download.downloadPicture("http://h.liepin.com/image/createimg/?type=00&linkinfo=5672694189p3096668867|1",
//             cookie,null,"/home/rock/Downloads/liepin.jpg");

        download.downloadPicture("http://h.liepin.com/image/createimg/?type=11&linkinfo=5672694189p3096668867|1",
             cookie,null,"/home/rock/Downloads/email.jpg");

//        download.downloadPicture("http://h.liepin.com/weixin/getbindedqrcodes/?userc_id=25566059",
//                cookie,null,"/home/rock/Downloads/weixin.png");
    }


    public  void downloadPicture(String urlStr, String cookie,Proxy proxy,
                                       String filePath) {


        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection ;
            if(proxy==null){
                urlConnection=(HttpURLConnection) url
                        .openConnection();
            }else{
                urlConnection=(HttpURLConnection) url
                        .openConnection(proxy);
            }

            if(cookie!=null){
                urlConnection.setRequestProperty("Cookie", cookie);
            }
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5 * 1000);

            InputStream in = urlConnection.getInputStream();//通过输入流获取图片数据



//            savePicToDisk(in,  filePath);
            writeImageToDisk(readInputStream(in), filePath);

            System.out.println("save to: "+filePath );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static void writeImageToDisk(byte[] img, String fileName){
        try {
            File file = new File(fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入到C盘");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void savePicToDisk(InputStream in,  String filename) {

        try {

            File file = new File(filename);
            if (file == null || !file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
