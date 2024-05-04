package com.truntao.system.service.impl;

import java.util.Date;
import com.github.pagehelper.Page;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.system.domain.po.SysNotice;
import com.truntao.system.domain.dto.SysNoticeDTO;
import com.truntao.system.domain.ro.SysNoticeParam;
import com.truntao.system.domain.ro.SysNoticeUpdateParam;
import com.truntao.system.mapper.SysNoticeMapper;
import com.truntao.system.service.ISysNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 公告 服务层实现
 *
 * @author truntao
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {
    @Resource
    private SysNoticeMapper noticeMapper;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNoticeDTO selectNoticeById(Long noticeId) {
        SysNotice sysNotice = noticeMapper.selectNoticeById(noticeId);
        return new SysNoticeDTO(sysNotice);
    }

    /**
     * 查询公告列表
     *
     * @param noticeParam 公告信息
     * @return 公告集合
     */
    @Override
    public Page<SysNoticeDTO> selectNoticeList(SysNoticeParam noticeParam) {
        SysNotice notice = noticeParam.getSysNotice();
        Page<SysNoticeDTO> page = new Page<>();
        try (Page<SysNotice> list = noticeMapper.selectNoticeList(notice)) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysNoticeDTO::new).toList());
        }
        return page;
    }

    /**
     * 新增公告
     *
     * @param noticeParam 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNoticeParam noticeParam) {
        SysNotice notice = noticeParam.getSysNotice();
        notice.setCreateBy(SecurityUtils.getUsername());
        notice.setCreateTime(new Date());
        return noticeMapper.insert(notice);
    }

    /**
     * 修改公告
     *
     * @param noticeUpdateParam 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNoticeUpdateParam noticeUpdateParam) {
        SysNotice notice = noticeUpdateParam.getSysNotice();
        notice.setUpdateBy(SecurityUtils.getUsername());
        notice.setUpdateTime(new Date());
        return noticeMapper.updateById(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId) {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
