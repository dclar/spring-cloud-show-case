package org.dclar.cache.vo;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * Description:
 *
 * @author dclar
 */
@RedisHash("receipt")
@Data
public class ReceiptVo implements Serializable {

    private static final long serialVersionUID = 3258522111524812775L;

    String id;
    String title = "defaultTitle";
    String user = "defaultUser";
}
