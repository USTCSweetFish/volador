package com.ayu.growing.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(com.ayu.growing.datasource.DataSource)")
    public void beforeSwitchDS(JoinPoint point) {

        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();

        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();

        String dataSource = DataSourceContextHolder.DEFAULT_DS;

        try {
            Method method = className.getMethod(methodName, argClass);

            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource source = method.getAnnotation(DataSource.class);

                dataSource = source.value();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        DataSourceContextHolder.setDS(dataSource);
    }

    @After("@annotation(com.ayu.growing.datasource.DataSource)")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.ClearDS();
    }

}
