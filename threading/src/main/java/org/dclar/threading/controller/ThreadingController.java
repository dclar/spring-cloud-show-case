package org.dclar.threading.controller;

import org.apache.tomcat.jni.Local;
import org.dclar.threading.service.ThreadingService;
import org.dclar.threading.vo.AsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Description:
 *
 * @author dclar
 */
@RestController
public class ThreadingController {

    @Autowired
    private ThreadingService threadingService;


    /**
     * 是否可以在schedule执行的过程中，再次调用被{@link org.springframework.scheduling.annotation.Scheduled}标注的方法
     * 答案是肯定的
     *
     * @return
     */
    @GetMapping("/schedule/start")
    public String startSchedule() {

        long t0 = System.nanoTime();
        System.out.println("Before startSchedule method : " + t0);
        threadingService.scheduleFixedDelayTask();
        long t1 = System.nanoTime();
        System.out.println("Before startSchedule method : " + t1);
        System.out.println("All time consumed           : " + (t1 - t0));
        return "success";
    }


    /**
     * 实验一个@Async方法的调用，观察
     * 1. 提前获取AsyncVo是否抛出{@link NullPointerException}
     * 2. 是否异步执行
     *
     * @param name
     * @return
     */
    @GetMapping("/async/{name}")
    public String executeAsync(@PathVariable("name") String name) {

        System.out.println("Before execute async method : ++++++++++++++");
        System.out.println(LocalTime.now());
        Optional<AsyncVo> optional = threadingService.executeAsyn(name);
//        if (optional.isPresent()) {
//            AsyncVo vo = optional.get();
//            System.out.println("Value of localTime of async method is : " + vo.getLocalTime());
//        }
        System.out.println("After  execute async method : ++++++++++++++");
        return "success";
    }

    /**
     * 调用一个@Async的method，观察：
     * 1. 哪个线程执行的任务
     * 2. 执行的顺序以及pending的时点与时长
     *
     * @param name
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/async/future/{name}")
    public ResponseEntity<AsyncVo> getFuture(@PathVariable("name") String name) throws ExecutionException, InterruptedException {

        System.out.println("++++ Start async call ++++ @" + Thread.currentThread().getName());
        Future<AsyncVo> future = threadingService.getFuture(name);
        AsyncVo vo = null;
        System.out.println("     Result pending ....");
        while (true) {
            if (future.isDone()) {
                vo = future.get();
                System.out.println("     Got result!!");
                break;
            }
        }
        System.out.println("++++ End   async call ++++ @" + Thread.currentThread().getName());
        return ResponseEntity.ok(vo);
    }

    /**
     * 调用一个@Async的method，观察：
     * 1. 发生Exception后是否直接被throw（由于service的return value是Future，应该可以直接抛出
     *
     * @param name
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/async/future/ex/{name}")
    public ResponseEntity<AsyncVo> getFutureEx(@PathVariable("name") String name) throws ExecutionException, InterruptedException {

        System.out.println("++++ Start async call ++++ @" + Thread.currentThread().getName());
        Future<AsyncVo> future = threadingService.getFutureWhenMeetException(name);
        AsyncVo vo = null;
        System.out.println("     Result pending ....");
        while (true) {
            if (future.isDone()) {
                vo = future.get();
                System.out.println("     Got result!!");
                break;
            }
        }
        System.out.println("++++ End   async call ++++ @" + Thread.currentThread().getName());
        return ResponseEntity.ok(vo);
    }

    /**
     * 调用一个@Async的method，观察：
     * 1. 发生Exception后的执行路径，由于执行Service是void的
     *
     * @param name
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/async/execute/void/ex/{name}")
    public ResponseEntity<String> executeEx(@PathVariable("name") String name) throws ExecutionException, InterruptedException {

        System.out.println("++++ Start async call ++++ @" + Thread.currentThread().getName());
        threadingService.executeWhenMeetException(name);

        System.out.println("++++ End   async call ++++ @" + Thread.currentThread().getName());
        return ResponseEntity.ok("Main thread is over");
    }



}
