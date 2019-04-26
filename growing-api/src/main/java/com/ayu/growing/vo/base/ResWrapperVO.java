package com.ayu.growing.vo.base;

import com.ayu.growing.exception.ExceptionEnum;
import com.ayu.growing.exception.RequestException;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

public class ResWrapperVO<T> {

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

    public ResWrapperVO<T> fail(ExceptionEnum error, T data) {
        this.errno = error.getCode();
        this.msg = error.getMsg();
        this.showMsg = error.getShowMsg();
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
    public ResWrapperVO<T> fail(RequestException error,String message, T data) {
        this.errno = error.getCode();
        this.msg = message;
        this.showMsg = error.getShowMsg();
        this.data = data;
        return this;
    }
    @SuppressWarnings("unchecked")
    public ResWrapperVO fail(ExceptionEnum exc, String msg) {
        return ResWrapperVO.createResponse().fail(exc.createException(),msg);
    }

    public ResWrapperVO<T> fail(Long code, String msg, T data) {
        this.errno =code;
        this.msg = msg;
        this.showMsg = msg;
        this.data = data;
        return this;
    }


    /**
     * 成功返回结果
     *
     */
    public  ResWrapperVO<T> success() {
        this.errno = ExceptionEnum.SUCCESS.getCode();
        this.msg = ExceptionEnum.SUCCESS.getMsg();
        this.showMsg = "";
        return this;
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public ResWrapperVO<T> fail(RequestException errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public ResWrapperVO<T> fail(ExceptionEnum exception) {
        return fail(exception.getCode(), exception.getMsg(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public  ResWrapperVO<T> fail(String message) {
        return this.fail(ExceptionEnum.INTERNAL_ERROR.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public  ResWrapperVO<T> fail() {
        return fail(ExceptionEnum.INTERNAL_ERROR);
    }

    /**
     * 参数验证失败返回结果
     */
    public  ResWrapperVO<T> validateFailed() {
        return fail(ExceptionEnum.INTERNAL_ERROR);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public  ResWrapperVO<T> validateFailed(String message) {
        return fail(ExceptionEnum.INTERNAL_ERROR.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public   ResWrapperVO<T> unauthorized(T data) {
        return fail(ExceptionEnum.SIGN_ERROR.getCode(), ExceptionEnum.SIGN_ERROR.getMsg(), data);
    }


    /**
     * 未授权返回结果
     */
    public   ResWrapperVO<T> forbidden(T data) {
        return fail(ExceptionEnum.INTERNAL_ERROR.getCode(), ExceptionEnum.INTERNAL_ERROR.getMsg(), data);
    }

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
