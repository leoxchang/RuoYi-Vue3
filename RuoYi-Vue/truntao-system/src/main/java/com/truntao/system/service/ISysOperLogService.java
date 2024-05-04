package com.truntao.system.service;

import com.github.pagehelper.Page;
import com.truntao.system.domain.po.SysOperLog;
import com.truntao.system.domain.dto.SysOperLogDTO;
import com.truntao.system.domain.ro.SysOperLogParam;

/**
 * 操作日志 服务层
 *
 * @author truntao
 */
public interface ISysOperLogService {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperLog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLogParam 操作日志对象
     * @return 操作日志集合
     */
    Page<SysOperLogDTO> selectOperLogList(SysOperLogParam operLogParam);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SysOperLogDTO selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
