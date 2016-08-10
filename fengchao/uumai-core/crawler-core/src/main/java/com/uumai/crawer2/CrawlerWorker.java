package com.uumai.crawer2;

/**
 * Created by rock on 8/4/16.
 */
public class CrawlerWorker implements  Runnable {
    public CrawlerTasker tasker;
    public CrawlerResult result;

    public CrawlerWorker(CrawlerTasker tasker) {
        this.tasker=tasker;
    }

    public void run(){
//        while (true){
//            CrawlerTasker tasker=this.pool.pollTask(); //queue.poll();
        if(tasker==null){
//                try {
            System.out.println("tasker is null!");
            //Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
        }else{
            System.out.println("Thread "+ Thread.currentThread().getName() +" do task "+tasker.getUrl());
//                System.out.println("Thread "+ Thread.currentThread().getName() +" start do tasker:" + new UumaiTime().getNowString());
            try {
                tasker.init();

                dobusiness();
//                    System.out.println("Thread "+ Thread.currentThread().getName() +" finish do tasker:" + new UumaiTime().getNowString());
            } catch (Exception e) {
                try {
                     e.printStackTrace();
                 } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }



        }


//        }

    }

    protected void dobusiness() throws Exception{
        download();
        pipeline();

    }
    protected void download() throws  Exception {
    }
    protected void pipeline() throws Exception{
    }
}
