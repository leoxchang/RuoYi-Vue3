package com.truntao.system.domain.ro;

import java.util.Date;
import java.util.Map;

import com.truntao.common.core.page.PageDomain;
import lombok.Data;
import com.truntao.system.domain.po.SysOperLog;
import lombok.ToString;

/**
 * 操作日志记录对象 sys_oper_log
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysOperLogParam extends PageDomain {
    /**
     * 模块标题
     */
    private String title;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer operatorType;
    /**
     * 操作人员
     */
    private String operName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 请求URL
     */
    private String operUrl;
    /**
     * 主机地址
     */
    private String operIp;
    /**
     * 操作地点
     */
    private String operLocation;
    /**
     * 请求参数
     */
    private String operParam;
    /**
     * 返回参数
     */
    private String jsonResult;
    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 操作时间
     */
    private Date operTime;
    /**
     * 消耗时间
     */
    private Long costTime;
    /**
     * 时间范围
     */
    private Map<String, Object> params;

    public SysOperLog getSysOperLog() {
        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setTitle(getTitle());
        sysOperLog.setBusinessType(getBusinessType());
        sysOperLog.setMethod(getMethod());
        sysOperLog.setRequestMethod(getRequestMethod());
        sysOperLog.setOperatorType(getOperatorType());
        sysOperLog.setOperName(getOperName());
        sysOperLog.setDeptName(getDeptName());
        sysOperLog.setOperUrl(getOperUrl());
        sysOperLog.setOperIp(getOperIp());
        sysOperLog.setOperLocation(getOperLocation());
        sysOperLog.setOperParam(getOperParam());
        sysOperLog.setJsonResult(getJsonResult());
        sysOperLog.setStatus(getStatus());
        sysOperLog.setErrorMsg(getErrorMsg());
        sysOperLog.setOperTime(getOperTime());
        sysOperLog.setCostTime(getCostTime());
        sysOperLog.setParams(getParams());
        return sysOperLog;
    }
}
