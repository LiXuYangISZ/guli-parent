package com.rg.staservice.controller;


import com.rg.commonutils.R;
import com.rg.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     *  统计某一台你的注册人数，生成统计数据
     * @param day
     * @return
     */
    @ApiOperation("人数统计")
    @PostMapping("/registerCount/{day}")
    public R registerCount(@ApiParam(name = "day", value = "查询日期", required = true) @PathVariable("day") String day){
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok();
    }
}

