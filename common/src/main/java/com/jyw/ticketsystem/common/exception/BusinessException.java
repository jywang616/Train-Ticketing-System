package com.jyw.ticketsystem.common.exception;

public class BusinessException extends RuntimeException{

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum anEnum) {
        this.e = anEnum;
    }

    public BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    private  BusinessExceptionEnum e;
}
