package com.MoneyTracker.MoneyTracker.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitoringAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitoringAspect.class);
    private static final long PERFORMANCE_THRESHOLD_MS = 1000; // Threshold for "too long" in milliseconds

    @Around("execution(* com.MoneyTracker.MoneyTracker.services.TransactionService.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();
            long endTime = System.nanoTime();
            long durationMs = (endTime - startTime) / 1_000_000; // Convert to milliseconds

            logger.info("Method {} executed in {} ms", methodName, durationMs);
            if (durationMs > PERFORMANCE_THRESHOLD_MS) {
                logger.warn("Method {} took too long to execute: {} ms (threshold: {} ms)", 
                            methodName, durationMs, PERFORMANCE_THRESHOLD_MS);
            }
            return result;
        } catch (Throwable t) {
            long endTime = System.nanoTime();
            long durationMs = (endTime - startTime) / 1_000_000;
            logger.error("Method {} failed after {} ms with exception: {}", 
                        methodName, durationMs, t.getMessage());
            if (durationMs > PERFORMANCE_THRESHOLD_MS) {
                logger.warn("Method {} took too long to fail: {} ms (threshold: {} ms)", 
                            methodName, durationMs, PERFORMANCE_THRESHOLD_MS);
            }
            throw t;
        }
    }
}