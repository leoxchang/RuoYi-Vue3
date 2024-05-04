package com.truntao.system.domain.ro;

import java.util.Date;

import lombok.Data;
import com.truntao.system.domain.po.SysLoginInfo;
import lombok.ToString;

/**
 * 系统访问记录对象 sys_login_info
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysLoginInfoParam {

    /**
     * 用户账号
     */
    private String userName;
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
     * 登录状态（0成功 1失败）
     */
    private String status;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 访问时间
     */
    private Date loginTime;

    public SysLoginInfo getSysLoginInfo() {
        SysLoginInfo sysLoginInfo = new SysLoginInfo();
        sysLoginInfo.setUserName(getUserName());
        sysLoginInfo.setIpaddr(getIpaddr());
        sysLoginInfo.setLoginLocation(getLoginLocation());
        sysLoginInfo.setBrowser(getBrowser());
        sysLoginInfo.setOs(getOs());
        sysLoginInfo.setStatus(getStatus());
        sysLoginInfo.setMsg(getMsg());
        sysLoginInfo.setLoginTime(getLoginTime());
        return sysLoginInfo;
    }
}
