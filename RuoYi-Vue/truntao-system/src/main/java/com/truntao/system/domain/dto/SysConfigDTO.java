package com.truntao.system.domain.dto;

import com.truntao.system.domain.po.SysConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.truntao.common.annotation.Excel;

import java.util.Date;

/**
 * 参数配置对象 sys_config
 *
 * @author truntao
 * @date 2023-08-22
 */
@Data
public class SysConfigDTO {

    public SysConfigDTO() {

    }

    public SysConfigDTO(SysConfig sysConfig) {
        setConfigId(sysConfig.getConfigId());
        setConfigName(sysConfig.getConfigName());
        setConfigKey(sysConfig.getConfigKey());
        setConfigValue(sysConfig.getConfigValue());
        setConfigType(sysConfig.getConfigType());
        setCreateBy(sysConfig.getCreateBy());
        setCreateTime(sysConfig.getCreateTime());
        setUpdateBy(sysConfig.getUpdateBy());
        setUpdateTime(sysConfig.getUpdateTime());
        setRemark(sysConfig.getRemark());
    }

    /**
     * 参数主键
     */
    @Excel(name = "参数主键")
    private Long configId;
    /**
     * 参数名称
     */
    @Excel(name = "参数名称")
    private String configName;
    /**
     * 参数键名
     */
    @Excel(name = "参数键名")
    private String configKey;
    /**
     * 参数键值
     */
    @Excel(name = "参数键值")
    private String configValue;
    /**
     * 系统内置（Y是 N否）
     */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;
    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("configId", getConfigId())
                .append("configName", getConfigName())
                .append("configKey", getConfigKey())
                .append("configValue", getConfigValue())
                .append("configType", getConfigType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
