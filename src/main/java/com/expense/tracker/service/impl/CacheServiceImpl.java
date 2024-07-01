package com.expense.tracker.service.impl;

import com.expense.tracker.service.ICacheService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class CacheServiceImpl implements ICacheService {

    CacheManager cacheManager;

    /**
     *
     */
    @Override
    @Scheduled(cron = "0 0 12 ? * SUN *")
    public void evictAllCache() {
        log.info("*** Evicting Cache ***");

        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        log.info("*** Evicting Cache done***");
    }

}
