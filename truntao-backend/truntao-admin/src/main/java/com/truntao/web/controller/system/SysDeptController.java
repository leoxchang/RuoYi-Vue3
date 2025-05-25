package com.truntao.web.controller.system;

import com.truntao.common.annotation.Log;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.dto.SysDeptDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.system.domain.ro.SysDeptParam;
import com.truntao.system.domain.ro.SysDeptUpdateParam;
import com.truntao.system.service.ISysDeptService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Resource
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    @GetMapping("/list")
    public R<List<SysDeptDTO>> list(SysDeptParam deptParam) {
        return R.ok(deptService.selectDeptList(deptParam));
    }

    /**
     * 查询部门列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public R<List<SysDeptDTO>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDeptDTO> depts = deptService.selectDeptList(new SysDeptParam());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), String.valueOf(deptId)));
        return R.ok(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public R<SysDeptDTO> getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return R.ok(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermission('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysDeptParam deptParam) {
        if (!deptService.checkDeptNameUnique(deptParam.getSysDept())) {
            return R.fail("新增部门'" + deptParam.getDeptName() + "'失败，部门名称已存在");
        }
        return R.ok(deptService.insertDept(deptParam));
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermission('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysDeptUpdateParam deptUpdateParam) {
        Long deptId = deptUpdateParam.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(deptUpdateParam.getSysDept())) {
            return R.fail("修改部门'" + deptUpdateParam.getDeptName() + "'失败，部门名称已存在");
        } else if (deptUpdateParam.getParentId().equals(deptId)) {
            return R.fail("修改部门'" + deptUpdateParam.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, deptUpdateParam.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return R.fail("该部门包含未停用的子部门！");
        }
        return R.ok(deptService.updateDept(deptUpdateParam));
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ss.hasPermission('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public R<Integer> remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return R.fail("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return R.fail("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return R.ok(deptService.deleteDeptById(deptId));
    }
}
