package com.rg;

import com.rg.config.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2024/7/28 18:46
 * Canal原理：基于二进制日志（binlog）进行数据增量同步。
 * 1. 模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave。向 MySQL master 发送dump 协议。
 * 2. master 收到 dump 请求，开始推送 binary log 给 slave。
 * 3. canal 解析 binary log 对象。
 * 流程和搭建主从基本上类似的~
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }
}