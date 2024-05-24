package com.truntao.system.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truntao.system.domain.po.SysLoginInfo;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.truntao.common.annotation.Excel;

/**
 * 系统访问记录对象 sys_login_info
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
public class SysLoginInfoDTO {

    public SysLoginInfoDTO() {

    }

    public SysLoginInfoDTO(SysLoginInfo sysLoginInfo) {
        setInfoId(sysLoginInfo.getInfoId());
        setUserName(sysLoginInfo.getUserName());
        setIpaddr(sysLoginInfo.getIpaddr());
        setLoginLocation(sysLoginInfo.getLoginLocation());
        setBrowser(sysLoginInfo.getBrowser());
        setOs(sysLoginInfo.getOs());
        setStatus(sysLoginInfo.getStatus());
        setMsg(sysLoginInfo.getMsg());
        setLoginTime(sysLoginInfo.getLoginTime());
    }

    /**
     * 访问ID
     */
    @Excel(name = "访问ID")
    private Long infoId;
    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;
    /**
     * 登录IP地址
     */
    @Excel(name = "登录IP地址")
    private String ipaddr;
    /**
     * 登录地点
     */
    @Excel(name = "登录地点")
    private String loginLocation;
    /**
     * 浏览器类型
     */
    @Excel(name = "浏览器类型")
    private String browser;
    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    private String os;
    /**
     * 登录状态（0成功 1失败）
     */
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    private String status;
    /**
     * 提示消息
     */
    @Excel(name = "提示消息")
    private String msg;
    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("infoId", getInfoId())
                .append("userName", getUserName())
                .append("ipaddr", getIpaddr())
                .append("loginLocation", getLoginLocation())
                .append("browser", getBrowser())
                .append("os", getOs())
                .append("status", getStatus())
                .append("msg", getMsg())
                .append("loginTime", getLoginTime())
                .toString();
    }
}
