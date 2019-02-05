package io.github.iamtwang.timelogger.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class TimeLoggerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TimeLoggerInterceptor.class);


    @Around(value = "@annotation(io.github.iamtwang.timelogger.component.TimeLogger)")
    public Object timeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Signature signature = proceedingJoinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("annotation TimeLogger can be used on the method");
        }

        Object object = null;
        MethodSignature methodSignature = (MethodSignature) signature;
        Object target = proceedingJoinPoint.getTarget();
        try {
            Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            TimeLogger timeLogger  = currentMethod.getAnnotation(TimeLogger.class);
            if (timeLogger != null) {
                long startTime = System.currentTimeMillis();
                object = proceedingJoinPoint.proceed();
                long timeUsed = System.currentTimeMillis() - startTime;

                if (timeLogger.timeout() != -1 && timeUsed < timeLogger.timeout()) {
                    return object;
                }

                String methodName =  timeLogger.methodName();
                if (methodName == null || methodName.equals("")) {
                    methodName = proceedingJoinPoint.getSignature().toString();
                }

                String printContent = methodName+ " used " + timeUsed + " ms";
                appendLog(timeLogger, printContent);

            } else {
                object = proceedingJoinPoint.proceed();
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object;

    }

    private void appendLog(final TimeLogger timeLogger,  final String content){

        switch (timeLogger.level()){
            case TRACE:
                logger.trace(content);
                break;
            case DEBUG:
                logger.debug(content);
                break;
            case INFO:
                logger.info(content);
                break;
            case WARN:
                logger.warn(content);
                break;
            case ERROR:
                logger.error(content);
                break;
        }

    }
}
