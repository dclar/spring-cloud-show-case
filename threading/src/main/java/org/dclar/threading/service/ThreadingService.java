package org.dclar.threading.service;

import org.dclar.threading.vo.AsyncVo;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Description:
 *
 * @author dclar
 */
public interface ThreadingService {

    void scheduleFixedDelayTask();

    void scheduleFixedRateTask();

    void scheduleFixedRateWithInitialDelayTask();

    void scheduleTaskUsingCronExpression();

    Optional<AsyncVo> executeAsyn(String name);

    Future<AsyncVo> getFuture(String name);

    Future<AsyncVo> getFutureWhenMeetException(String name);

    void executeWhenMeetException(String name);
}
