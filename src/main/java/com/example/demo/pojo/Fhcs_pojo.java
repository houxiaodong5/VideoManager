package com.example.demo.pojo;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.pojo
 * @ClassName: Fhcs_pojo
 * @Author: sxtc
 * @Description: Fhcs_pojo
 * @Date: 2020/4/26 10:47
 * @LatestUpdate:
 * @Version: 1.0
 */

/**
 * 开启结束录像返回参数的pojo类
 */
public class Fhcs_pojo {
   private Integer code;//返回状态码
   private String  message; //返回消息
   private Long beginTime;//开始录像时间
   private String cameraIndexCode;//摄像机编号
   private Long endTime;//结束时间
   private String taskID;//任务编号
   private String eventId;//事件编号

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

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
