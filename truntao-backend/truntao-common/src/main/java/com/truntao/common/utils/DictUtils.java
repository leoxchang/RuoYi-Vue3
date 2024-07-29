package com.truntao.common.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson2.JSONArray;
import com.truntao.common.constant.CacheConstants;
import com.truntao.common.core.domain.dto.SysDictDataDTO;
import com.truntao.common.core.redis.RedisCache;
import com.truntao.common.utils.spring.SpringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字典工具类
 *
 * @author truntao
 */
public class DictUtils {
    private DictUtils() {
    }

    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 设置字典缓存
     *
     * @param key          参数键
     * @param dictDataList 字典数据列表
     */
    public static void setDictCache(String key, List<SysDictDataDTO> dictDataList) {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDataList);
    }

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return  字典数据列表
     */
    public static List<SysDictDataDTO> getDictCache(String key) {
        JSONArray arrayCache = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (Objects.nonNull(arrayCache)) {
            return arrayCache.toList(SysDictDataDTO.class);
        }
        return Collections.emptyList();
    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue) {
        if(StringUtils.isEmpty(dictValue)){
            return StringUtils.EMPTY;
        }
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel) {
        if(StringUtils.isEmpty(dictLabel)){
            return StringUtils.EMPTY;
        }
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictDataDTO> dataList = getDictCache(dictType);

        if (CollectionUtils.isNotEmpty(dataList)) {
            if (StringUtils.containsAny(separator, dictValue)) {
                for (SysDictDataDTO dict : dataList) {
                    for (String value : dictValue.split(separator)) {
                        if (value.equals(dict.getDictValue())) {
                            propertyString.append(dict.getDictLabel()).append(separator);
                            break;
                        }
                    }
                }
            } else {
                for (SysDictDataDTO dict : dataList) {
                    if (dictValue.equals(dict.getDictValue())) {
                        return dict.getDictLabel();
                    }
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictDataDTO> dataList = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictLabel) && CollectionUtils.isNotEmpty(dataList)) {
            for (SysDictDataDTO dict : dataList) {
                for (String label : dictLabel.split(separator)) {
                    if (label.equals(dict.getDictLabel())) {
                        propertyString.append(dict.getDictValue()).append(separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDictDataDTO dict : dataList) {
                if (dictLabel.equals(dict.getDictLabel())) {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 删除指定字典缓存
     *
     * @param key 字典键
     */
    public static void removeDictCache(String key) {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(key));
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(CacheConstants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return CacheConstants.SYS_DICT_KEY + configKey;
    }
}
