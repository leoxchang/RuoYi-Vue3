package com.truntao.system.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.truntao.system.domain.dto.SysPostDTO;
import com.truntao.system.domain.ro.SysPostParam;
import com.truntao.system.domain.ro.SysPostUpdateParam;

/**
 * 岗位信息 服务层
 *
 * @author truntao
 */
public interface ISysPostService {
    /**
     * 查询岗位信息集合
     *
     * @param postParam 岗位信息
     * @return 岗位列表
     */
    Page<SysPostDTO> selectPostList(SysPostParam postParam);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPostDTO> selectPostAll();

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    SysPostDTO selectPostById(Long postId);

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<Long> selectPostListByUserId(Long userId);

    /**
     * 校验岗位名称
     *
     * @param postId   岗位Id
     * @param postName 岗位名称
     * @return 结果
     */
    boolean checkPostNameUnique(Long postId, String postName);

    /**
     * 校验岗位编码
     *
     * @param postId   岗位Id
     * @param postCode 岗位代码
     * @return 结果
     */
    boolean checkPostCodeUnique(Long postId, String postCode);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    int countUserPostById(Long postId);

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    int deletePostById(Long postId);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    int deletePostByIds(Long[] postIds);

    /**
     * 新增保存岗位信息
     *
     * @param postParam 岗位信息
     * @return 结果
     */
    int insertPost(SysPostParam postParam);

    /**
     * 修改保存岗位信息
     *
     * @param postUpdateParam 岗位信息
     * @return 结果
     */
    int updatePost(SysPostUpdateParam postUpdateParam);
}
