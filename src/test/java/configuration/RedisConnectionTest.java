package configuration;

import common.BaseContextCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Created by supumall on 2016/7/4.
 */
public class RedisConnectionTest extends BaseContextCase {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testKeyValue(){
        String test="member:tecent:one";
        String value="这是一个神奇的世界";
        ValueOperations valueOperations= redisTemplate.opsForValue();
        HashOperations hashOperations=redisTemplate.opsForHash();
        for (int i=0;i<100000;i++){
            hashOperations.put(test,""+i,value);
        }
    }
}
