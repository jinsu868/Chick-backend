package story.cheek.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisMailUtils implements RedisUtils {

    @Value("${spring.data.redis.mailExpiredTime}")
    private int mailExpiredTime;

    private final RedisTemplate<String, String> redisMailTemplate;

    @Override
    public void setData(String key, String value) {
        redisMailTemplate.opsForValue().set(key, value, mailExpiredTime, TimeUnit.MINUTES);
    }

    @Override
    public String getData(String key) {
        return redisMailTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteData(String key) {
        redisMailTemplate.delete(key);
    }
}
