package com.truntao.system.domain.dto;

import com.truntao.system.domain.po.SysNotice;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.truntao.common.annotation.Excel;

import java.util.Date;

/**
 * 通知公告对象 sys_notice
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
public class SysNoticeDTO {

    public SysNoticeDTO() {

    }

    public SysNoticeDTO(SysNotice sysNotice) {
        setNoticeId(sysNotice.getNoticeId());
        setNoticeTitle(sysNotice.getNoticeTitle());
        setNoticeType(sysNotice.getNoticeType());
        setNoticeContent(sysNotice.getNoticeContent());
        setStatus(sysNotice.getStatus());
        setCreateBy(sysNotice.getCreateBy());
        setCreateTime(sysNotice.getCreateTime());
        setUpdateBy(sysNotice.getUpdateBy());
        setUpdateTime(sysNotice.getUpdateTime());
        setRemark(sysNotice.getRemark());
    }

    /**
     * 公告ID
     */
    @Excel(name = "公告ID")
    private Long noticeId;
    /**
     * 公告标题
     */
    @Excel(name = "公告标题")
    private String noticeTitle;
    /**
     * 公告类型（1通知 2公告）
     */
    @Excel(name = "公告类型", readConverterExp = "1=通知,2=公告")
    private String noticeType;
    /**
     * 公告内容
     */
    @Excel(name = "公告内容")
    private String noticeContent;
    /**
     * 公告状态（0正常 1关闭）
     */
    @Excel(name = "公告状态", readConverterExp = "0=正常,1=关闭")
    private String status;
    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId", getNoticeId())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeType", getNoticeType())
                .append("noticeContent", getNoticeContent())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
