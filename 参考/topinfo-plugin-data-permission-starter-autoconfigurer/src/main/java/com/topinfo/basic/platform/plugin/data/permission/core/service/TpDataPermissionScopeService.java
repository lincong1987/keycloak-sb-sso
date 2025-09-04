package com.topinfo.basic.platform.plugin.data.permission.core.service;


import com.topinfo.basic.platform.plugin.data.permission.core.bean.vo.TpDataPermissionScopeVO;

/**
 * @ClassName: TpDataPermissionScopeService
 * @Description: 数据权限范围表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public interface TpDataPermissionScopeService {

    /**
     * 保存
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    String add(TpDataPermissionScopeVO vo, String jwtpid, String tenantId);

    /**
     * 查看数据权限详情
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    TpDataPermissionScopeVO view(String personId, String deptId);

    /**
     * 查看数据权限详情 - 并返回部门名称，用于前端页面回显
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    TpDataPermissionScopeVO viewWithDeptName(String personId, String deptId);

    /**
     * 根据部门id，查询部门层级编码
     *
     * @param deptId
     * @return java.lang.String
     * @author 杨占锐
     * @date 2024/12/4 15:52
     */
    String getDeptLevelcode(String deptId);
}

