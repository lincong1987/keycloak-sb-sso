package com.jiuxi.datapermission.controller;

import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.datapermission.core.api.TpDataPermissionApiService;
import com.jiuxi.datapermission.core.bean.dto.TpDataPermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据权限测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestDataPermissionController {

    @Autowired
    private TpDataPermissionApiService tpDataPermissionApiService;

    /**
     * 测试获取数据权限
     *
     * @param personId 人员ID
     * @param deptId   部门ID
     * @return JsonResponse
     */
    @RequestMapping("/test_data_permission")
    public JsonResponse testDataPermission(@RequestParam String personId, @RequestParam String deptId) {
        try {
            TpDataPermissionDTO dataPermission = tpDataPermissionApiService.getDataPermission(personId, deptId);
            return JsonResponse.buildSuccess(dataPermission);
        } catch (Exception e) {
            return JsonResponse.buildFailure("获取数据权限失败: " + e.getMessage());
        }
    }

    /**
     * 测试获取数据权限（带表名）
     *
     * @param personId              人员ID
     * @param deptId               部门ID
     * @param mainTableAs          主表别名
     * @param deptIdFieldName      部门ID字段名
     * @param deptLevelcodeFieldName 部门层级编码字段名
     * @return JsonResponse
     */
    @RequestMapping("/test_data_permission_with_table")
    public JsonResponse testDataPermissionWithTable(@RequestParam String personId, 
                                                   @RequestParam String deptId, 
                                                   @RequestParam String mainTableAs,
                                                   @RequestParam String deptIdFieldName,
                                                   @RequestParam String deptLevelcodeFieldName) {
        try {
            TpDataPermissionDTO dataPermission = tpDataPermissionApiService.getDataPermission(personId, deptId, mainTableAs, deptIdFieldName, deptLevelcodeFieldName);
            return JsonResponse.buildSuccess(dataPermission);
        } catch (Exception e) {
            return JsonResponse.buildFailure("获取数据权限失败: " + e.getMessage());
        }
    }
}