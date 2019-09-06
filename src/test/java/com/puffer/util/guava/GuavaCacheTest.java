package com.puffer.util.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.testng.annotations.Test;

/**
 * guava缓存测试
 *
 * @author buyi
 * @date 2019年06月21日 11:40:54
 * @since 1.0.0
 */
public class GuavaCacheTest {

    public static com.google.common.cache.CacheLoader<String, String> createCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                System.out.println("加载创建key:" + key);
                return "";
            }
        };
    }

    @Test
    public void test() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).build(GuavaCacheTest.createCacheLoader());
    }
}
