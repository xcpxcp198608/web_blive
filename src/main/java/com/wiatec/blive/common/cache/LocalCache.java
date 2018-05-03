package com.wiatec.blive.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * guava cache
 *
 * @author patrick
 */
public class LocalCache {

    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    private static LoadingCache<String, String> loadingCache = CacheBuilder
            .newBuilder()
            .initialCapacity(1000)
            .maximumSize(100000)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return "";
                }
            });

    public static void set(String key, String value){
        loadingCache.put(key, value);
    }

    public static String get(String key){
        String value;
        try {
            value = loadingCache.get(key);
            return value;
        } catch (ExecutionException e) {
            logger.error("cache get error(" + key + ")", e);
            return "";
        }
    }
}
