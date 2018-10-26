# Cache

使用Spring Cache完成

- IO操作的二级缓存，如数据库操作
- 完成对缓存的CRUD，使用repository方式
- 完成对缓存的CRUD，使用template方式

## @Cache的showcase

### application.yml

```yml

spring:
  cache:
    type: redis #指定使用redis作为@Cache的介质
    cache-names: addressVo,receipt #CacheNames的设定
    # 这里的cache下的各种配置也可以使用@CacheConfig
    redis:
      key-prefix: "showcase-cache::" #在redis中的key的prefix
      time-to-live: 60000 # 1 minute
  redis:
    host: 10.0.47.51
    port: 6379

```

### Config

- @EnableCaching 注解开启cache（为了cache生成proxy）
- 如果需要自定义Redis的RestTemplate，则@Bean定义RestTemplate

```java

@EnableCaching // 开启@Cache大门
@Configuration
// @CacheConfig 已经在yml中进行了配置，知道也可以通过这个annotation配置即可
public class CachingConfig {
    
    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
    

}

```

### Spring Cache

参看代码：http://github.com/dclar/spring-cloud-show-case/cache/src/main/java/org/dclar/cache/service/impl/AddressServiceImpl.java



## Spring data repository方式的showcase

### Entity

使用@RedisHash声明

```java

@RedisHash("receipt")
@Data
public class ReceiptVo implements Serializable {

    private static final long serialVersionUID = 3258522111524812775L;

    String id;
    String title = "defaultTitle";
    String user = "defaultUser";
}

```

### Dao（Repository）

```java

@Repository
public interface ReceiptRepository extends CrudRepository<ReceiptVo, String> {
}

```

### 使用

参看：http://github.com/dclar/spring-cloud-show-case/cache/src/main/java/org/dclar/cache/service/impl/AddressServiceImpl.java
