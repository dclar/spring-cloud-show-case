package org.dclar.cache.dao;

import org.dclar.cache.vo.ReceiptVo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author dclar
 */
@Repository
public interface ReceiptRepository extends CrudRepository<ReceiptVo, String> {
}
