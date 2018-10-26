package org.dclar.threading.vo;

import lombok.Data;

import java.time.LocalTime;

/**
 * Description:
 *
 * @author dclar
 */
@Data
public class AsyncVo {

    private String name = "default";
    private LocalTime localTime = LocalTime.now();

    @Override
    public String toString() {
        return "name : " + name + ", localTime : " + localTime;
    }
}
