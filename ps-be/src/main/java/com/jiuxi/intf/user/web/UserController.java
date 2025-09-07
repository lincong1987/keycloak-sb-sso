package com.jiuxi.intf.user.web;

import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.core.core.annotation.Authorization;
import com.jiuxi.core.core.annotation.IgnoreAuthorization;
import com.jiuxi.core.core.validator.group.AddGroup;
import com.jiuxi.core.core.validator.group.UpdateGroup;
import com.jiuxi.app.user.dto.UserCreateDTO;
import com.jiuxi.app.user.dto.UserQueryDTO;
import com.jiuxi.app.user.dto.UserResponseDTO;
import com.jiuxi.app.user.dto.UserUpdateDTO;
import com.jiuxi.app.user.service.UserApplicationService;
import com.jiuxi.security.core.entity.vo.PersonVO;
import com.jiuxi.security.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 * 采用DDD架构的用户管理接口
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
@RestController
@RequestMapping("/api/v1/users")
@Authorization
public class UserController {

    /**
     * 接口配置 passKey
     */
    private static final String PASS_KEY = "personId";

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private PersonService personService;

    /**
     * 登录成功后,获取用户信息
     */
    @GetMapping("/me")
    @IgnoreAuthorization
    public JsonResponse getCurrentUserInfo(
            @RequestHeader(value = "X-User-Dept-Id", required = false) String jwtdid,
            @RequestHeader(value = "X-User-Person-Id", required = false) String jwtpid,
            @RequestHeader(value = "X-User-Role-Ids", required = false) String jwtrids,
            @RequestHeader(value = "X-User-City-Code", required = false) String jwtCityCode) {
        
        PersonVO vo = personService.getUserInfo(jwtdid, jwtpid);
        vo.setRoleIds(jwtrids);
        vo.setCityCode(jwtCityCode);
        
        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 创建用户
     */
    @PostMapping
    public JsonResponse createUser(
            @Validated(AddGroup.class) @RequestBody UserCreateDTO createDTO,
            @RequestHeader("X-User-Person-Id") String operator,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        
        try {
            String personId = userApplicationService.createUser(createDTO, tenantId, operator);
            return JsonResponse.buildSuccess(personId);
        } catch (Exception e) {
            return JsonResponse.buildFailure("用户创建失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{personId}")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse updateUser(
            @PathVariable String personId,
            @Validated(UpdateGroup.class) @RequestBody UserUpdateDTO updateDTO,
            @RequestHeader("X-User-Person-Id") String operator,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        
        try {
            updateDTO.setPersonId(personId);
            userApplicationService.updateUser(updateDTO, tenantId, operator);
            return JsonResponse.buildSuccess("更新成功");
        } catch (Exception e) {
            return JsonResponse.buildFailure("用户更新失败: " + e.getMessage());
        }
    }

    /**
     * 查看用户详情
     */
    @GetMapping("/{personId}")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse getUserDetail(@PathVariable String personId) {
        try {
            UserResponseDTO user = userApplicationService.getUserById(personId);
            return JsonResponse.buildSuccess(user);
        } catch (Exception e) {
            return JsonResponse.buildFailure("查询用户失败: " + e.getMessage());
        }
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public JsonResponse getUserByUsername(
            @PathVariable String username,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        
        try {
            UserResponseDTO user = userApplicationService.getUserByUsername(username, tenantId);
            return JsonResponse.buildSuccess(user);
        } catch (Exception e) {
            return JsonResponse.buildFailure("查询用户失败: " + e.getMessage());
        }
    }

    /**
     * 根据部门查询用户列表
     */
    @GetMapping("/departments/{deptId}")
    public JsonResponse getUsersByDepartment(@PathVariable String deptId) {
        try {
            List<UserResponseDTO> users = userApplicationService.getUsersByDepartment(deptId);
            return JsonResponse.buildSuccess(users);
        } catch (Exception e) {
            return JsonResponse.buildFailure("查询部门用户失败: " + e.getMessage());
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{personId}")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse deleteUser(
            @PathVariable String personId,
            @RequestHeader("X-User-Person-Id") String operator) {
        
        try {
            userApplicationService.deleteUser(personId, operator);
            return JsonResponse.buildSuccess("删除成功");
        } catch (Exception e) {
            return JsonResponse.buildFailure("删除用户失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    public JsonResponse deleteUsers(
            @RequestBody List<String> personIds,
            @RequestHeader("X-User-Person-Id") String operator) {
        
        try {
            userApplicationService.deleteUsers(personIds, operator);
            return JsonResponse.buildSuccess("批量删除成功");
        } catch (Exception e) {
            return JsonResponse.buildFailure("批量删除失败: " + e.getMessage());
        }
    }

    /**
     * 激活用户
     */
    @PutMapping("/{personId}/activate")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse activateUser(
            @PathVariable String personId,
            @RequestHeader("X-User-Person-Id") String operator) {
        
        try {
            userApplicationService.activateUser(personId, operator);
            return JsonResponse.buildSuccess("激活成功");
        } catch (Exception e) {
            return JsonResponse.buildFailure("激活用户失败: " + e.getMessage());
        }
    }

    /**
     * 停用用户
     */
    @PutMapping("/{personId}/deactivate")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse deactivateUser(
            @PathVariable String personId,
            @RequestHeader("X-User-Person-Id") String operator) {
        
        try {
            userApplicationService.deactivateUser(personId, operator);
            return JsonResponse.buildSuccess("停用成功");
        } catch (Exception e) {
            return JsonResponse.buildFailure("停用用户失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询用户列表
     */
    @PostMapping("/search")
    public JsonResponse searchUsers(@RequestBody UserQueryDTO queryDTO) {
        try {
            List<UserResponseDTO> users = userApplicationService.getUserPage(queryDTO);
            return JsonResponse.buildSuccess(users);
        } catch (UnsupportedOperationException e) {
            return JsonResponse.buildFailure("分页查询功能正在开发中");
        } catch (Exception e) {
            return JsonResponse.buildFailure("查询用户失败: " + e.getMessage());
        }
    }
}