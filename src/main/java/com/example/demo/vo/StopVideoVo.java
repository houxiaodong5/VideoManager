package com.example.demo.vo;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.vo
 * @ClassName: StopVideoVo
 * @Author: sxtc
 * @Description: StopVideoVo
 * @Date: 2020/4/26 10:30
 * @LatestUpdate:
 * @Version: 1.0
 */

import com.example.demo.pojo.Event;

import java.util.List;

/**
 * 停止录像的入参实体类
 */
public class StopVideoVo {
    private List<Event> cameraIndexCodes;//视频编号集合
    private String machineName;//调用方机器名称
    private String userAccount;//调用方登录账号
    private String systemNum;//系统编号



    public List<Event> getCameraIndexCodes() {
        return cameraIndexCodes;
    }

    public void setCameraIndexCodes(List<Event> cameraIndexCodes) {
        this.cameraIndexCodes = cameraIndexCodes;
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
}
