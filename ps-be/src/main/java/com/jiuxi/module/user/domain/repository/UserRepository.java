package com.jiuxi.module.user.domain.repository;

import com.jiuxi.module.user.domain.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * 用户仓储接口
 * 领域层的仓储接口，定义用户聚合根的持久化操作
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
public interface UserRepository {
    
    /**
     * 保存用户
     */
    User save(User user);
    
    /**
     * 根据用户ID查找用户
     */
    Optional<User> findById(String personId);
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据手机号查找用户
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据身份证号查找用户
     */
    Optional<User> findByIdCard(String idCard);
    
    /**
     * 根据部门ID查找用户列表
     */
    List<User> findByDepartmentId(String deptId);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 检查身份证号是否存在
     */
    boolean existsByIdCard(String idCard);
    
    /**
     * 删除用户（逻辑删除）
     */
    void deleteById(String personId);
    
    /**
     * 批量删除用户（逻辑删除）
     */
    void deleteByIds(List<String> personIds);
}