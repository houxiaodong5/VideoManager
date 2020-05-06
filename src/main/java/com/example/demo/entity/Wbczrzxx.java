package com.example.demo.entity;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: Wbczrzxx
 * @Author: sxtc
 * @Description: Wbczrzxx
 * @Date: 2020/4/26 14:43
 * @LatestUpdate:
 * @Version: 1.0
 */

import java.util.Date;

/**
 * 外部操作日志信息表对应实体类
 */
public class Wbczrzxx {

    private String rzlx;//日志类型
    private Integer rzbh;//日志编号
    private String czjk;//操作接口
    private String czlx;//操作类型
    private String xtbh;//系统编号
    private String jqmc;//机器名称
    private String yhzh;//用户账号
    private Date czsj;//操作时间
    private Date xysj;//响应时间

    public String getRzlx() {
        return rzlx;
    }

    public void setRzlx(String rzlx) {
        this.rzlx = rzlx;
    }

    public Integer getRzbh() {
        return rzbh;
    }

    public void setRzbh(Integer rzbh) {
        this.rzbh = rzbh;
    }

    public String getCzjk() {
        return czjk;
    }

    public void setCzjk(String czjk) {
        this.czjk = czjk;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getXtbh() {
        return xtbh;
    }

    public void setXtbh(String xtbh) {
        this.xtbh = xtbh;
    }

    public String getJqmc() {
        return jqmc;
    }

    public void setJqmc(String jqmc) {
        this.jqmc = jqmc;
    }

    public String getYhzh() {
        return yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Date getXysj() {
        return xysj;
    }

    public void setXysj(Date xysj) {
        this.xysj = xysj;
    }
}
