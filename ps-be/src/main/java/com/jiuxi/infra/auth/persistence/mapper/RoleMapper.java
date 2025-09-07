package com.jiuxi.infra.auth.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuxi.infra.auth.persistence.entity.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper接口
 * 定义角色持久化对象的数据库操作
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {
    
}