package com.truntao.system.service.impl;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.DataScope;
import com.truntao.common.constant.Constants;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.domain.TreeSelect;
import com.truntao.common.core.domain.dto.SysDeptDTO;
import com.truntao.common.core.domain.entity.SysDept;
import com.truntao.common.core.domain.entity.SysRole;
import com.truntao.common.core.text.Convert;
import com.truntao.common.exception.ServiceException;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.common.utils.spring.SpringUtils;
import com.truntao.system.domain.ro.SysDeptParam;
import com.truntao.system.domain.ro.SysDeptUpdateParam;
import com.truntao.system.mapper.SysDeptMapper;
import com.truntao.system.mapper.SysRoleMapper;
import com.truntao.system.service.ISysDeptService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 部门管理 服务实现
 *
 * @author truntao
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    @Resource
    private SysDeptMapper deptMapper;

    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private AdminService adminService;

    /**
     * 查询部门管理数据
     *
     * @param deptParam 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public Page<SysDeptDTO> selectDeptList(SysDeptParam deptParam) {
        Page<SysDeptDTO> page = new Page<>();
        try (Page<SysDept> list = deptMapper.selectDeptList(deptParam.getSysDept())) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysDeptDTO::new).toList());
        }
        return page;
    }

    /**
     * 查询部门树结构信息
     *
     * @param deptParam 部门信息
     * @return 部门树信息集合
     */
    @Override
    public List<TreeSelect> selectDeptTreeList(SysDeptParam deptParam) {
        List<SysDeptDTO> deptList = SpringUtils.getAopProxy(this).selectDeptList(deptParam);
        return buildDeptTreeSelect(deptList);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDeptDTO> buildDeptTree(List<SysDeptDTO> deptList) {
        List<SysDeptDTO> returnList = new ArrayList<>();
        List<Long> tempList = deptList.stream().map(SysDeptDTO::getDeptId).toList();
        for (SysDeptDTO dept : deptList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(deptList, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = deptList;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptList 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDeptDTO> deptList) {
        List<SysDeptDTO> deptTrees = buildDeptTree(deptList);
        return deptTrees.stream().map(TreeSelect::new).toList();
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, Objects.equals(Constants.YES, role.getDeptCheckStrictly()));
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDeptDTO selectDeptById(Long deptId) {
        return new SysDeptDTO(deptMapper.selectDeptById(deptId));
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean checkDeptNameUnique(SysDept dept) {
        long deptId = Objects.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (Objects.nonNull(info) && info.getDeptId() != deptId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId) {
        if (!adminService.isAdmin(SecurityUtils.getUserId()) && Objects.nonNull(deptId)) {
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            List<SysDept> deptList = deptMapper.selectDeptList(dept);
            if (CollectionUtils.isEmpty(deptList)) {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     *
     * @param deptParam 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDeptParam deptParam) {
        SysDept info = deptMapper.selectDeptById(deptParam.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        SysDept dept = deptParam.getSysDept();
        dept.setCreateBy(SecurityUtils.getUsername());
        dept.setAncestors(info.getAncestors() + "," + deptParam.getParentId());
        dept.setCreateTime(new Date());
        return deptMapper.insert(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param deptUpdateParam 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDeptUpdateParam deptUpdateParam) {
        SysDept dept = deptUpdateParam.getSysDept();
        dept.setUpdateBy(SecurityUtils.getUsername());
        dept.setUpdateTime(new Date());
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (Objects.nonNull(newParentDept) && Objects.nonNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && Objects.equals("0", dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDept dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (!children.isEmpty()) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDeptDTO> list, SysDeptDTO t) {
        // 得到子节点列表
        List<SysDeptDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDeptDTO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDeptDTO> getChildList(List<SysDeptDTO> list, SysDeptDTO t) {
        List<SysDeptDTO> tlist = new ArrayList<>();
        for (SysDeptDTO sysDept : list) {
            if (Objects.nonNull(sysDept.getParentId()) && sysDept.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(sysDept);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDeptDTO> list, SysDeptDTO t) {
        return !getChildList(list, t).isEmpty();
    }
}
