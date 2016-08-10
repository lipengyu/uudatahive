package com.uumai.crawer.util.audio;

/**
 * Created by rock on 11/4/15.
 */

import java.io.*;
import java.net.URL;
import sun.audio.*;


public class Player {
    private AudioStream  as; //单次播放声音用
    ContinuousAudioDataStream cas;//循环播放声音
    // 构造函数
    public Player(String filename)
    {
        try {
            InputStream in = new FileInputStream(filename);

            //打开一个声音文件流作为输入
            as = new AudioStream (in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 一次播放 开始
    public void start()
    {
        if( as==null ){
            System.out.println("AudioStream object is not created!");
            return;
        }else{
            AudioPlayer.player.start (as);
        }
    }
    // 一次播放 停止
    public void stop()
    {
        if( as==null ){
            System.out.println("AudioStream object is not created!");
            return;
        }else{
            AudioPlayer.player.stop(as);
        }
    }
    // 循环播放 开始
    public void continuousStart()
    {
        // Create AudioData source.
        AudioData data = null;
        try {
            data = as.getData();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Create ContinuousAudioDataStream.
        cas = new ContinuousAudioDataStream (data);

        // Play audio.
        AudioPlayer.player.start(cas);
    }
    // 循环播放 停止
    public void continuousStop()
    {
        if(cas != null)
        {
            AudioPlayer.player.stop (cas);
        }
    }

    public static void  main(String[] args){
        Player player=new Player("/home/rock/kanxg/tools/pidgin-2.10.11/share/sounds/alert.wav");
        player.start();
    }

}
