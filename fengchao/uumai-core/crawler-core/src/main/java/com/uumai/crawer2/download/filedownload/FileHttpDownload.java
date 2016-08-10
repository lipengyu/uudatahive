package com.uumai.crawer2.download.filedownload;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * Created by rock on 5/11/16.
 */
public class FileHttpDownload {
    private int poolsize=1;
    private Proxy proxy;
    private String cookie;
    private ExecutorService executor;

    public FileHttpDownload(){
        startpool();
    }
    public FileHttpDownload(int poolsize,String cookie,Proxy proxy){
        this.poolsize=poolsize;
        this.cookie=cookie;
        this.proxy=proxy;
        startpool();
    }
    private void startpool(){
        executor = Executors.newFixedThreadPool(poolsize);
    }
    public void stoppool(){
        ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor)executor;

        while(true){
            //System.out.println(" current queue size:" + threadPoolExecutor.getQueue().size());
            //use for DefaultFixThreadPool for ArrayBlockingQueue
            if (threadPoolExecutor.getActiveCount()!=0){
                //use for DefaultFixThreadPool fix pool size
//             if (threadPoolExecutor.getQueue().size() > defaultFixThreadPool.getQueuesize()){
                try {
                    System.out.println(this.getClass().getCanonicalName() + " pool full,wait! ");
//                    sender.sendmsg(" pool full,wait! ");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                break;
            }
        }


        executor.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println(this.getClass().getCanonicalName() + " Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
            System.err.println(this.getClass().getCanonicalName() + " Pool interrupt");
        }
    }
    public void sendtasker(String url, String savefile){
        executor.execute(new Worker(url,savefile));
    }



    private class Worker extends  Thread{
        private String url;
        private String savefile;

        public Worker(String url,String savefile){
            this.url=url;
            this.savefile=savefile;
        }
        public void run(){
            try{
                String html=download(url);
                savefile(html,savefile);
             }catch(Exception e){
                e.printStackTrace();
            }

        }
        private String download(String urlStr) throws Exception{

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection =null;
            if(proxy==null){
                urlConnection=(HttpURLConnection) url
                        .openConnection();
            }else{
                urlConnection=(HttpURLConnection) url
                        .openConnection(proxy);
            }
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0");
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");

            if(cookie!=null)
                urlConnection.setRequestProperty("Cookie", cookie);

            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000*2);

            urlConnection.connect();
            int return_code=urlConnection.getResponseCode();
            if (return_code != 200) {

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                InputStream es = urlConnection.getErrorStream();
                int ret = 0;
                // read the response body
                while ((ret = es.read(buf)) > 0) {
                    os.write(buf, 0, ret);
                }
                // close the errorstream
                es.close();
                throw new Exception("Error when download:"+return_code +",Error:"+ new String(os.toByteArray()));
            }
            String encoding=urlConnection.getContentType();
            if(encoding!=null){
                if(encoding.indexOf("charset=")==-1){
                    encoding=null;
                }else{
                    encoding=encoding.substring(encoding.indexOf("charset=")+8).trim();
                }
            }



            InputStream is=null;
            if ("gzip".equals(urlConnection.getContentEncoding())) {
                is = new GZIPInputStream(
                        urlConnection.getInputStream());
            }else{
                is=urlConnection.getInputStream();
            }

            //is = urlConnection.getInputStream();
            BufferedReader reader = null;

            if (encoding==null){
                reader=new BufferedReader(
                        new InputStreamReader(is,"utf-8"));
            }else{
                reader=new BufferedReader(
                        new InputStreamReader(is,encoding));
            }



            String s;
            StringBuilder result = new StringBuilder();
            while (((s = reader.readLine()) != null)) {
                result.append(s);
            }

            reader.close();

            return result.toString();
        }
        private void savefile(String html,String savefile) throws  Exception{

                File file = new File(savefile);// 要写入的文本文件
                file.createNewFile();
                FileWriter writer = new FileWriter(file);// 获取该文件的输出流
                writer.write(html);// 写内容
                writer.flush();// 清空缓冲区，立即将输出流里的内容写到文件里
                writer.close();// 关闭输出流，施放资源
        }

    }
}
