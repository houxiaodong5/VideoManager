package com.example.demo.dao;

import com.example.demo.entity.Lxglxx;
import com.example.demo.entity.Lxsjxx;
import com.example.demo.entity.Sjbqxx;
import com.example.demo.entity.Wbczrzxx;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.mapper
 * @ClassName: VideoMapper
 * @Author: sxtc
 * @Description: VideoMapper
 * @Date: 2020/4/24 12:05
 * @LatestUpdate:
 * @Version: 1.0
 */
@Mapper
public interface VideoMapper {

    //t_hjjxl_yspsjcj_xtyxcspz查询定时任务间隔时间
    String selectScheduledCs(@Param("xtyxcsbs") String i);

    //添加事件
    void insertSj(Sjbqxx sjbqxx);

    //添加录像关联信息表
    void insertLxglxx(Lxglxx lxglxx);

    //根据视频设备编号修改录像管理信息表的状态和任务编号
    void updateLXglxxBySBBH(Lxglxx lxglxx);

    //修改录像时间信息表
    void updateLXsjxx(Lxsjxx lxsjxx);

    //添加录像时间信息表
    void insertLxsjxx(Lxsjxx lxsjxx);

    //根据摄像机编号删除录像时间信息表
    void deleteLxsjxx(@Param("spsbbh") String key);

    //根据摄像机状态查询摄像机信息
    List<Lxsjxx> selectLxsjxx(@Param("zt") int i);

    //根据条件查询数据列表
    List<Sjbqxx> selectSJbqInfo(Sjbqxx sjbqxx);

    //查询符合条件的数据条数
    Integer selectSJbqCount(Sjbqxx sjbqxx);

    //添加外部操作日志信息
    void insertWbczrzxx(Wbczrzxx wbczrzxx);

    //修改事件标签信息
    void updateSj(Sjbqxx sjbqxx);
}
