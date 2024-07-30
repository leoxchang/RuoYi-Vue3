package com.truntao.system.service.impl;


import com.github.pagehelper.Page;
import com.truntao.system.domain.po.SysOperLog;
import com.truntao.system.domain.dto.SysOperLogDTO;
import com.truntao.system.domain.ro.SysOperLogParam;
import com.truntao.system.mapper.SysOperLogMapper;
import com.truntao.system.service.ISysOperLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 操作日志 服务层处理
 *
 * @author truntao
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {
    @Resource
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperLog(SysOperLog operLog) {
        operLog.setOperTime(new Date());
        operLogMapper.insert(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLogParam 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public Page<SysOperLogDTO> selectOperLogList(SysOperLogParam operLogParam) {
        Page<SysOperLogDTO> page = new Page<>();
        try (Page<SysOperLog> list = operLogMapper.selectOperLogList(operLogParam.getSysOperLog())) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysOperLogDTO::new).toList());
        }
        return page;
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLogDTO selectOperLogById(Long operId) {
        return new SysOperLogDTO(operLogMapper.selectOperLogById(operId));
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
