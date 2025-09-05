package com.jiuxi.module.org.app.service;

import com.jiuxi.module.org.domain.entity.Department;
import com.jiuxi.module.org.domain.entity.DepartmentStatus;
import com.jiuxi.module.org.domain.event.DepartmentCreatedEvent;
import com.jiuxi.module.org.domain.event.DepartmentDeletedEvent;
import com.jiuxi.module.org.domain.event.DepartmentUpdatedEvent;
import com.jiuxi.module.org.domain.repo.DepartmentRepository;
import com.jiuxi.module.org.domain.service.DepartmentDomainService;
import com.jiuxi.module.org.app.dto.DepartmentCreateDTO;
import com.jiuxi.module.org.app.dto.DepartmentUpdateDTO;
import com.jiuxi.module.org.app.dto.DepartmentResponseDTO;
import com.jiuxi.module.org.app.assembler.DepartmentAssembler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 部门应用服务
 * 负责部门相关的应用逻辑和事务协调
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
@Service
@Transactional
public class DepartmentApplicationService {
    
    private final DepartmentRepository departmentRepository;
    private final DepartmentDomainService departmentDomainService;
    private final DepartmentAssembler departmentAssembler;
    private final ApplicationEventPublisher eventPublisher;
    
    public DepartmentApplicationService(DepartmentRepository departmentRepository,
                                       DepartmentDomainService departmentDomainService,
                                       DepartmentAssembler departmentAssembler,
                                       ApplicationEventPublisher eventPublisher) {
        this.departmentRepository = departmentRepository;
        this.departmentDomainService = departmentDomainService;
        this.departmentAssembler = departmentAssembler;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * 创建部门
     * @param createDTO 创建请求
     * @param tenantId 租户ID
     * @param operator 操作者
     * @return 部门响应
     */
    public DepartmentResponseDTO createDepartment(DepartmentCreateDTO createDTO, String tenantId, String operator) {
        // 创建部门聚合根
        Department department = new Department();
        department.setDeptId(UUID.randomUUID().toString());
        department.setDeptName(createDTO.getDeptName());
        department.setDeptSimpleName(createDTO.getDeptSimpleName());
        department.setDeptFullName(createDTO.getDeptFullName());
        department.setParentDeptId(createDTO.getParentDeptId());
        department.setType(createDTO.getType());
        department.setManagerId(createDTO.getManagerId());
        department.setContactPhone(createDTO.getContactPhone());
        department.setAddress(createDTO.getAddress());
        department.setDescription(createDTO.getDescription());
        department.setOrderIndex(createDTO.getOrderIndex());
        department.setTenantId(tenantId);
        department.setCreator(operator);
        department.setCreateTime(LocalDateTime.now());
        department.setStatus(DepartmentStatus.ACTIVE);
        
        // 计算部门层级和路径
        Integer deptLevel = departmentDomainService.calculateDeptLevel(createDTO.getParentDeptId());
        department.setDeptLevel(deptLevel);
        
        String deptPath = departmentDomainService.buildDeptPath(createDTO.getParentDeptId(), department.getDeptId());
        department.setDeptPath(deptPath);
        
        // 业务规则验证
        departmentDomainService.validateForCreate(department, tenantId);
        
        // 保存部门
        Department savedDepartment = departmentRepository.save(department);
        
        // 发布创建事件
        DepartmentCreatedEvent event = new DepartmentCreatedEvent(
            savedDepartment.getDeptId(),
            savedDepartment.getDeptName(),
            savedDepartment.getParentDeptId(),
            savedDepartment.getDeptPath(),
            savedDepartment.getDeptLevel(),
            tenantId,
            operator
        );
        eventPublisher.publishEvent(event);
        
        return departmentAssembler.toResponseDTO(savedDepartment);
    }
    
    /**
     * 更新部门
     * @param deptId 部门ID
     * @param updateDTO 更新请求
     * @param tenantId 租户ID
     * @param operator 操作者
     * @return 部门响应
     */
    public DepartmentResponseDTO updateDepartment(String deptId, DepartmentUpdateDTO updateDTO, 
                                                 String tenantId, String operator) {
        // 查找现有部门
        Optional<Department> existingDeptOpt = departmentRepository.findById(deptId);
        if (existingDeptOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在: " + deptId);
        }
        
        Department existingDept = existingDeptOpt.get();
        String oldDeptName = existingDept.getDeptName();
        String oldParentDeptId = existingDept.getParentDeptId();
        
        // 更新部门信息
        existingDept.setDeptName(updateDTO.getDeptName());
        existingDept.setDeptSimpleName(updateDTO.getDeptSimpleName());
        existingDept.setDeptFullName(updateDTO.getDeptFullName());
        existingDept.setParentDeptId(updateDTO.getParentDeptId());
        existingDept.setType(updateDTO.getType());
        existingDept.setManagerId(updateDTO.getManagerId());
        existingDept.setContactPhone(updateDTO.getContactPhone());
        existingDept.setAddress(updateDTO.getAddress());
        existingDept.setDescription(updateDTO.getDescription());
        existingDept.setOrderIndex(updateDTO.getOrderIndex());
        existingDept.setUpdator(operator);
        existingDept.setUpdateTime(LocalDateTime.now());
        
        // 如果父部门发生变化，重新计算层级和路径
        if (!StringUtils.hasText(oldParentDeptId) && StringUtils.hasText(updateDTO.getParentDeptId()) ||
            StringUtils.hasText(oldParentDeptId) && !oldParentDeptId.equals(updateDTO.getParentDeptId())) {
            
            Integer newDeptLevel = departmentDomainService.calculateDeptLevel(updateDTO.getParentDeptId());
            existingDept.setDeptLevel(newDeptLevel);
            
            String newDeptPath = departmentDomainService.buildDeptPath(updateDTO.getParentDeptId(), deptId);
            existingDept.setDeptPath(newDeptPath);
        }
        
        // 业务规则验证
        departmentDomainService.validateForUpdate(existingDept, tenantId);
        
        // 保存部门
        Department savedDepartment = departmentRepository.save(existingDept);
        
        // 发布更新事件
        DepartmentUpdatedEvent event = new DepartmentUpdatedEvent(
            savedDepartment.getDeptId(),
            savedDepartment.getDeptName(),
            savedDepartment.getParentDeptId(),
            savedDepartment.getDeptPath(),
            savedDepartment.getDeptLevel(),
            oldDeptName,
            oldParentDeptId,
            tenantId,
            operator
        );
        eventPublisher.publishEvent(event);
        
        return departmentAssembler.toResponseDTO(savedDepartment);
    }
    
    /**
     * 删除部门
     * @param deptId 部门ID
     * @param tenantId 租户ID
     * @param operator 操作者
     */
    public void deleteDepartment(String deptId, String tenantId, String operator) {
        // 查找现有部门
        Optional<Department> existingDeptOpt = departmentRepository.findById(deptId);
        if (existingDeptOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在: " + deptId);
        }
        
        Department existingDept = existingDeptOpt.get();
        
        // 业务规则验证
        departmentDomainService.validateForDelete(deptId);
        
        // 删除部门
        departmentRepository.deleteById(deptId);
        
        // 发布删除事件
        DepartmentDeletedEvent event = new DepartmentDeletedEvent(
            existingDept.getDeptId(),
            existingDept.getDeptName(),
            existingDept.getParentDeptId(),
            existingDept.getDeptPath(),
            tenantId,
            operator
        );
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 根据ID查询部门
     * @param deptId 部门ID
     * @return 部门响应
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentResponseDTO> findDepartmentById(String deptId) {
        return departmentRepository.findById(deptId)
                .map(departmentAssembler::toResponseDTO);
    }
    
    /**
     * 查询部门树
     * @param tenantId 租户ID
     * @return 部门树
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> findDepartmentTree(String tenantId) {
        List<Department> departments = departmentRepository.findDepartmentTree(tenantId);
        return departments.stream()
                .map(departmentAssembler::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据父部门查询子部门
     * @param parentDeptId 父部门ID
     * @return 子部门列表
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> findChildDepartments(String parentDeptId) {
        List<Department> children = departmentRepository.findByParentId(parentDeptId);
        return children.stream()
                .map(departmentAssembler::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 激活部门
     * @param deptId 部门ID
     * @param operator 操作者
     */
    public void activateDepartment(String deptId, String operator) {
        Optional<Department> deptOpt = departmentRepository.findById(deptId);
        if (deptOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在: " + deptId);
        }
        
        Department department = deptOpt.get();
        department.activate();
        department.setUpdator(operator);
        
        departmentRepository.save(department);
    }
    
    /**
     * 停用部门
     * @param deptId 部门ID
     * @param operator 操作者
     */
    public void deactivateDepartment(String deptId, String operator) {
        Optional<Department> deptOpt = departmentRepository.findById(deptId);
        if (deptOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在: " + deptId);
        }
        
        Department department = deptOpt.get();
        department.deactivate();
        department.setUpdator(operator);
        
        departmentRepository.save(department);
    }
}