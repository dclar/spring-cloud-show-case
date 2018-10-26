package org.dclar.cache.service.impl;

import org.dclar.cache.dao.ReceiptRepository;
import org.dclar.cache.service.ReceiptService;
import org.dclar.cache.vo.ReceiptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Description:
 *
 * @author dclar
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {


    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Optional<ReceiptVo> putData(String id) {

        Assert.notNull(id, "receipt id can not be empty");
        ReceiptVo receipt = new ReceiptVo();
        receipt.setId(id);
        receipt.setTitle("receiptTitle");
        receipt.setUser("receiptUser");
        receiptRepository.save(receipt);
        System.out.println("save receipt by repository");
        return Optional.of(receipt);
    }

    @Override
    public Optional<ReceiptVo> getData(String id) {

        Assert.notNull(id, "receipt id can not be empty");
        Optional<ReceiptVo> receipt = receiptRepository.findById(id);
        return receipt;
    }

    @Override
    public String cacheData(String id) {
        Assert.notNull(id, "receipt id can not be empty");
        ReceiptVo vo = new ReceiptVo();
        vo.setId(id);
        vo.setTitle("Title cached by restTemplate type");
        vo.setUser("User cached by restTemplate type");
        redisTemplate.opsForValue().set(id, vo);
        return "success";
    }

    @Override
    public Optional<ReceiptVo> getCachedData(String id) {
        Assert.notNull(id, "receipt id can not be empty");
        ReceiptVo receiptVo = (ReceiptVo) redisTemplate.opsForValue().get(id);
        return Optional.of(receiptVo);
    }


}
