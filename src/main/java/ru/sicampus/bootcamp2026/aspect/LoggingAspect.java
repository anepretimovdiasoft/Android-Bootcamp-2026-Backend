package ru.sicampus.bootcamp2026.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Around("@annotation (ru.sicampus.bootcamp2026.aspect.annotation.LogExample)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(">>> logging method: {} | params: {}",
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        logger.info(">>> complete method: {} | result: {}",
                joinPoint.getSignature().getName(), result);
        return result;
    }

    @AfterThrowing(pointcut = "@annotation (ru.sicampus.bootcamp2026.aspect.annotation.LogExample)", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Object error) { logger.error(error.toString()); }
}
