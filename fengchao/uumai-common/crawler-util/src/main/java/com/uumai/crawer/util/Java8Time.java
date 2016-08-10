package com.uumai.crawer.util;

import com.uumai.crawer.util.math.MathUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by rock on 10/26/15.
 */
public class Java8Time {

    DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime getNow(){

        LocalDateTime now = LocalDateTime.now();
        return  now;
    }

    public String getNowString(){
        LocalDateTime now = LocalDateTime.now();
        return format.format(now);

    }

    public float getTimeDuration(String startime){
        return this.getTimeDuration(startime,getNowString());
    }
    /**
     *
     * @param startime
     * @param endtime
     * @return  分钟
     */
    public float getTimeDuration(String startime,String endtime){
//		return joda_fmt.parseDateTime(endtime) -joda_fmt.parseDateTime(startime);
        try {
            float duration=df.parse(endtime).getTime()-df.parse(startime).getTime();
//            String dr= MathUtils.round(new Double(duration).toString(), 2, 1);

//			return MathUtils.multiply(dr, "24");
            return duration/(1000*60);
//            return dr;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args){


        Java8Time java8Time=new Java8Time();
//        System.out.println(java8Time.getNow());

//        String newupdatetime=java8Time.getNowString();
//        System.out.println(newupdatetime);
        float dura=java8Time.getTimeDuration("2016-03-08 10:33:43","2016-03-08 11:41:43");
        System.out.println(dura);



    }
}
