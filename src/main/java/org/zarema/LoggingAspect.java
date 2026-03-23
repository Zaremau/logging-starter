package org.zarema;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void servicePointcut() {}

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerPointcut() {}

    @Around("servicePointcut() || restControllerPointcut()")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("[METHOD STARTS] {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("[METHOD ENDS] {}.{} - {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                (endTime - startTime));
        return result;
    }
}
