package com.train.netty.enums;

/**
 * Author: yuzzha
 * Date: 2020-08-28 17:30
 * Description: ${DESCRIPTION}
 */
public enum ErrorCodeEnum {
    /**
     *
     */
    API_ERROR(-1000, "系统错误"),
    API_ERROR_DATA_NOT_EXITS(-1001, "数据不存在"),
    SYSTEM_ERROR(-20000, "系统错误");

    private int code;
    private String desc;

    private ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
