package com.rg.staservice.controller;


import com.rg.commonutils.R;
import com.rg.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Leo
 * @since 2024-07-25
 */
@RestController
@RequestMapping("/staservice/daily")
@CrossOrigin
@Api(description = "日常统计")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     *  统计某一天的注册人数，生成统计数据
     * @param day
     * @return
     */
    @ApiOperation("人数统计")
    @PostMapping("/registerCount/{day}")
    public R registerCount(@ApiParam(name = "day", value = "查询日期", required = true) @PathVariable("day") String day){
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok();
    }

    /**
     * 图标显示统计信息，返回两部分数据：日期数据、数量数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("图标显示统计信息")
    @GetMapping(value = "showData/{type}/{begin}/{end}")
    public R showData(@PathVariable("type") String type,@PathVariable("begin") String begin, @PathVariable("end") String end){
        Map<String, Object> map = statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

