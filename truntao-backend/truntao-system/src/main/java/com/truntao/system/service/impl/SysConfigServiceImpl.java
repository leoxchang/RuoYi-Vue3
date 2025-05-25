package com.truntao.system.service.impl;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.DataSource;
import com.truntao.common.constant.CacheConstants;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.redis.RedisCache;
import com.truntao.common.core.text.Convert;
import com.truntao.common.enums.DataSourceType;
import com.truntao.common.exception.ServiceException;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.system.domain.dto.SysConfigDTO;
import com.truntao.system.domain.po.SysConfig;
import com.truntao.system.domain.ro.SysConfigParam;
import com.truntao.system.domain.ro.SysConfigUpdateParam;
import com.truntao.system.mapper.SysConfigMapper;
import com.truntao.system.service.ISysConfigService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * 参数配置 服务层实现
 *
 * @author truntao
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Resource
    private SysConfigMapper configMapper;

    @Resource
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    @DataSource(DataSourceType.MASTER)
    public SysConfigDTO selectConfigById(Long configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return new SysConfigDTO(configMapper.selectConfig(config));
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (Objects.nonNull(retConfig)) {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return "";
    }

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 查询参数配置列表
     *
     * @param configParam 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public Page<SysConfigDTO> selectConfigList(SysConfigParam configParam) {
        SysConfig config = configParam.getSysConfig();
        Page<SysConfigDTO> page = new Page<>();
        try (Page<SysConfig> list = configMapper.selectConfigList(config)) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysConfigDTO::new).toList());
        }
        return page;
    }

    /**
     * 新增参数配置
     *
     * @param configParam 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfigParam configParam) {
        SysConfig config = configParam.getSysConfig();
        config.setCreateBy(SecurityUtils.getUsername());
        config.setCreateTime(new Date());
        int row = configMapper.insert(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     *
     * @param configUpdateParam 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfigUpdateParam configUpdateParam) {
        SysConfig temp = configMapper.selectConfigById(configUpdateParam.getConfigId());
        if (!Objects.equals(temp.getConfigKey(), configUpdateParam.getConfigKey())) {
            redisCache.deleteObject(getCacheKey(temp.getConfigKey()));
        }
        SysConfig config = configUpdateParam.getSysConfig();
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(new Date());
        int row = configMapper.updateById(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(configUpdateParam.getConfigKey()),
                    configUpdateParam.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    @Override
    public void deleteConfigByIds(Long[] configIds) {
        for (Long configId : configIds) {
            SysConfigDTO config = selectConfigById(configId);
            if (Objects.equals(UserConstants.YES, config.getConfigType())) {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        try (Page<SysConfig> configsList = configMapper.selectConfigList(new SysConfig())) {
            for (SysConfig config : configsList) {
                redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
            }
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param configId  参数配置Id
     * @param configKey 参数配置Key
     * @return 结果
     */
    @Override
    public boolean checkConfigKeyUnique(Long configId, String configKey) {
        configId = Objects.isNull(configId) ? -1L : configId;
        SysConfig info = configMapper.checkConfigKeyUnique(configKey);
        if (Objects.nonNull(info) && !Objects.equals(info.getConfigId(), configId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}
