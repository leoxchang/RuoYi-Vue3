package com.truntao.web.controller.monitor;

import java.util.*;

import com.truntao.common.core.domain.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.truntao.common.constant.CacheConstants;
import com.truntao.system.domain.dto.SysCache;

import javax.annotation.Resource;

/**
 * 缓存监控
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final List<SysCache> caches = new ArrayList<>();

    static {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "密码错误次数"));
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @GetMapping()
    public R<Map<String,Object>> getInfo() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        Properties commandStats =
                (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info(
                        "commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        if(Objects.nonNull(commandStats)) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return R.ok(result);
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @GetMapping("/getNames")
    public R<List<SysCache>> cache() {
        return R.ok(caches);
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @GetMapping("/getKeys/{cacheName}")
    public R<Set<String>> getCacheKeys(@PathVariable String cacheName) {
        Set<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        return R.ok(new TreeSet<>(cacheKeys));
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public R<SysCache> getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return R.ok(sysCache);
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @DeleteMapping("/clearCacheName/{cacheName}")
    public R<Void> clearCacheName(@PathVariable String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        if(Objects.nonNull(cacheKeys)) {
            redisTemplate.delete(cacheKeys);
        }
        return R.ok();
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public R<Void> clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return R.ok();
    }

    @PreAuthorize("@ss.hasPermission('monitor:cache:list')")
    @DeleteMapping("/clearCacheAll")
    public R<Void> clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        if(Objects.nonNull(cacheKeys)) {
            redisTemplate.delete(cacheKeys);
        }
        return R.ok();
    }
}
