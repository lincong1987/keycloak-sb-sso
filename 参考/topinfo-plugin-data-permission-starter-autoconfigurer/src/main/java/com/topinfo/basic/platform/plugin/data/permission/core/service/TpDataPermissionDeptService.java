package com.topinfo.basic.platform.plugin.data.permission.core.service;


import com.topinfo.basic.platform.common.bean.TreeNode;
import java.util.List;

/**
 * @ClassName: TpDataPermissionDeptService
 * @Description: 数据权限部门表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public interface TpDataPermissionDeptService {

    /**
     * 保存指定的部门
     *
     * @param permId
     * @param deptIds
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 19:32
     */
    void save(String permId, String deptIds, String tenantId);

    /**
     * 根据外键查询所有指定部门
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    List<String> getById(String permId);

    /**
     * 根据部门id，查询部门节点对象信息
     *
     * @param deptIds
     * @return java.util.List<javax.swing.tree.TreeNode>
     * @author 杨占锐
     * @date 2024/12/5 11:11
     */
    List<TreeNode> listDeptNodes(List<String> deptIds);

}

