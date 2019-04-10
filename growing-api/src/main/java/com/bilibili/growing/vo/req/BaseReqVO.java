package com.bilibili.growing.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class BaseReqVO implements Serializable {
    /**
     * 请求追踪id
     */
    @NotBlank(message = "请求追踪id不能为空")
    @ApiModelProperty(value = "请求追踪id", required = true)
    private   String    traceId;
    /**
     * 请求时间戳
     */
    @ApiModelProperty(value = "请求时间戳", required = true)
    @NotNull(message = "请求时间戳不能为空")
    private   Long      timestamp;
    /**
     * 请求接口版本，目前默认1.0
     */
    @ApiModelProperty(value = "请求接口版本，目前默认1.0", required = true)
    @NotNull(message = "请求接口版本不能为空")
    private   String    version;
    /**
     * 签名类型，目前默认MD5
     */
    @ApiModelProperty(value = "签名类型，目前默认MD5", required = true)
    @NotBlank(message = "签名类型不能为空")
    private   String    signType;
    /**
     * 请求签名串
     */
    @ApiModelProperty(value = "请求签名串", required = true)
    @NotBlank(message = "请求签名串不能为空")
    private   String    sign;
}
