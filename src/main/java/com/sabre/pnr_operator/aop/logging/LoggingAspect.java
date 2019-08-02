package com.sabre.pnr_operator.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* *..ScheduledTask.runTask())")
    public void scheduledTaskPointcut() {}

    @Before("scheduledTaskPointcut()")
    public void logBeforeScheduledTask() {
        log.info("Start running scheduled task...");
    }

    @AfterReturning(pointcut = "scheduledTaskPointcut()")
    public void logAfterReturningScheduledTask() {
        log.info("Scheduled task is finished.");
    }

    @AfterThrowing(pointcut = "scheduledTaskPointcut()", throwing = "e")
    public void logAfterThrowingExceptionScheduledTask(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause\'{}\' and exception \'{}\'",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                e.getCause(),
                e.getMessage());

        e.printStackTrace();
    }

}
