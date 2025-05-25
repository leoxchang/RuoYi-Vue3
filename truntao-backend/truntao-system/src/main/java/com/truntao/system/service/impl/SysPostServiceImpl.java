package com.truntao.system.service.impl;

import com.github.pagehelper.Page;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.exception.ServiceException;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.system.domain.dto.SysPostDTO;
import com.truntao.system.domain.po.SysPost;
import com.truntao.system.domain.ro.SysPostParam;
import com.truntao.system.domain.ro.SysPostUpdateParam;
import com.truntao.system.mapper.SysPostMapper;
import com.truntao.system.mapper.SysUserPostMapper;
import com.truntao.system.service.ISysPostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 岗位信息 服务层处理
 *
 * @author truntao
 */
@Service
public class SysPostServiceImpl implements ISysPostService {
    @Resource
    private SysPostMapper postMapper;

    @Resource
    private SysUserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     *
     * @param postParam 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public Page<SysPostDTO> selectPostList(SysPostParam postParam) {
        SysPost post = postParam.getSysPost();
        Page<SysPostDTO> page = new Page<>();
        try (Page<SysPost> list = postMapper.selectPostList(post)) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysPostDTO::new).toList());
        }
        return page;
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPostDTO> selectPostAll() {
        return postMapper.selectPostAll().stream().map(SysPostDTO::new).toList();
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPostDTO selectPostById(Long postId) {
        SysPost post = postMapper.selectPostById(postId);
        return new SysPostDTO(post);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return postMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param postId 岗位Id
     * @param postName 岗位名称
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(Long postId,String postName) {
        postId = Objects.isNull(postId) ? -1L : postId;
        SysPost info = postMapper.checkPostNameUnique(postName);
        if (Objects.nonNull(info) && !Objects.equals(info.getPostId(), postId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param postId 岗位Id
     * @param postCode 岗位代码
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(Long postId, String postCode) {
        postId = Objects.isNull(postId) ? -1L : postId;
        SysPost info = postMapper.checkPostCodeUnique(postCode);
        if (Objects.nonNull(info) && !Objects.equals(info.getPostId(), postId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId) {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int deletePostById(Long postId) {
        return postMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    @Override
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            SysPostDTO postDTO = selectPostById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", postDTO.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     *
     * @param postParam 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(SysPostParam postParam) {
        SysPost post = postParam.getSysPost();
        post.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        post.setCreateTime(new Date());
        return postMapper.insert(post);
    }

    /**
     * 修改保存岗位信息
     *
     * @param postUpdateParam 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(SysPostUpdateParam postUpdateParam) {
        SysPost post = postUpdateParam.getSysPost();
        post.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        post.setUpdateTime(new Date());
        return postMapper.updateById(post);
    }
}
