package org.dclar.cache.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.math.RandomUtils;

import java.io.Serializable;

/**
 * Description:
 *
 * @author dclar
 */
@Data
@AllArgsConstructor
public class AddressVo implements Serializable {

    private static final long serialVersionUID = 3258522114575912775L;
    String id;
    String district;
    String zipCode;

    public static AddressVo defaultNew(String id, String district, String zipCode) {
        return new AddressVo(id, district, zipCode);
    }

    public static AddressVo sampleNew(String id) {
        return new AddressVo(id, String.valueOf(RandomUtils.nextInt()), String.valueOf(RandomUtils.nextInt()));
    }

}
