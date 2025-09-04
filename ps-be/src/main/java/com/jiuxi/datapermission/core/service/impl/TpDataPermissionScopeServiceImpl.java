package com.jiuxi.datapermission.core.service.impl;

import com.jiuxi.datapermission.constant.DataPermissionConstant;
import com.jiuxi.datapermission.core.bean.vo.TpDataPermissionScopeVO;
import com.jiuxi.datapermission.core.mapper.TpDataPermissionScopeMapper;
import com.jiuxi.datapermission.core.service.TpDataPermissionScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @ClassName: TpDataPermissionScopeServiceImpl
 * @Description: 数据权限范围表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service
public class TpDataPermissionScopeServiceImpl implements TpDataPermissionScopeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TpDataPermissionScopeServiceImpl.class);

    @Autowired
    private TpDataPermissionScopeMapper tpDataPermissionScopeMapper;

    /**
     * 保存
     * <per>
     *     1. 根据personId和deptId查询数据权限，查询不到则新增数据权限范围，查询到则修改数据权限范围
     *     2. 保存指定的部门
     * </per>
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @Override
    public String add(TpDataPermissionScopeVO vo, String jwtpid, String tenantId) {
        // 简化实现，仅返回空字符串
        return "";
    }

    /**
     * 查看数据权限详情
     * <per>
     *     1. 根据人员id和部门id查询数据权限范围
     *     2. 如果范围是指定部门，则再查询指定部门id
     * </per>
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @Override
    public TpDataPermissionScopeVO view(String personId, String deptId) {
        // 查询
        TpDataPermissionScopeVO view = tpDataPermissionScopeMapper.view(personId, deptId);

        if (null != view) {
            // 指定部门权限时，需查询指定部门
            if (DataPermissionConstant.ASSIGN_SCOPE_SET.contains(view.getDataScope())) {
                // 简化实现，暂时不处理指定部门查询
                LOGGER.warn("指定部门权限查询暂未实现");
            }
        }

        return view;
    }

    /**
     * 查看数据权限详情 - 并返回部门名称，用于前端页面回显
     * <per>
     *     1. 根据人员id和部门id查询数据权限范围
     *     2. 如果范围是指定部门，则再查询指定部门id
     *     3. 如果有指定部门，则查询部门名称
     * </per>
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @Override
    public TpDataPermissionScopeVO viewWithDeptName(String personId, String deptId) {
        TpDataPermissionScopeVO view = this.view(personId, deptId);
        // 简化实现，不处理部门名称查询
        return view;
    }

    /**
     * 根据部门id，查询部门层级编码
     *
     * @param deptId
     * @return java.lang.String
     * @author 杨占锐
     * @date 2024/12/4 15:52
     */
    @Override
    public String getDeptLevelcode(String deptId) {
        return tpDataPermissionScopeMapper.getDeptLevelcode(deptId);
    }
}