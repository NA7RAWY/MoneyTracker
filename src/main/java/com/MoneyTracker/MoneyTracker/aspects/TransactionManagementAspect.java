package com.MoneyTracker.MoneyTracker.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Aspect
@Component
public class TransactionManagementAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementAspect.class);

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TransactionManagementAspect(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Around("execution(* com.MoneyTracker.MoneyTracker.services.TransactionService.*(..))")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Starting transaction for method: {}", methodName);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                try {
                    Object result = joinPoint.proceed();
                    logger.info("Committing transaction for method: {}", methodName);
                    return result;
                } catch (Throwable t) {
                    logger.error("Rolling back transaction for method: {} due to exception: {}", 
                                methodName, t.getMessage());
                    status.setRollbackOnly();
                    throw new RuntimeException(t);
                }
            }
        });
    }
}