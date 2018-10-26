package org.dclar.cache.controller;

import org.dclar.cache.service.AddressService;
import org.dclar.cache.vo.AddressVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Description:
 * <p>
 * 操作@Cache的REFTful接口汇总
 *
 * @author dclar
 */
@RestController
public class CacheRestController {

    private AddressService addressService;

    public CacheRestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address/{id}")
    ResponseEntity<AddressVo> address(@PathVariable("id") String id) {
        Optional<AddressVo> rel = addressService.getAddress(id);
        if (rel.isPresent())
            return ResponseEntity
                    .ok()
                    .body(rel.get());

        return ResponseEntity
                .ok()
                .body(rel.orElse(AddressVo.defaultNew("null", "null", "null")));
    }

    @GetMapping("/address/condition/{id}")
    ResponseEntity<AddressVo> addressOnCondition(@PathVariable("id") String id) {
        return ResponseEntity.ok(addressService.getAddressByCondition(id).get());
    }

    @GetMapping("/address/evict")
    ResponseEntity<AddressVo> addressEvict() {
        return ResponseEntity.ok(addressService.evictAddress().get());
    }

}
