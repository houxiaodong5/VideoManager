package com.example.demo.entity;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: Sjbqxx
 * @Author: sxtc
 * @Description: Sjbqxx
 * @Date: 2020/4/25 13:10
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间标签信息表对应的实体类
 */
public class Sjbqxx {
    private String sjid;//事件编号
    private String sjly;//事件来源
    private String sjlxdm;//事件类型代码 splx001
    private Date  sjkssj;//事件开始时间
    private Date sjjssj;//事件结束时间
    private String czlx;//操作类型  01 开始录像  02 结束录像
    private String jksxjbh;//监控摄像机编号
    private String sjyh;//事件用户
    private String czyh;//操作用户
    private String jqmc;//机器名称
    private Integer pageSize=10;//分页单位
    private Integer pageNum=1;//页码

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getSjlxdm() {
        return sjlxdm;
    }

    public void setSjlxdm(String sjlxdm) {
        this.sjlxdm = sjlxdm;
    }


    public Date getSjkssj() {
        return sjkssj;
    }

    public void setSjkssj(Date sjkssj) {
        this.sjkssj = sjkssj;
    }

    public Date getSjjssj() {
        return sjjssj;
    }

    public void setSjjssj(Date sjjssj) {
        this.sjjssj = sjjssj;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getJksxjbh() {
        return jksxjbh;
    }

    public void setJksxjbh(String jksxjbh) {
        this.jksxjbh = jksxjbh;
    }

    public String getSjyh() {
        return sjyh;
    }

    public void setSjyh(String sjyh) {
        this.sjyh = sjyh;
    }

    public String getCzyh() {
        return czyh;
    }

    public void setCzyh(String czyh) {
        this.czyh = czyh;
    }

    public String getJqmc() {
        return jqmc;
    }

    public void setJqmc(String jqmc) {
        this.jqmc = jqmc;
    }

}
