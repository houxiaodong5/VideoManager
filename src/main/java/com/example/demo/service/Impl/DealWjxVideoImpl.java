package com.example.demo.service.Impl;

import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.service
 * @ClassName: DealVideoImpl
 * @Author: sxtc
 * @Description: DealVideoImpl
 * @Date: 2020/4/24 13:58
 * @LatestUpdate:
 * @Version: 1.0
 */

/**
 * 定时处理未进行录像的摄像机集合
 */
@Component
public class DealWjxVideoImpl implements Runnable{

    @Autowired
    private VideoService videoService;

    @Override
    public void run() {
        videoService.dealWjxVideo();

    }
}
