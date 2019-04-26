package com.ayu.growing.aop;

import com.ayu.growing.exception.ExceptionEnum;
import com.ayu.growing.vo.base.ResWrapperVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BindingResultAspect {
    @Pointcut("execution(public * com.ayu.growing.web.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if(fieldError!=null){
                        return ResWrapperVO.createResponse().fail(ExceptionEnum.MISSING_PARAMS,fieldError.getDefaultMessage());
                    }else{
                        return ResWrapperVO.createResponse().fail();
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
