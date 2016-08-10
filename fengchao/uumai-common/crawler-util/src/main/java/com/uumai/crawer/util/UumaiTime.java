package com.uumai.crawer.util;

import com.uumai.crawer.util.math.MathUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * Created by rock on 7/24/15.
 */
public class UumaiTime {
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    JodaTime jodaTime=new JodaTime();
//    Java8Time java8Time=new Java8Time();

//    public LocalDateTime getNow(){
//        return  java8Time.getNow();
//
//    }

    public DateTime getNow(){
        return  jodaTime.getNow();
    }


    public String getNowString(){
         return jodaTime.getNowString();

    }
    public float getTimeDuration(String startime,String endtime){
//		return joda_fmt.parseDateTime(endtime) -joda_fmt.parseDateTime(startime);
        try {
            float duration=df.parse(endtime).getTime()-df.parse(startime).getTime();
            duration= duration / (1000 * 60);
            return duration;
//			return MathUtils.multiply(dr, "24");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1f;
    }
    public static void main(String[] args){


        UumaiTime uumaiTime=new UumaiTime();
        System.out.println(uumaiTime.getNow());

        String newupdatetime=uumaiTime.getNowString();
        System.out.println(newupdatetime);


    }
}
