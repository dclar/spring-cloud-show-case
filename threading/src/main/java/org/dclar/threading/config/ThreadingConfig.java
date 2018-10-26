package org.dclar.threading.config;

import org.dclar.threading.exception.CustomAsyncException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Description:
 * <p>
 * 见每个方法的config
 *
 * @author dclar
 * @see {@link SpringAsyncConfig}
 */
public class ThreadingConfig {

    /**
     * {@link org.springframework.scheduling.annotation.Async}进行注解的method默认会使用名字是"taskExecutor"的bean进行
     * 异步处理，故这里把method的名称定义为taskExecutor。
     * 如果希望使用其他的名字，可以在设定{@link Async#value()}中设定Executor的名称即可。
     * 如 @Async("myTaskExecutor")这样的使用方法
     *
     * @return
     */
//    @Bean
    public Executor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    /**
     * 需要注意的是，在{@link AsyncUncaughtExceptionHandler}是无法使用上面的方式进行定义的
     * 所以此@Bean定义是无效的，参考{@link SpringAsyncConfig#getAsyncUncaughtExceptionHandler()}
     *
     * @return
     * @see {@link SpringAsyncConfig#getAsyncUncaughtExceptionHandler()}
     */
//    @Bean
    @Deprecated
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {

        return ((ex, method, params) -> {
            if (ex instanceof CustomAsyncException) {
                System.out.println("CustomAsyncException occurred. We should do something....");
            }

        });
    }
}
