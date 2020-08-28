package com.train.netty.vo;

import com.train.netty.enums.ErrorCodeEnum;

/**
 * Author: yuzzha
 * Date: 2020-08-28 17:28
 * Description: ${DESCRIPTION}
 */
public class ResponseVo<T>  {
    private int resCode;
    private String resMsg;
    private T data;

    public int getResCode() {
        return this.resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return this.resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.resCode == 0;
    }

    public ResponseVo() {
    }

    public ResponseVo(T data) {
        this.data = data;
    }

    public ResponseVo(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo(data);
    }

    public static <T> ResponseVo<T> success() {
        return new ResponseVo((Object)null);
    }

    public static ResponseVo fail(ErrorCodeEnum errorCode) {
        return new ResponseVo(errorCode.getCode(), errorCode.getDesc());
    }

    public static ResponseVo fail(ErrorCodeEnum errorCode, String errorMessage) {
        return new ResponseVo(errorCode.getCode(), errorMessage);
    }
}
