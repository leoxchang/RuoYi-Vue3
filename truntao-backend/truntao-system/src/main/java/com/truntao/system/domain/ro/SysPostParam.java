package com.truntao.system.domain.ro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.truntao.system.domain.po.SysPost;

/**
 * 岗位信息对象 sys_post
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
public class SysPostParam {

    /**
     * 岗位ID
     */
    @Schema(description = "岗位id")
    private Long postId;
    /**
     * 岗位编码
     */
    private String postCode;
    /**
     * 岗位名称
     */
    private String postName;
    /**
     * 显示顺序
     */
    private Integer postSort;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    public SysPost getSysPost() {
        SysPost sysPost = new SysPost();
        sysPost.setPostCode(getPostCode());
        sysPost.setPostName(getPostName());
        sysPost.setPostSort(getPostSort());
        sysPost.setStatus(getStatus());
        sysPost.setRemark(getRemark());
        return sysPost;
    }
}
