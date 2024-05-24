package com.truntao.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.truntao.system.domain.po.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知公告表 数据层
 *
 * @author truntao
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    Page<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * 批量删除公告
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
