package com.example.demo.config.Scheduld;

import com.example.demo.service.Impl.DealWjxVideoImpl;
import com.example.demo.service.Impl.DealYjxVideoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.config.Scheduld
 * @ClassName: RunableContro
 * @Author: sxtc
 * @Description: RunableContro
 * @Date: 2020/4/24 14:02
 * @LatestUpdate:
 * @Version: 1.0
 */
@Component
public class RunableContro {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;


    @Autowired
    private DealWjxVideoImpl dealwjxVideo;

    @Autowired
    private DealYjxVideoImpl dealyjxVideo;


    private ScheduledFuture<?> future1;

    private ScheduledFuture<?> future2;


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(50);

        /**需要实例化线程*/
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }


    public void startCron1(String cron) {
        future1 = threadPoolTaskScheduler.schedule(dealwjxVideo, new CronTrigger(cron));
        System.out.println("DynamicTask.startCron1()");
    }

    public void stopCron1() {
        if (future1 != null) {
            future1.cancel(false);
        }
    }
    public void startCron2(String cron) {
        future2 = threadPoolTaskScheduler.schedule(dealyjxVideo, new CronTrigger(cron));
        System.out.println("DynamicTask.startCron1()");
    }

    public void stopCron2() {
        if (future2 != null) {
            future2.cancel(false);
        }
    }
}
