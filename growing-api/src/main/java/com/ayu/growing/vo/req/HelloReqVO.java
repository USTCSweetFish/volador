package com.ayu.growing.vo.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "HelleoReqVO", description = "xxx请求对象")
@Data
public class HelloReqVO implements Serializable {
    @NotNull
    private int length;

    @NotBlank
    private String name;

}
