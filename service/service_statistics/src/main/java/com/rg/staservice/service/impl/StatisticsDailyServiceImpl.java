package com.rg.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.staservice.client.UcenterClient;
import com.rg.staservice.entity.StatisticsDaily;
import com.rg.staservice.mapper.StatisticsDailyMapper;
import com.rg.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.WJcovUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Leo
 * @since 2024-07-25
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    @Transactional
    public void createStatisticsByDay(String day) {
        // 移除脏数据【最新的覆盖旧的】
        LambdaQueryWrapper <StatisticsDaily> queryWrapper = new LambdaQueryWrapper <>();
        queryWrapper.eq(StatisticsDaily::getDateCalculated,day);
        this.baseMapper.delete(queryWrapper);

        // 统计
        Integer count = ucenterClient.registerCount(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setCourseNum(RandomUtils.nextInt(500,1000));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(500, 1000));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(500,1000));
        statisticsDaily.setDateCalculated(day);
        this.baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map <String, Object> getShowData(String type, String begin, String end) {
        LambdaQueryWrapper <StatisticsDaily> queryWrapper = new LambdaQueryWrapper <>();
        queryWrapper.between(StatisticsDaily::getDateCalculated,begin,end);
        ArrayList <Integer> dataList = new ArrayList <>();
        ArrayList <String> monthList = new ArrayList <>();
        List <StatisticsDaily> statisticsDailyList = this.baseMapper.selectList(queryWrapper);
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            monthList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "login_num" :
                    dataList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num" :
                    dataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        HashMap <String, Object> map = new HashMap <>();
        map.put("dataList",dataList);
        map.put("monthList",monthList);
        return map;
    }
}
