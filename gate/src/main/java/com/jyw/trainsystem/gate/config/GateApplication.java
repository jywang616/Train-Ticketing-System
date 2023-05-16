package com.jyw.trainsystem.gate.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
//默认扫描当前包和子孙包的所有类
@ComponentScan("com.jyw")
public class GateApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GateApplication.class);
    public static void main(String[] args){
        SpringApplication app=new SpringApplication(GateApplication.class);
        Environment env= app.run(args).getEnvironment();
        LOG.info("启动成功啦~");
        LOG.info("网关地址：\thttp://127.0.0.1:{}",env.getProperty("server.port"));
    }


}
