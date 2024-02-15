package story.cheek.common.redis;

public interface RedisUtils {
    void setData(String key, String value);
    String getData(String key);
    void deleteData(String key);
}
