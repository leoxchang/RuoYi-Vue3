package com.truntao.system.domain.ro;

import com.truntao.system.domain.po.SysPost;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 岗位信息对象 sys_post
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysPostUpdateParam {

    /**
     * 岗位ID
     */
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
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

    public SysPost getSysPost() {
        SysPost sysPost = new SysPost();
        sysPost.setPostId(getPostId());
        sysPost.setPostCode(getPostCode());
        sysPost.setPostName(getPostName());
        sysPost.setPostSort(getPostSort());
        sysPost.setStatus(getStatus());
        sysPost.setRemark(getRemark());
        return sysPost;
    }
}
