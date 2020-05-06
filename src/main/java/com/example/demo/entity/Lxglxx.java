package com.example.demo.entity;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: Lxglxx
 * @Author: sxtc
 * @Description: Lxglxx
 * @Date: 2020/4/25 16:01
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.util.Date;

/**
 * 录像关联信息表t_hjjxl_yspsjcj_lxglxx对应的实体类
 */
public class Lxglxx {
    private Integer lxbh;//录像编号
    private String sjid;//事件编号
    private String spsbbh;//视频设备编号
    private Date kssj;//开始时间
    private Date jssj;//结束时间
    private Integer lxzt;//录像状态：0-未开始 1-已开始 2-已结束 3-录像异常
    private String lxrwbh;//录像任务编号

    public Integer getLxbh() {
        return lxbh;
    }

    public void setLxbh(Integer lxbh) {
        this.lxbh = lxbh;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getSpsbbh() {
        return spsbbh;
    }

    public void setSpsbbh(String spsbbh) {
        this.spsbbh = spsbbh;
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public Integer getLxzt() {
        return lxzt;
    }

    public void setLxzt(Integer lxzt) {
        this.lxzt = lxzt;
    }

    public String getLxrwbh() {
        return lxrwbh;
    }

    public void setLxrwbh(String lxrwbh) {
        this.lxrwbh = lxrwbh;
    }
}
