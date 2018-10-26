package org.dclar.threading.advice;

import org.dclar.threading.exception.CustomAsyncException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description:
 *
 * @author dclar
 */
@RestControllerAdvice
public class ThreadingAdvice {

    @ExceptionHandler(CustomAsyncException.class)
    ResponseEntity<String> handleCustomAsyncException(Throwable ex) {
        System.out.println(ex);
        return ResponseEntity.ok("Handle CustomAsyncException Over!");
    }
}
