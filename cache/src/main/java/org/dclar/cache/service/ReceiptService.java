package org.dclar.cache.service;

import org.dclar.cache.vo.ReceiptVo;

import java.util.Optional;

/**
 * Description:
 *
 * @author dclar
 */
public interface ReceiptService {

    Optional<ReceiptVo> putData(String id);

    Optional<ReceiptVo> getData(String id);

    String cacheData(String id);

    Optional<ReceiptVo> getCachedData(String id);

}
