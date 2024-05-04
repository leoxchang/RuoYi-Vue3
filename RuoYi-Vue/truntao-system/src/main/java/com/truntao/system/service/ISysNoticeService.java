package com.truntao.system.service;

import com.github.pagehelper.Page;
import com.truntao.system.domain.dto.SysNoticeDTO;
import com.truntao.system.domain.ro.SysNoticeParam;
import com.truntao.system.domain.ro.SysNoticeUpdateParam;

/**
 * 公告 服务层
 *
 * @author truntao
 */
public interface ISysNoticeService {
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    SysNoticeDTO selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param noticeParam 公告信息
     * @return 公告集合
     */
    Page<SysNoticeDTO> selectNoticeList(SysNoticeParam noticeParam);

    /**
     * 新增公告
     *
     * @param noticeParam 公告信息
     * @return 结果
     */
    int insertNotice(SysNoticeParam noticeParam);

    /**
     * 修改公告
     *
     * @param noticeUpdateParam 公告信息
     * @return 结果
     */
    int updateNotice(SysNoticeUpdateParam noticeUpdateParam);

    /**
     * 删除公告信息
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    int deleteNoticeById(Long noticeId);

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    int deleteNoticeByIds(Long[] noticeIds);
}
