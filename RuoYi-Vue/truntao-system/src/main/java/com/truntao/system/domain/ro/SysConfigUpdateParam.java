package com.truntao.system.domain.ro;

import com.truntao.system.domain.po.SysConfig;
import lombok.Data;
import lombok.ToString;

/**
 * 参数配置对象 sys_config
 *
 * @author truntao
 * @date 2023-08-22
 */
@Data
@ToString
public class SysConfigUpdateParam {

    /**
     * 参数主键
     */
    private Long configId;
    /**
     * 参数名称
     */
    private String configName;
    /**
     * 参数键名
     */
    private String configKey;
    /**
     * 参数键值
     */
    private String configValue;
    /**
     * 系统内置（Y是 N否）
     */
    private String configType;
    /**
     * 备注
     */
    private String remark;

    public SysConfig getSysConfig() {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigId(getConfigId());
        sysConfig.setConfigName(getConfigName());
        sysConfig.setConfigKey(getConfigKey());
        sysConfig.setConfigValue(getConfigValue());
        sysConfig.setConfigType(getConfigType());
        sysConfig.setRemark(getRemark());
        return sysConfig;
    }
}
