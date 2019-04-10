package com.ayu.growing.exception;

public enum ExceptionEnum {
    SUCCESS(0L, "SUCCESS"),
    SIGN_ERROR(8004010001L, "SIGN_ERROR"),
    MISSING_CUSTOMERID(8004010002L, "MISSING_CUSTOMERID"),
    MISSING_PARAMS(8004010003L, "MISSING_PARAMS", "必要参数缺失"),
    INTERNAL_ERROR(8004010004L, "INTERNAL_ERROR"),
    SHARDING_CONFIG_BAD(8004010005L,"SHANDING_ERROR"),
    TEST_TIME_OUT_ERROR(8999999999L, "testTimeoutError!");//TODO


    /**
     * 错误码
     */
    private Long code;
    /**
     * 错误信息
     */
    private String msg;

    private String showMsg;

    ExceptionEnum(Long code, String msg) {
        this(code, msg, COMMON_SHOW_ERROR_MSG);
    }

    ExceptionEnum(Long code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

    public Long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public RequestException createExceptionWithData(Object data) {
        return new RequestException(this.code, this.msg, this.showMsg, data);
    }

    public RequestException createException() {
        return new RequestException(this.code, this.msg, this.showMsg);
    }

    public RequestException createException(String msg) {
        return new RequestException(this.code, msg, msg);
    }

    public RequestException createExceptionMsg(String msg) {
        return new RequestException(this.code, msg);
    }

    public RequestException createExceptionShowMsg(String showMsg) {
        return new RequestException(this.code, this.msg, showMsg);
    }

    public String getShowMsg() {
        return showMsg;
    }

    public final static String COMMON_SHOW_ERROR_MSG = "服务器开小差了";
}
