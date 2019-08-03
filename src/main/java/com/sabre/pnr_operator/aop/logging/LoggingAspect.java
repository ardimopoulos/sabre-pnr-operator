package com.sabre.pnr_operator.aop.logging;

import com.sabre.pnr_operator.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

import static com.sabre.pnr_operator.constants.HandlerConstants.ERROR;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("within(com.sabre.pnr_operator.handlers..*) " +
            "|| within(com.sabre.pnr_operator.headers..*)) " +
            "|| within(com.sabre.pnr_operator.ScheduledTask..*)")
    public void allMethodsPointcut() {}

    @Before("allMethodsPointcut()")
    public void logBeforeMethods(JoinPoint joinPoint) {
        log.info("Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "allMethodsPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause\'{}\' and exception \'{}\'",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                e.getCause(),
                e.getMessage());

        e.printStackTrace();
    }

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

    @Pointcut("execution(* com.sabre.pnr_operator.handlers.*.processRequest())")
    public void processRequestPointcut() {}

    @AfterReturning(pointcut = "processRequestPointcut()", returning = "returnedValue")
    public void logAfterReturningProcessRequest(JoinPoint joinPoint, Response returnedValue) {
        if (returnedValue.getStatus().equals(ERROR)) {
            log.error("Exception in {}.{}(): \'{}\'",
                    joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(),
                    returnedValue.getMessage());
        }
        log.info("Handler response: {}", returnedValue);
    }
}
