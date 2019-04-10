package com.bilibili.growing.vo.req;


import com.bilibili.growing.exception.ExceptionEnum;
import com.bilibili.growing.exception.RequestException;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class ResWrapperVO<T> implements Serializable{
    public static ResWrapperVO createResponse() {
        return new ResWrapperVO();
    }

    public ResWrapperVO<T> success(T data) {
        this.errno = ExceptionEnum.SUCCESS.getCode();
        this.msg = ExceptionEnum.SUCCESS.getMsg();
        this.showMsg = "";
        this.data = data;
        return this;
    }

    public ResWrapperVO<T> fail(RequestException error, T data) {
        this.errno = error.getCode();
        this.msg = error.getMessage();
        this.showMsg = error.getShowMsg();
        this.data = data;
        return this;
    }

    public ResWrapperVO<T> fail(ExceptionEnum exc, String msg, T data) {
        this.errno = exc.getCode();
        String useMsg = StringUtils.isNotBlank(msg) ? msg : exc.getMsg();
        this.msg = useMsg;
        this.showMsg = useMsg;
        this.data = data;
        return this;
    }

    /**
     * 错误码, 0为正确响应
     */
    @ApiModelProperty(value = "错误码, 0为正确响应", required = true)
    private Long errno;

    /**
     * 错误信息，success为成功
     */
    @ApiModelProperty(value = "错误信息，success为成功", required = true)
    private String msg;

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    /**
     * 错误信息，success为成功
     */
    @ApiModelProperty(value = "客户端展示错误信息", required = true)
    private String showMsg;
    /**
     * 返回数据对象
     */
    @ApiModelProperty(value = "返回数据对象", required = true)
    private T data;

    public Long getErrno() {
        return errno;
    }

    public void setErrno(Long errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
