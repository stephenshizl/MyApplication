package com.yxhuang.carapplication.car.exception;

/**
 * 自定义异常
 * 系统中所有异常都转化为Exception
 * Created by Administrator on 2015/10/10.
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    public BaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }
}
