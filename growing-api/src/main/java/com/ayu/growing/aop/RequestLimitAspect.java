package com.bilibili.growing.aop;

import com.bilibili.growing.annotation.RequestLimit;
import com.bilibili.growing.exception.ExceptionEnum;
import com.bilibili.growing.feign.RedisService;
import com.bilibili.growing.utils.WebKit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Order(20)
public class RequestLimitAspect {

    @Autowired
    private RedisService redisService;


    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void firstPointcut() {
    }

    @Pointcut("@annotation(com.bilibili.growing.annotation.RequestLimit)")
    public void secondPointcut() {

    }


    @Before("firstPointcut() && secondPointcut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        /* 获取目标方法的参数列表 */
        Object[] args = joinPoint.getArgs();
        System.out.println(args.toString());
        String ip = WebKit.getHttpRequest().getRemoteUser();
        String uri = WebKit.getHttpRequest().getRequestURI();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //获取注解
        RequestLimit limit = method.getAnnotation(RequestLimit.class);

        System.out.println(limit.count()+limit.time());

        String key = ip + uri;
        Long count = redisService.increment(key, 1L);

        if(count<=1){
            redisService.expire(key,Long.valueOf(limit.time()));
        }
        if(count>limit.count()){
            throw ExceptionEnum.INTERNAL_ERROR.createException("该用户请求过于频繁");
        }

    }
}
