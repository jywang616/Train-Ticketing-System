package com.jyw.ticketsystem.common.exception;


//区分系统异常和业务异常，所以新增此类
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册");

    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                '}'+super.toString();
    }
}
