package com.jyw.ticketsystem.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SpringBootTestJob {
    //随便输出的字符串 5秒输出一次 当前秒/5余0就触发
    //但没办法更改定时任务状态
    @Scheduled(cron = "0/5 * * * * ?")
    private void test() {
        // 增加分布式锁可以解决集群问题
        System.out.println("TEST");
    }
}