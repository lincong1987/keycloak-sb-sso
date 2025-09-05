package com.jiuxi.module.user.infrastructure.persistence.repository;

import com.jiuxi.module.user.domain.entity.User;
import com.jiuxi.module.user.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户仓储实现类（临时实现）
 * 桥接到原有的服务，确保应用能正常启动
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @Override
    public User save(User user) {
        // 临时实现，避免启动错误
        throw new UnsupportedOperationException("用户保存功能暂未实现，需要桥接到原有TpPersonBasicinfoService");
    }
    
    @Override
    public Optional<User> findById(String personId) {
        // 临时实现，避免启动错误
        return Optional.empty();
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        // 临时实现，避免启动错误
        return Optional.empty();
    }
    
    @Override
    public Optional<User> findByPhone(String phone) {
        // 临时实现，避免启动错误
        return Optional.empty();
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        // 临时实现，避免启动错误
        return Optional.empty();
    }
    
    @Override
    public Optional<User> findByIdCard(String idCard) {
        // 临时实现，避免启动错误
        return Optional.empty();
    }
    
    @Override
    public List<User> findByDepartmentId(String deptId) {
        // 临时实现，避免启动错误
        throw new UnsupportedOperationException("根据部门查询用户功能暂未实现，需要桥接到原有TpPersonBasicinfoService");
    }
    
    @Override
    public boolean existsByUsername(String username) {
        // 临时实现，避免启动错误
        return false;
    }
    
    @Override
    public boolean existsByPhone(String phone) {
        // 临时实现，避免启动错误
        return false;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        // 临时实现，避免启动错误
        return false;
    }
    
    @Override
    public boolean existsByIdCard(String idCard) {
        // 临时实现，避免启动错误
        return false;
    }
    
    @Override
    public void deleteById(String personId) {
        // 临时实现，避免启动错误
        throw new UnsupportedOperationException("删除用户功能暂未实现，需要桥接到原有TpPersonBasicinfoService");
    }
    
    @Override
    public void deleteByIds(List<String> personIds) {
        // 临时实现，避免启动错误
        throw new UnsupportedOperationException("批量删除用户功能暂未实现，需要桥接到原有TpPersonBasicinfoService");
    }
}