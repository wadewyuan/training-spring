package com.wadeyuan.training.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class ServiceExecutionTimeLoggerAspect {

    private static final Logger logger = Logger.getLogger(ServiceExecutionTimeLoggerAspect.class);

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long executionStartTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed(); // run the method
        long executionEndTime = System.currentTimeMillis();

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Execution Time:  ");
        logBuilder.append(executionEndTime - executionStartTime);
        logBuilder.append("ms  ");
        logBuilder.append(joinPoint.getTarget().getClass().getName());
        logBuilder.append(":  ");
        logBuilder.append(joinPoint.getSignature().getName());
        logBuilder.append(":  ");
        logBuilder.append("args[");
        // print all the arguments in this invocation
        Object[] args = joinPoint.getArgs();
        for(int i = 0; i < args.length; i++) {
            logBuilder.append(args[i]);
            if(i < args.length -1) {
                logBuilder.append(", ");
            }
        }
        logBuilder.append("]");

        logger.info(logBuilder.toString());

        return returnValue;
    }
}
