package com.example.demo.vo;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.vo
 * @ClassName: StartVideoVo
 * @Author: sxtc
 * @Description: StartVideoVo
 * @Date: 2020/4/25 13:26
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.util.Arrays;
import java.util.Date;

/**
 * 开启录像的传入参数实体类
 */
public class StartVideoVo {
    private String[] cameraIndexCodes;//视频编号集合
    private Long beginTime=new Date().getTime();//摄像机开始录像的时间
    private Long endTime;//摄像机停止录像的时间
    private String machineName;//调用方机器名称
    private String userAccount;//调用方登录账号
    private String systemNum;//系统编号
    private String eventId;//事件编号

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String[] getCameraIndexCodes() {
        return cameraIndexCodes;
    }

    public void setCameraIndexCodes(String[] cameraIndexCodes) {
        this.cameraIndexCodes = cameraIndexCodes;
    }

    public Long getBeginTime() {
        if (beginTime==null || beginTime==0L){
            beginTime=new Date().getTime();
        }
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(String systemNum) {
        this.systemNum = systemNum;
    }

    @Override
    public String toString() {
        return "StartVideoVo{" +
                "cameraIndexCodes=" + Arrays.toString(cameraIndexCodes) +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", machineName='" + machineName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", systemNum='" + systemNum + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
