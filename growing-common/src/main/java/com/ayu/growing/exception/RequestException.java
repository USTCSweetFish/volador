package com.ayu.growing.exception;

public class RequestException extends RuntimeException {


    public RequestException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public RequestException(Long code, String message, String showMsg) {
        super(message);
        this.code = code;
        this.showMsg = showMsg;
    }

    public RequestException(Long code, String message, String showMsg, Object data) {
        super(message);
        this.code = code;
        this.showMsg = showMsg;
        this.data = data;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    private Long code;

    private String showMsg;

    private Object data;

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
