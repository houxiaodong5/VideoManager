package com.example.demo.entity;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: ResultEntity
 * @Author: sxtc
 * @Description: ResultEntity
 * @Date: 2020/4/24 12:07
 * @LatestUpdate:
 * @Version: 1.0
 */
public class ResultEntity {

  private String systemNum;//调用方系统编号
  private String machineName;//调用方机器名字
  private String userAccount;//调用方登录账号
  private Long timeFlag;//时间戳
  private Object data;
  private Integer code;
  private String message;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(Long timeFlag) {
        this.timeFlag = timeFlag;
    }

    public String getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(String systemNum) {
        this.systemNum = systemNum;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "systemNum='" + systemNum + '\'' +
                ", machineName='" + machineName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", timeFlag=" + timeFlag +
                ", data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
