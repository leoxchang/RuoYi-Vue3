package com.truntao.system.domain.ro;

import lombok.Data;
import com.truntao.system.domain.po.SysNotice;
import lombok.ToString;

/**
 * 通知公告对象 sys_notice
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysNoticeParam {
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
     * 备注
     */
    private String remark;

    public SysNotice getSysNotice() {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeTitle(getNoticeTitle());
        sysNotice.setNoticeType(getNoticeType());
        sysNotice.setNoticeContent(getNoticeContent());
        sysNotice.setStatus(getStatus());
        sysNotice.setRemark(getRemark());
        return sysNotice;
    }
}
