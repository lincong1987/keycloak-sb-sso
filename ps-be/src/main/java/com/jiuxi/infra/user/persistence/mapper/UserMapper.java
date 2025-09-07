package com.jiuxi.infra.user.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuxi.infra.user.persistence.entity.UserPO;
import org.apache.ibatis.annotations.Param;
// 添加MyBatis注解
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * 用户Mapper接口
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
    
    /**
     * 根据ID查询用户
     */
    UserPO selectById(@Param("personId") String personId);
    
    /**
     * 根据用户名和租户ID查询用户
     */
    Optional<UserPO> findByUsername(@Param("username") String username, @Param("tenantId") String tenantId);
    
    /**
     * 根据邮箱和租户ID查询用户
     */
    Optional<UserPO> findByEmail(@Param("email") String email, @Param("tenantId") String tenantId);
    
    /**
     * 根据手机号和租户ID查询用户
     */
    Optional<UserPO> findByPhone(@Param("phone") String phone, @Param("tenantId") String tenantId);
    
    /**
     * 根据身份证号和租户ID查询用户
     */
    Optional<UserPO> findByIdCard(@Param("idCard") String idCard, @Param("tenantId") String tenantId);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(@Param("username") String username, @Param("tenantId") String tenantId, @Param("excludeUserId") String excludeUserId);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(@Param("email") String email, @Param("tenantId") String tenantId, @Param("excludeUserId") String excludeUserId);
    
    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(@Param("phone") String phone, @Param("tenantId") String tenantId, @Param("excludeUserId") String excludeUserId);
    
    /**
     * 检查身份证号是否存在
     */
    boolean existsByIdCard(@Param("idCard") String idCard, @Param("tenantId") String tenantId, @Param("excludeUserId") String excludeUserId);
    
    /**
     * 根据部门ID查询用户列表
     */
    List<UserPO> findByDeptId(@Param("deptId") String deptId);
    
    /**
     * 根据租户ID查询用户列表
     */
    List<UserPO> findByTenantId(@Param("tenantId") String tenantId);
    
    /**
     * 分页查询用户列表
     */
    IPage<UserPO> queryPage(IPage<UserPO> page, @Param("query") Object query);
    
    /**
     * 查询用户列表
     */
    List<UserPO> queryList(@Param("query") Object query);
    
    /**
     * 根据ID查询用户详情
     */
    UserPO selectByPersonId(@Param("personId") String personId);
    
    /**
     * 批量删除用户
     */
    int deleteByPersonIds(@Param("personIds") List<String> personIds);
    
    /**
     * 更新用户
     */
    int updateById(UserPO userPO);
    
    /**
     * 插入用户
     */
    int insert(UserPO userPO);
}