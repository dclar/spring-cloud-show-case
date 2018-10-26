package org.dclar.cache.service.impl;

import org.dclar.cache.service.AddressService;
import org.dclar.cache.vo.AddressVo;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Description:
 * <p>
 * 案例来源于：https://www.baeldung.com/spring-cache-tutorial
 *
 * @author dclar
 */
@Service
public class AddressServiceImpl implements AddressService {


    /**
     * 基础的Cacheable
     * <p>
     * 使用id作为key缓存Address
     *
     * @param id
     * @return
     */
    @Cacheable(value = "address", key = "#id")
    @Override
    public Optional<AddressVo> getAddress(String id) {

        Assert.notNull(id, "address id can not be null");
        System.out.println("Id " + id + " is not using caching ...");
        return Optional.of(AddressVo.sampleNew(id));
    }

    /**
     * 在id startWith 'condition'的情况下，会对Address的数据进行缓存
     *
     * @param id
     * @return
     */
    @Cacheable(value = "address", key = "#id", condition = "#id.startsWith('condition')")
    @Override
    public Optional<AddressVo> getAddressByCondition(String id) {
        System.out.println("execute method of 'getAddressByCondition' when id equals " + id);
        return Optional.of(AddressVo.sampleNew(id));
    }

    /**
     * 在缓存Address的内容，除非result.zipCode.length > 2 的时候不进行缓存
     *
     * @param id
     * @return
     */
    @Cacheable(value = "address", unless = "#result.zipCode.length() > 2") // unless ... or will cache values
    @Override
    public Optional<AddressVo> getAddressUnless(String id, String zipCode) {
        System.out.println("execute method of 'getAddressUnless' when id equals " + id);
        AddressVo addressVo = AddressVo.sampleNew(id);
        addressVo.setZipCode("2201121");
        return Optional.of(addressVo);
    }

    /**
     * Now, what would be the problem with making all methods @Cacheable?
     * The problem is size – we don’t want to populate the cache with values that we don’t need often.
     * Caches can grow quite large, quite fast, and we could be holding on to a lot of stale or unused data.
     * The @CacheEvict annotation is used to indicate the removal of one or more/all values –
     * so that fresh values can be loaded into the cache again:
     * <p>
     * From ： https://www.baeldung.com/spring-cache-tutorial
     *
     * @return
     */
    @CacheEvict(value = "address", allEntries = true) // 清除所有的缓存数据
    @Override
    public Optional<AddressVo> evictAddress() {
        System.out.println("evict all cached address contents ... ");
        return Optional.of(AddressVo.sampleNew("evictAddressId"));
    }


    /**
     * While @CacheEvict reduces the overhead of looking up entries in a large cache by removing stale and
     * unused entries, ideally, you want to avoid evicting too much data out of the cache.
     * Instead, you’d want to selectively and intelligently update the entries whenever they’re altered.
     * With the @CachePut annotation, you can update the content of the cache without interfering the method execution.
     * That is, the method would always be executed and the result cached.
     * <p>
     * 与@Cacheable最大的区别是，@CachePut总会执行method，而@Cacheable有可能跳过method的执行只走缓存
     *
     * @param id
     * @return
     */
    @CachePut(value = "address", key = "#id") // 清除所有的缓存数据
    @Override
    public Optional<AddressVo> cachePutAddress(String id) {
        System.out.println("cachePut data of " + id);
        return Optional.of(AddressVo.sampleNew(id));
    }


    /**
     * 使用@Caching对@CacheEvict、@Cacheable、@CachePut进行group
     * <p>
     * 可以完成以下场景：
     * 数据的更新操作，更新后的数据同步到cache中，使用@CacheEvict + @CachePut
     *
     * @param id
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict("receipt"),
                    @CacheEvict(value = "address", key = "#id")
            },
            put = {@CachePut(value = "address", key = "#id")}
    )
    public Optional<AddressVo> cachingAddress(String id) {
        System.out.println("using caching for " + id);
        return Optional.of(AddressVo.sampleNew(id));
    }


}
