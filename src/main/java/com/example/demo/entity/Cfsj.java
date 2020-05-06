package com.example.demo.entity;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: Cfsj
 * @Author: sxtc
 * @Description: Cfsj
 * @Date: 2020/4/24 14:26
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.util.Date;

/**
 * 存放摄像机开始和结束录像的时间
 */
public class Cfsj {
    private Long startTime;//摄像机开始拍摄时间
    private Long endTime;//摄像机结束拍摄时间

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
