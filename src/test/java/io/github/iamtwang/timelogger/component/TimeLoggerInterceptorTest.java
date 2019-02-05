package io.github.iamtwang.timelogger.component;

import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import java.util.concurrent.TimeUnit;

public class TimeLoggerInterceptorTest {

    @Test
    public void timeLogger() {

        Dummy target = new Dummy();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        TimeLoggerInterceptor aspect = new TimeLoggerInterceptor();
        factory.addAspect(aspect);
        Dummy proxy = factory.getProxy();
        proxy.show();
    }

    class Dummy{

        @TimeLogger(level = TimeLogger.Level.INFO)
        public void show(){

            try {
                TimeUnit.MILLISECONDS.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}