package com.example.demo.config;

import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: JKXL
 * @Package: com.example.demo.config
 * @ClassName: SpringbootInit
 * @Author: sxtc
 * @Description: SpringbootInit
 * @Date: 2020/4/22 15:00
 * @LatestUpdate:
 * @Version: 1.0
 */

/**
 * 项目启动开始执行
 */
@Configuration
public class SpringbootInit implements ApplicationRunner {


    @Autowired
    private VideoService videoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //项目启动开始调用查询数据库定时任务时间
        videoService.getScheduledCs();
        //查询数据库待开启和已开启的摄像机信息
        videoService.selectLXsjxx();
        //开启定时任务
        videoService.starCan();
    }
}
