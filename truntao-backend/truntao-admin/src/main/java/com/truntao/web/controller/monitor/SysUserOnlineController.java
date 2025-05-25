package com.truntao.web.controller.monitor;

import com.truntao.common.annotation.Log;
import com.truntao.common.constant.CacheConstants;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.model.LoginUser;
import com.truntao.common.core.redis.RedisCache;
import com.truntao.common.enums.BusinessType;
import com.truntao.system.domain.dto.SysUserOnline;
import com.truntao.system.service.ISysUserOnlineService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 在线用户监控
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    @Resource
    private ISysUserOnlineService userOnlineService;

    @Resource
    private RedisCache redisCache;

    @PreAuthorize("@ss.hasPermission('monitor:online:list')")
    @GetMapping("/list")
    public R<List<SysUserOnline>> list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (StringUtils.isNotEmpty(userName) && Objects.nonNull(user.getUser())) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return R.ok(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermission('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public R<Void> forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return R.ok();
    }
}
