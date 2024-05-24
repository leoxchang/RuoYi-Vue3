package com.truntao.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.truntao.system.domain.po.SysConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数配置 数据层
 *
 * @author truntao
 */
@Mapper
 public interface SysConfigMapper extends BaseMapper<SysConfig> {
    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
     SysConfig selectConfig(SysConfig config);

    /**
     * 通过ID查询配置
     *
     * @param configId 参数ID
     * @return 参数配置信息
     */
     SysConfig selectConfigById(Long configId);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
     Page<SysConfig> selectConfigList(SysConfig config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
     SysConfig checkConfigKeyUnique(String configKey);

    /**
     * 删除参数配置
     *
     * @param configId 参数ID
     * @return 结果
     */
     int deleteConfigById(Long configId);

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
     int deleteConfigByIds(Long[] configIds);
}
