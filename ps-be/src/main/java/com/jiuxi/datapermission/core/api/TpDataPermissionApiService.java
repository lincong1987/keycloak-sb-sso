package com.jiuxi.datapermission.core.api;

import com.jiuxi.datapermission.core.bean.dto.TpDataPermissionDTO;

/**
 * @ClassName: TpDataPermissionApiService
 * @Description: 对外接口
 * @Author 杨占锐
 * @Date 2024/12/3 20:24
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public interface TpDataPermissionApiService {

    /**
     * 查询数据权限
     *
     * @param personId   当前登录人id
     * @param deptId     当前登录人id
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 20:29
     */
    TpDataPermissionDTO getDataPermission(String personId, String deptId);

    /**
     * 查询数据权限
     *
     * @param personId               当前登录人id
     * @param deptId                 当前登录人id
     * @param mainTableAs             主表别名
     * @param deptIdFieldName         主表中dept字段名称
     * @param deptLevelcodeFieldName  主表中deptLevlecode字段名称
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 20:29
     */
    TpDataPermissionDTO getDataPermission(String personId, String deptId, String mainTableAs, String deptIdFieldName, String deptLevelcodeFieldName);
}