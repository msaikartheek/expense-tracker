package com.expense.tracker.controller;

import com.expense.tracker.service.ICacheService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Cache controller.
 */
@RestController
@RequestMapping("/api/v1/cache")
@AllArgsConstructor
public class CacheController {

    /**
     * The Cache service.
     */
    ICacheService cacheService;

    /**
     * Evict cache.
     */
    @GetMapping("/evictCache")
    public void evictCache() {
        cacheService.evictAllCache();
    }
}
