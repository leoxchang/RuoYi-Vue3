package com.truntao.system.domain.dto;

import com.truntao.common.annotation.Excel;
import com.truntao.common.annotation.Excel.ColumnType;
import com.truntao.system.domain.po.SysPost;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 岗位表 sys_post
 *
 * @author truntao
 */
@Data
public class SysPostDTO {

    public SysPostDTO() {

    }

    public SysPostDTO(SysPost post) {
        setPostId(post.getPostId());
        setPostCode(post.getPostCode());
        setPostName(post.getPostName());
        setPostSort(post.getPostSort());
        setStatus(post.getStatus());
        setFlag(isFlag());
    }

    /**
     * 岗位序号
     */
    @Excel(name = "岗位序号", cellType = ColumnType.NUMERIC)
    private Long postId;

    /**
     * 岗位编码
     */
    @Excel(name = "岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    private String postName;

    /**
     * 岗位排序
     */
    @Excel(name = "岗位排序")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 用户是否存在此岗位标识 默认不存在
     */
    private boolean flag = false;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("postId", getPostId())
                .append("postCode", getPostCode())
                .append("postName", getPostName())
                .append("postSort", getPostSort())
                .append("status", getStatus())
                .toString();
    }
}
