package org.dclar.cache.controller;

import org.dclar.cache.service.ReceiptService;
import org.dclar.cache.vo.ReceiptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Optional;

/**
 * Description:
 *
 * @author dclar
 */
@RestController
public class RepositoryRestController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("receipt/put/{id}") // 为了测试方便使用get，原则上使用put或post
    public ResponseEntity<ReceiptVo> put(@PathVariable("id") String id) {
        return ResponseEntity.ok(receiptService.putData(id).get());
    }

    @GetMapping("receipt/get/{id}")
    public ResponseEntity<ReceiptVo> get(@PathVariable("id") String id) {

        Optional<ReceiptVo> optional = receiptService.getData(id);
        if (optional.isPresent())
            return ResponseEntity.ok(receiptService.getData(id).get());
        return ResponseEntity.ok(new ReceiptVo());
    }

    @GetMapping("receipt/cache/{id}")
    public String cacheData(@PathVariable("id") String id) {
        receiptService.cacheData(id);
        return "success";
    }

    @GetMapping("receipt/cache/get/{id}")
    public ResponseEntity<ReceiptVo> getCacheData(@PathVariable("id") String id) {
        Optional<ReceiptVo> optional = receiptService.getCachedData(id);
        if (optional.isPresent())
            return ResponseEntity.ok().body(optional.get());

        return ResponseEntity.ok(new ReceiptVo());
    }


}
