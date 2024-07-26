package com.rg.staservice.service;

import com.rg.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Leo
 * @since 2024-07-25
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatisticsByDay(String day);
}
