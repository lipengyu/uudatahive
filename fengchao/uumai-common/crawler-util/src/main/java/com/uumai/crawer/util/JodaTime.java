package com.uumai.crawer.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

import static java.util.TimeZone.*;

/**
 * Created by rock on 10/26/15.
 */
public class JodaTime {

    static DateTimeFormatter joda_fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");//自定义日期格式

    public DateTime getNow(){
        DateTime in = new DateTime();
        return in.now();

//        DateTime dt = new DateTime();
//        DateTime dtlocal = dt.withZone(default_zone);
//        return dtlocal;

    }

    public String getNowString(){
//        java.util.Date juDate = new Date();
//        fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        return fmt.format(juDate);

        DateTime in = new DateTime();
//        return in.toString();
       return  in.now().toString(joda_fmt); //使用自定义的日期格式化当期日期*/
        // return joda_fmt.withZone(DateTimeZone.getDefault()).print(in.getMillis());
//        joda_fmt.withZone(default_zone);
//        return joda_fmt.print(in);

//        DateTime dt = new DateTime();
//        DateTime dtlocal = dt.withZone(default_zone);
//        return dtlocal.toString(fmt);
        //fmt.setTimeZone(TimeZone.getDefault());
//        fmt.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//        return fmt.format(juDate.getTime());

//        Calendar cal = Calendar.getInstance();
//        return fmt.format(cal.getTime());



    }
    public DateTime getDatetime(String s){
        return joda_fmt.parseDateTime(s);
    }

    public String getDatetimeString(String s){
        return joda_fmt.parseDateTime(s).toString(joda_fmt);
    }


    public static void main(String[] args){

        System.out.println("default 5 time zone:"+ getDefault());
        System.out.println(new Date());



        System.out.println("default jdoa time zone:"+ DateTimeZone.getDefault());
        System.out.println(new DateTime()) ;


        JodaTime jodaTime=new JodaTime();
        System.out.println(jodaTime.getNow());

        String newupdatetime=jodaTime.getNowString();
        System.out.println(newupdatetime);

//        String newupdatetime= uumaiTime.getDatetimeString("2015-07-11 04:00:11");
//                System.out.println(newupdatetime);  //国际标准时间

//        DateTime in = new DateTime();
//
//        System.out.println(in.now());  //国际标准时间
//
//        System.out.println(in.getYear()); //当年
//
//        System.out.println(in.getMonthOfYear()); //当月
//
//        System.out.println(in.getDayOfMonth());  //当月第几天
//
//        System.out.println(in.getDayOfWeek());//本周第几天
//
//        System.out.println(in.getDayOfYear());//本年第几天
//
//        System.out.println(in.getHourOfDay());//时
//
//        System.out.println(in.getMinuteOfHour());//分
//
//        System.out.println(in.getMinuteOfDay());//当天第几分钟
//
//        System.out.println(in.getSecondOfMinute());//秒
//
//        System.out.println(in.getSecondOfDay());//当天第几秒
//
//        System.out.println(in.getWeekOfWeekyear());//本年第几周
//
//        System.out.println(in.getZone());//所在时区
//
//        System.out.println(in.dayOfWeek().getAsText()); //当天是星期几，例如：星期五
//
//        System.out.println(in.yearOfEra().isLeap()); //当你是不是闰年，返回boolean值
//
//        System.out.println(in.dayOfMonth().getMaximumValue());//当月day里面最大的值
//
//
////        3.2    更改日期格式
//        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");//自定义日期格式
//
//        in.now().toString(fmt); //使用自定义的日期格式化当期日期
//
//
////        3.3    日期比较：
//
//        DateTime in2 = new DateTime(in.getMillis() + 10);
//
//        in.equals(in2);  //false
//
//        in.compareTo(in2); //-1
//
//        in.isEqual(in2); //false
//
//        in.isAfter(in2); //false
//
//        in.isBefore(in2);  //true
    }
}
