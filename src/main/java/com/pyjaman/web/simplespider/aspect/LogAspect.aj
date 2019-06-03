package com.pyjaman.web.simplespider.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.pyjaman.web.simplespider.service..*.*(..))")
    public void pointcutMethod(){}

    @Around("pointcutMethod()")
    public String doAround(ProceedingJoinPoint joinPoint) {
       long startTime = System.currentTimeMillis();
        String count = null;
        try {
            count = (String)joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        LOGGER.debug("此次爬取共耗时"+(endTime-startTime)+"毫秒，共入库地区行政区划信息数据"+count+"条。");
        return "此次爬取共耗时"+(endTime-startTime)+"毫秒，共入库地区行政区划信息数据"+count+"条。";
    }
}
