package com.rg.staservice.service;

import com.rg.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Leo
 * @since 2024-07-25
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数，生成统计数据
     * @param day
     */
    void createStatisticsByDay(String day);

    /**
     * 图表显示统计信息
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
