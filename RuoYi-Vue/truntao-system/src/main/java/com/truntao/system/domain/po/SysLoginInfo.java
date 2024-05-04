package com.truntao.system.domain.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.truntao.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author truntao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginInfo extends BaseEntity {

    /**
     * ID
     */
    @TableId
    private Long infoId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    private String status;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private Date loginTime;
}
