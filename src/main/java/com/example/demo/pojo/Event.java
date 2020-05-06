package com.example.demo.pojo;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.pojo
 * @ClassName: StopVideo_pojo
 * @Author: sxtc
 * @Description: StopVideo_pojo
 * @Date: 2020/4/26 10:31
 * @LatestUpdate:
 * @Version: 1.0
 */

/**
 * 停止录像的入参集合中存放的实体类
 */
public class Event {
    private String eventId;//事件编号
    private String taskID;//任务编号
    private String cameraIndexCode;//摄像机编号

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }
}
