package com.truntao.system.domain.ro;

import com.truntao.system.domain.po.SysNotice;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 通知公告对象 sys_notice
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysNoticeUpdateParam {

    /**
     * 公告ID
     */
    private Long noticeId;
    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;
    /**
     * 公告内容
     */
    private String noticeContent;
    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

    public SysNotice getSysNotice() {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeId(getNoticeId());
        sysNotice.setNoticeTitle(getNoticeTitle());
        sysNotice.setNoticeType(getNoticeType());
        sysNotice.setNoticeContent(getNoticeContent());
        sysNotice.setStatus(getStatus());
        sysNotice.setRemark(getRemark());
        return sysNotice;
    }
}
