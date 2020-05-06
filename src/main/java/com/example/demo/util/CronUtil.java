package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.util
 * @ClassName: CronUtil
 * @Author: sxtc
 * @Description: CronUtil
 * @Date: 2020/4/24 12:41
 * @LatestUpdate:
 * @Version: 1.0
 */
public class CronUtil {

    /**
     * 根据时间获取定时任务表达式
     * @param sj
     * @return
     */
    public static String getCron(Integer sj){
        String corn="";
        if(sj/60>0){
            String m=String.valueOf(sj/60);
            corn="0 0/"+m+" * * * ?";
        }else{
            String s=String.valueOf(sj);
            corn="1/"+s+" * * * * ?";
        }
        return corn;
    }
}
