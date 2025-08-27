package com.jiuxi.admin.core.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiuxi.admin.core.bean.entity.TpOperateLog;
import com.jiuxi.admin.core.bean.query.TpOperateLogQuery;
import com.jiuxi.admin.core.bean.vo.TpOperateLogVO;
import com.jiuxi.admin.core.mapper.TpOperateLogMapper;
import com.jiuxi.admin.core.service.TpOperateLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: TpOperateLogServiceImpl
 * @Description: 操作日志表服务实现类
 * @Author: System
 * @Date: 2024-01-17
 * @Copyright: www.jiuxi.com Inc. All rights reserved.
 */
@Service
public class TpOperateLogServiceImpl implements TpOperateLogService {

    @Autowired
    private TpOperateLogMapper tpOperateLogMapper;

    @Override
    public IPage<TpOperateLogVO> queryPage(TpOperateLogQuery query) {
        Page<TpOperateLogVO> page = new Page<>(query.getCurrent(), query.getSize());
        return tpOperateLogMapper.getPage(page, query);
    }

    @Override
    public String add(TpOperateLogVO vo, String jwtpid) {
        TpOperateLog entity = new TpOperateLog();
        BeanUtils.copyProperties(vo, entity);
        
        // 设置主键ID
        if (StrUtil.isBlank(entity.getLogId())) {
            entity.setLogId(IdUtil.simpleUUID());
        }
        
        // 设置创建信息
        entity.setCreator(jwtpid);
        entity.setCreateTime(new Date());
        entity.setUpdator(jwtpid);
        entity.setUpdateTime(new Date());
        
        tpOperateLogMapper.add(entity);
        return entity.getLogId();
    }

    @Override
    public int update(TpOperateLogVO vo, String jwtpid) {
        // 操作日志一般不允许修改，此方法可根据业务需要实现
        return 0;
    }

    @Override
    public TpOperateLogVO view(String id) {
        return tpOperateLogMapper.view(id);
    }

    @Override
    public int deleteByIds(List<String> ids, String jwtpid) {
        // 操作日志一般不允许删除，此方法可根据业务需要实现
        return 0;
    }

    @Override
    public void login(String ticket, String username, String category, String token, String ip, String userAgent, String extend01, String extend02, String extend03) {
        TpOperateLog log = new TpOperateLog();
        log.setLogId(IdUtil.simpleUUID());
        log.setUsername(username);
        log.setCategory(category);
        log.setModuleCode("LOGIN");
        log.setOperterType("LOGIN");
        log.setOperterTime(new Date());
        log.setOperterIp(ip);
        log.setOperterBrowser(userAgent);
        log.setExtend01(extend01);
        log.setExtend02(extend02);
        log.setExtend03(extend03);
        log.setActived("1");
        log.setCreator("SYSTEM");
        log.setCreateTime(new Date());
        log.setUpdator("SYSTEM");
        log.setUpdateTime(new Date());
        
        tpOperateLogMapper.add(log);
    }

    @Override
    public void logout(String username, String category, String token, String ip, String userAgent, String extend01, String extend02, String extend03) {
        TpOperateLog log = new TpOperateLog();
        log.setLogId(IdUtil.simpleUUID());
        log.setUsername(username);
        log.setCategory(category);
        log.setModuleCode("LOGOUT");
        log.setOperterType("LOGOUT");
        log.setOperterTime(new Date());
        log.setOperterIp(ip);
        log.setOperterBrowser(userAgent);
        log.setExtend01(extend01);
        log.setExtend02(extend02);
        log.setExtend03(extend03);
        log.setActived("1");
        log.setCreator("SYSTEM");
        log.setCreateTime(new Date());
        log.setUpdator("SYSTEM");
        log.setUpdateTime(new Date());
        
        tpOperateLogMapper.add(log);
    }

    @Override
    public void collection(String moduleCode, String operterType, String operterRid, String username, String category, String token, String ip, String userAgent, String extend01, String extend02, String extend03) {
        TpOperateLog log = new TpOperateLog();
        log.setLogId(IdUtil.simpleUUID());
        log.setModuleCode(moduleCode);
        log.setOperterType(operterType);
        log.setOperterRid(operterRid);
        log.setUsername(username);
        log.setCategory(category);
        log.setOperterTime(new Date());
        log.setOperterIp(ip);
        log.setOperterBrowser(userAgent);
        log.setExtend01(extend01);
        log.setExtend02(extend02);
        log.setExtend03(extend03);
        log.setActived("1");
        log.setCreator("SYSTEM");
        log.setCreateTime(new Date());
        log.setUpdator("SYSTEM");
        log.setUpdateTime(new Date());
        
        tpOperateLogMapper.add(log);
    }
}