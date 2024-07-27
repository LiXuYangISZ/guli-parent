package com.rg.staservice.config;

import com.rg.staservice.service.StatisticsDailyService;
import com.rg.staservice.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lxy
 * @version 1.0
 * @Description 定时任务类
 * @date 2024/7/27 16:15
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 测试：每五秒执行一次
     */
    // @Scheduled(cron = "0/5 * * * * ?")
    // public void test1(){
    //     System.out.println("********************task1执行了....");
    // }

    /**
     * 每天凌晨一点，统计昨天的注册人数
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay(date);
    }
}
