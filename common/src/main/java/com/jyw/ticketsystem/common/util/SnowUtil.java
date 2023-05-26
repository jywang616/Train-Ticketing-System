package com.jyw.ticketsystem.common.util;

import cn.hutool.core.util.IdUtil;

//封装了雪花算法
//为了优雅哈哈哈哈哈哈哈哈哈哈哈哈（bushi）
//为了隐藏数据中心和机器标识的参数罢了）））
//讲点道理不用也行
public class SnowUtil {

    private static long dataCenterId = 1;
    private static long workerId = 1;

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
    }
}
