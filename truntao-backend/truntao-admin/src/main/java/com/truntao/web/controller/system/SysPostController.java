package com.truntao.web.controller.system;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.system.domain.dto.SysPostDTO;
import com.truntao.system.domain.ro.SysPostParam;
import com.truntao.system.domain.ro.SysPostUpdateParam;
import com.truntao.system.service.ISysPostService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    @Resource
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermission('system:post:list')")
    @GetMapping("/list")
    public R<PageDTO<SysPostDTO>> list(SysPostParam postParam) {
        startPage();
        Page<SysPostDTO> page = postService.selectPostList(postParam);
        return R.ok(new PageDTO<>(page));
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPostParam postParam) {
        List<SysPostDTO> list = postService.selectPostList(postParam);
        ExcelUtil<SysPostDTO> util = new ExcelUtil<>(SysPostDTO.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    @GetMapping(value = "/{postId}")
    public R<SysPostDTO> getInfo(@PathVariable("postId") Long postId) {
        return R.ok(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermission('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysPostParam postParam) {
        if (!postService.checkPostNameUnique(null, postParam.getPostName())) {
            return R.fail("新增岗位'" + postParam.getPostName() + "'失败，岗位名称已存在");
        } else if (!postService.checkPostCodeUnique(null, postParam.getPostCode())) {
            return R.fail("新增岗位'" + postParam.getPostName() + "'失败，岗位编码已存在");
        }
        return R.ok(postService.insertPost(postParam));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermission('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysPostUpdateParam postUpdateParam) {
        if (!postService.checkPostNameUnique(postUpdateParam.getPostId(), postUpdateParam.getPostName())) {
            return R.fail("修改岗位'" + postUpdateParam.getPostName() + "'失败，岗位名称已存在");
        } else if (!postService.checkPostCodeUnique(postUpdateParam.getPostId(), postUpdateParam.getPostCode())) {
            return R.fail("修改岗位'" + postUpdateParam.getPostName() + "'失败，岗位编码已存在");
        }
        return R.ok(postService.updatePost(postUpdateParam));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermission('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public R<Integer> remove(@PathVariable("postIds") Long[] postIds) {
        return R.ok(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/option-select")
    public R<List<SysPostDTO>> optionSelect() {
        List<SysPostDTO> posts = postService.selectPostAll();
        return R.ok(posts);
    }
}
