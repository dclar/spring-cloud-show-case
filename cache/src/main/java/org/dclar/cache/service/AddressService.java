package org.dclar.cache.service;

import org.dclar.cache.vo.AddressVo;

import java.util.Optional;

/**
 * Description:
 *
 * @author dclar
 */
public interface AddressService {

    Optional<AddressVo> getAddress(String id);

    Optional<AddressVo> cachePutAddress(String id);

    Optional<AddressVo> getAddressByCondition(String id);

    Optional<AddressVo> getAddressUnless(String id, String zipCode);

    Optional<AddressVo> evictAddress();
}
