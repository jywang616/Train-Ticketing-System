package com.jyw.ticketsystem.member.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
//默认扫描当前包和子孙包的所有类
@ComponentScan("com.jyw")
public class MemberApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);
    public static void main(String[] args){
        SpringApplication app=new SpringApplication(MemberApplication.class);
        Environment env= app.run(args).getEnvironment();
        LOG.info("启动成功啦~");
        LOG.info("项目地址：\thttp://127.0.0.1:{}",env.getProperty("server.port"));
    }


}
