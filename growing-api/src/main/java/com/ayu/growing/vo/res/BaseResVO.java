package com.ayu.growing.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(value = "BaseResVO", description = "基础响应对象")
public class BaseResVO implements Serializable {
    /**
     * 请求追踪id
     */
    @ApiModelProperty(value = "请求追踪id", required = true)
    private String traceId;
    /**
     * 服务器时间戳
     */
    @ApiModelProperty(value = "服务器时间戳", required = true)
    private Long timestamp;

}