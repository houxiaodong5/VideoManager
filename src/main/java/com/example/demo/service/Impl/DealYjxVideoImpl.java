package com.example.demo.service.Impl;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.service.Impl
 * @ClassName: DealYjxVideoImpl
 * @Author: sxtc
 * @Description: DealYjxVideoImpl
 * @Date: 2020/4/27 8:59
 * @LatestUpdate:
 * @Version: 1.0
 */

import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理已进行的摄像机集合
 */
@Component
public class DealYjxVideoImpl implements Runnable{

    @Autowired
    private VideoService videoService;

    @Override
    public void run() {
        videoService.dealYjxVideo();
    }
}
