package com.truntao.system.service.impl;

import com.github.pagehelper.Page;
import com.truntao.system.domain.dto.SysLoginInfoDTO;
import com.truntao.system.domain.po.SysLoginInfo;
import com.truntao.system.domain.ro.SysLoginInfoParam;
import com.truntao.system.mapper.SysLoginInfoMapper;
import com.truntao.system.service.ISysLoginInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author truntao
 */
@Service
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

    @Resource
    private SysLoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    @Override
    public void insertLoginInfo(SysLoginInfo loginInfo) {
        loginInfo.setLoginTime(new Date());
        loginInfoMapper.insert(loginInfo);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfoParam 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public Page<SysLoginInfoDTO> selectLoginInfoList(SysLoginInfoParam loginInfoParam) {
        SysLoginInfo loginInfo = loginInfoParam.getSysLoginInfo();
        Page<SysLoginInfoDTO> page = new Page<>();
        try (Page<SysLoginInfo> list = loginInfoMapper.selectLoginInfoList(loginInfo)){
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysLoginInfoDTO::new).toList());
        }

        return page;
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        return loginInfoMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginInfo() {
        loginInfoMapper.cleanLoginInfo();
    }
}
