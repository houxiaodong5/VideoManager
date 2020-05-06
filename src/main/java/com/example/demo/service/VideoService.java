package com.example.demo.service;

import com.example.demo.entity.ResultEntity;
import com.example.demo.entity.Sjbqxx;
import com.example.demo.entity.Wbczrzxx;
import com.example.demo.vo.StartVideoVo;
import com.example.demo.vo.StopVideoVo;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.service
 * @ClassName: VideoService
 * @Author: sxtc
 * @Description: VideoService
 * @Date: 2020/4/24 12:04
 * @LatestUpdate:
 * @Version: 1.0
 */
public interface VideoService {

    //去数据库表t_hjjxl_yspsjcj_xtyxcspz查询定时任务间隔时间
    void getScheduledCs();

    //摄像机开启录像
    String startVideo(String cameraIndexCode);

    //摄像机开启录像
    Integer stopVideo(String cameraIndexCode,String taskId);

    //开始扫描系统中未进行和已进行工作的摄像机
    void starCan();

    //开始录像
    ResultEntity start(ResultEntity resultEntity, StartVideoVo vo);

    //未进行拍摄摄像机定时管理
    void dealWjxVideo();

    //未进行拍摄摄像机定时管理
    void dealYjxVideo();

    //查询录像时间信息表
    void selectLXsjxx();

    //停止录像
    ResultEntity stop(ResultEntity resultEntity, StopVideoVo vo);

    //查询事件标签信息
    ResultEntity getSJBQInfo(Sjbqxx sjbqxx);

    //添加用户操作日志
    void addWbczrz(Wbczrzxx wbczrzxx);
}
