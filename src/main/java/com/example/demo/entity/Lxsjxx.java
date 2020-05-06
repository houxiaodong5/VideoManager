package com.example.demo.entity;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: Lxsjxx
 * @Author: sxtc
 * @Description: Lxsjxx
 * @Date: 2020/4/26 9:28
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.util.Date;

/**
 * 录像时间信息表对应实体类
 */
public class Lxsjxx {

    private String spsbbh;//视频设备编号
    private Date zxlxsj;//最小录像时间
    private Date zdlxsj;//最大录像时间
    private Integer zt;//摄像机状态
    private String taskId;//任务编号

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSpsbbh() {
        return spsbbh;
    }

    public void setSpsbbh(String spsbbh) {
        this.spsbbh = spsbbh;
    }

    public Date getZxlxsj() {
        return zxlxsj;
    }

    public void setZxlxsj(Date zxlxsj) {
        this.zxlxsj = zxlxsj;
    }

    public Date getZdlxsj() {
        return zdlxsj;
    }

    public void setZdlxsj(Date zdlxsj) {
        this.zdlxsj = zdlxsj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }
}
