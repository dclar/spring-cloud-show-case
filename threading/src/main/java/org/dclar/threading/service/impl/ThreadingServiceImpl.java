package org.dclar.threading.service.impl;


import org.apache.commons.lang.math.RandomUtils;
import org.dclar.threading.exception.CustomAsyncException;
import org.dclar.threading.service.ThreadingService;
import org.dclar.threading.vo.AsyncVo;
import org.springframework.aop.interceptor.AsyncExecutionInterceptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * <p>
 * Sample is from :
 * https://www.baeldung.com/spring-scheduled-tasks
 * https://www.baeldung.com/spring-async
 * <p>
 * <p>
 * Doc is from :https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
 *
 * @author dclar
 */
@Service
public class ThreadingServiceImpl implements ThreadingService {

    public static final int fixedDelay = 1000;


    /**
     * 【注意】：
     *
     * {@link Scheduled}是不能使用argument参数的，因为是启动系统就会执行的task
     * 而{@link org.springframework.scheduling.annotation.Async}则不同，是可以通过method调用来启动另一个线程
     * 达到想要的执行结果
     *
     */


    /**
     * In this case, the duration between the end of last execution and the start of next execution is fixed.
     * The task always waits until the previous one is finished.
     * This option should be used when it’s mandatory that the previous execution is completed before running again.
     * <p>
     * 上一个任务完成后，delay 1000ms 后会执行下一次任务，即F(Finish)-S(Start)任务
     */
    @Override
//    @Scheduled(fixedDelay = fixedDelay)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / fixedDelay);
        throw new RuntimeException("RunTimeException");
    }


    /**
     * Note that the beginning of the task execution doesn’t wait for the completion of the previous execution.
     * This option should be used when each execution of the task is independent.
     * <p>
     * 只是按照rate进行task的执行，不会考虑上一个任务是否完成
     */
    @Override
//    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    /**
     * Note how we’re using both fixedDelay as well as initialDelay in this example.
     * The task will be executed a first time after the initialDelay value – and it will continue to
     * be executed according to the fixedDelay.
     * This option comes handy when the task has a set-up that needs to be completed.
     * <p>
     * 第一次启动等待初始时常，然后按照fixedDelay进行task运行。适合有初始化的任务。
     */
    @Override
//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "Fixed rate task with one second initial delay - " + now);
    }

    /**
     * Note – in this example, that we’re scheduling a task to be executed at 10:15 AM on the 15th day of every month.
     */
    @Override
//    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }

    /**
     * Async如果不配置executor的话，默认会使用{@link SimpleAsyncTaskExecutor}
     * 调用地点参考{@link AsyncExecutionInterceptor#getDefaultExecutor(BeanFactory)}。
     * 如果没有executor，只会打印一个info的log，然后使用{@link SimpleAsyncTaskExecutor}
     * <p>
     * 可以自定义executor，参考{@link org.dclar.threading.config.ThreadingConfig}的配置
     * <p>
     * <p>
     * 需要注意的是，如果在方法执行完之前调用了{@link Optional#get()}的方法，有可能会导致{@link NullPointerException}
     * 此种情况下，使用{@link Future}作为return的vlaue，参考{@link ThreadingServiceImpl#getFuture(String)}
     *
     * @param name
     * @return
     * @see {@link ThreadingServiceImpl#getFuture(String)}
     */
    @Async
    @Override
    public Optional<AsyncVo> executeAsyn(String name) {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.notNull(name, "name can not be empty...");

        System.out.println("----- Execute async method start -----");
        AsyncVo vo = new AsyncVo();
        vo.setName(name);
        LocalTime now = LocalTime.now();
        vo.setLocalTime(now);
        System.out.println("Now is : " + now);
        System.out.println("----- Execute async method end   -----");
        return Optional.of(vo);
    }


    /**
     * 为了防止在线程执行完毕之前获取到{@link NullPointerException}的错误，使用{@link Future#get()}获取结果
     * 注意，get是阻塞式的调用
     *
     * @param name
     * @return
     */
    @Async
    @Override
    public Future<AsyncVo> getFuture(String name) {

        System.out.println("---- Start getFuture  ---- @" + Thread.currentThread().getName());
        try {
            // 休息 休息一下～ 模拟异步调用
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---- End   getFuture  ---- @" + Thread.currentThread().getName());

        AsyncVo vo = new AsyncVo();
        vo.setName(name);
        LocalTime now = LocalTime.now();
        vo.setLocalTime(now);
        return new AsyncResult<>(vo);
    }

    /**
     * 为了防止在线程执行完毕之前获取到{@link NullPointerException}的错误，使用{@link Future#get()}获取结果
     * 注意，get是阻塞式的调用
     *
     * @param name
     * @return
     */
    @Async
    @Override
    public Future<AsyncVo> getFutureWhenMeetException(String name) {

        System.out.println("---- Start getFutureWithEX  ---- @" + Thread.currentThread().getName());
        try {
            // 休息 休息一下～ 模拟异步调用
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---- End   getFutureWithEX  ---- @" + Thread.currentThread().getName());

        if (RandomUtils.nextBoolean()) {
            // 如果在return value是Future的method中throw exception的话，Future.get() 会帮助我们throw出这个exception
            // 但如果是void的话则需要我们自己封装错误处理机制
            throw new CustomAsyncException("New customAsyncException ...");
        }
        return new AsyncResult<>(new AsyncVo());
    }

    @Async
    @Override
    public void executeWhenMeetException(String name) {

        System.out.println("---- Start executeWithEX  ---- @" + Thread.currentThread().getName());
        try {
            // 休息 休息一下～ 模拟异步调用
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---- End   executeWithEX  ---- @" + Thread.currentThread().getName());

        if (RandomUtils.nextBoolean()) {
            // 如果在return value是Future的method中throw exception的话，Future.get() 会帮助我们throw出这个exception
            // 但如果是void的话则需要我们自己封装错误处理机制
            throw new CustomAsyncException("New customAsyncException ...");
        }

    }


}
