package com.topinfo.basic.platform.plugin.data.permission.core.service.impl;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.topinfo.basic.platform.common.util.SnowflakeIdUtil;

import com.topinfo.basic.platform.plugin.data.permission.core.mapper.TpDataPermissionDeptMapper;
import com.topinfo.basic.platform.plugin.data.permission.core.bean.entity.TpDataPermissionDept;
import com.topinfo.basic.platform.plugin.data.permission.core.service.TpDataPermissionDeptService;

import com.topinfo.basic.platform.common.bean.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TpDataPermissionDeptServiceImpl
 * @Description: 数据权限部门表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service
public class TpDataPermissionDeptServiceImpl implements TpDataPermissionDeptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TpDataPermissionDeptServiceImpl.class);

    @Autowired
    private  TpDataPermissionDeptMapper  tpDataPermissionDeptMapper;

    /**
     * 保存指定的部门
     *
     * @param permId
     * @param deptIds
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 19:32
     */
    @Override
    public void save(String permId, String deptIds, String tenantId) {

        // 先根据外键删除所有
        tpDataPermissionDeptMapper.deleteById(permId);

        if (StrUtil.isBlank(deptIds)) {
            // 指定部门id为空则直接返回
            return;
        }

        String[] ids = deptIds.split(",");
        List<TpDataPermissionDept> list = new ArrayList<>();
        for (String deptId: ids) {
            if (StrUtil.isBlank(deptId)) {
                continue;
            }
            TpDataPermissionDept dept = new TpDataPermissionDept();
            dept.setDeptId(deptId);
            dept.setPermId(permId);
            dept.setId(SnowflakeIdUtil.nextIdStr());
            dept.setTenantId(tenantId);
            list.add(dept);
        }

        if (CollectionUtil.isNotEmpty(list)) {
            tpDataPermissionDeptMapper.addBatch(list);
        }
    }

    /**
     * 根据外键查询所有指定的部门id
     *
     * @param permId
     * @return java.util.List<java.lang.String>
     * @author 杨占锐
     * @date 2024/12/3 19:48
     */
    @Override
    public List<String> getById(String permId) {
        return tpDataPermissionDeptMapper.getById(permId);
    }

    /**
     * 根据部门id，查询部门节点对象信息
     *
     * @param deptIds
     * @return java.util.List<javax.swing.tree.TreeNode>
     * @author 杨占锐
     * @date 2024/12/5 11:11
     */
    @Override
    public List<TreeNode> listDeptNodes(List<String> deptIds) {
        if (CollectionUtil.isEmpty(deptIds)) {
            return new ArrayList<>();
        }
        return tpDataPermissionDeptMapper.listDeptNodes(deptIds);
    }
}