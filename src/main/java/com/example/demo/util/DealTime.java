package com.example.demo.util;

/**
 * @ProjectName: tim-server
 * @Package: com.atisafe.gngl.externalProvisionApi.utils
 * @ClassName: DealTime
 * @Author: sxtc
 * @Description: DealTime
 * @Date: 2020/3/29 12:37
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理时间的工具类
 */
public class DealTime {

    public static String getTime_Date(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
        String date = null;
        date = simpleDateFormat.format(new Date());
        return date;
    }

    public static Date dateRoll(Date date, String dw, int num) {
        // 获取Calendar对象并以传进来的时间为准
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将现在的时间滚动固定时长,转换为Date类型赋值
        if(dw.toUpperCase().equals("HOUR")) {
            calendar.add(Calendar.HOUR_OF_DAY, num);
        }else if(dw.toUpperCase().equals("MONTH")){
            calendar.add(Calendar.MONTH,num);
        }else if(dw.toUpperCase().equals("DAY")){
            calendar.add(Calendar.DAY_OF_MONTH,num);
        }
        // 转换为Date类型再赋值
        date = calendar.getTime();
        return date;
    }

    public static Date stringToDate(String date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = simpleDateFormat.parse(date);
            return parse;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将数字类型的时间戳转为String类型时间戳
     * @param t
     * @return
     */
    public static String InteToStringTime(long t){
        if (t==0){
            return null;
        }
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time3=sdf1.format(t);
        return time3;
    }
    public static Date InteToDateTime(Long t){
        Date parse = null;
        if(t==0L || t==null){
            return null;
        }
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time3 = sdf1.format(t);
            parse = sdf1.parse(time3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return parse;
    }

    public static void main(String[] args) {
        //  1585107267188L
        Date s = InteToDateTime(new Date().getTime());
        System.err.println(s);
    }
}
