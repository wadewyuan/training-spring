package com.wadeyuan.training.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class ServiceExecutionTimeLoggerAdvice implements MethodInterceptor{

    private static final Logger logger = Logger.getLogger(ServiceExecutionTimeLoggerAdvice.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long executionStartTime = System.currentTimeMillis();
        Object returnValue = invocation.proceed(); // run the method
        long executionEndTime = System.currentTimeMillis();

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Execution Time:  ");
        logBuilder.append(executionEndTime - executionStartTime);
        logBuilder.append("ms  ");
        logBuilder.append(invocation.getMethod().getDeclaringClass().getName());
        logBuilder.append(":  ");
        logBuilder.append(invocation.getMethod().getName());
        logBuilder.append(":  ");
        logBuilder.append("args[");
        // print all the arguments in this invocation
        Object[] args = invocation.getArguments();
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
