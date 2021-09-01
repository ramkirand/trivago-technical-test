package com.trivago.configuration;

import com.trivago.constants.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@org.springframework.context.annotation.Configuration
@EnableCaching
public class Configuration {

  @Bean
  public CacheManager cacheManager() {
    final SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(Collections.singletonList(
        new ConcurrentMapCache(Constants.SEARCH_CACHE)));
    return cacheManager;
  }
}
