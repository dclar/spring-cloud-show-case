package org.dclar.threading.config;

import org.dclar.threading.exception.CustomAsyncException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Description:
 * <p>
 * 如果使用实现{@link AsyncConfigurer}接口的方式，则在整个系统环境中{@link Executor}都会是方法
 * {@link AsyncConfigurer#getAsyncExecutor()}返回的类型。
 * 相比于{@link ThreadingConfig#taskExecutor()}的定义方式，会覆盖全系统的{@link Executor}。
 *
 * @author dclar
 * @see {@link ThreadingConfig}
 */
@Configuration
@EnableAsync      // 开启@Async的支持
@EnableScheduling // 开启@Schedule的proxy支持
public class SpringAsyncConfig implements AsyncConfigurer {


    /**
     * 从系统角度返回一个通用的P{@link Executor}
     * <p>
     * The {@link Executor} instance to be used when processing async
     * method invocations.
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        /************************
         *  非常非常非常非常重要   *
         ************************/
        /*
        采用@Bean来定义的时候，spring会帮我们调用afterPropertiesSet方法从而进行initialize
        采用AsyncConfigurer的override方式来定义Executor的时候
        executor并不会自动的进行initialize，所以需要explicitly进行初始化
        */
        executor.initialize();
        return executor;
    }

    /**
     * 系统角度对所有的exception进行filter处理，省略实体类采用lambda
     * 注意，这是唯一的注入自定义{@link AsyncUncaughtExceptionHandler}的方式
     * <p>
     * The {@link AsyncUncaughtExceptionHandler} instance to be used
     * when an exception is thrown during an asynchronous method execution
     * with {@code void} return type.
     *
     * @see {@link ThreadingConfig#asyncUncaughtExceptionHandler()}
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        // use lambda to deal with exception handle
        return ((ex, method, params) -> {
            if (ex instanceof CustomAsyncException) {
                System.out.println("CustomAsyncException occurred. We should do something....");
            }

        });
    }
}
