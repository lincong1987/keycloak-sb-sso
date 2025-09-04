package com.topinfo.basic.platform.plugin.data.permission.core.mapper;


import com.topinfo.basic.platform.plugin.data.permission.core.bean.entity.TpDataPermissionDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.topinfo.basic.platform.common.bean.TreeNode;
import java.util.List;

/**
 * @ClassName: TpDataPermissionDeptMapper
 * @Description: 数据权限部门表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Mapper
public interface TpDataPermissionDeptMapper {

    /**
     * 批量新增
     *
     * @param list
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 19:48
     */
    void addBatch(List<TpDataPermissionDept> list);

    /**
     * 根据外键删除所有
     *
     * @param permId
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 19:48
     */
    void deleteById(@Param("permId") String permId);

    /**
     * 根据外键查询所有指定的部门id
     *
     * @param permId
     * @return java.util.List<java.lang.String>
     * @author 杨占锐
     * @date 2024/12/3 19:48
     */
    List<String> getById(@Param("permId") String permId);

    /**
     * 根据部门id，查询部门节点对象信息
     *
     * @param deptIds
     * @return java.util.List<javax.swing.tree.TreeNode>
     * @author 杨占锐
     * @date 2024/12/5 11:11
     */
    List<TreeNode> listDeptNodes(@Param("deptIds") List<String> deptIds);

}
