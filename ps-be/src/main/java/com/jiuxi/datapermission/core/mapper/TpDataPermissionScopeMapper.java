package com.jiuxi.datapermission.core.mapper;

import com.jiuxi.datapermission.core.bean.vo.TpDataPermissionScopeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: TpDataPermissionScopeMapper
 * @Description: 数据权限范围表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Mapper
public interface TpDataPermissionScopeMapper {

    /**
     * 根据人员id和部门id，查询数据权限范围
     *
     * @param personId 人员id
     * @param deptId   部门id
     * @return com.jiuxi.datapermission.core.bean.vo.TpDataPermissionScopeVO
     * @author 杨占锐
     * @date 2024/12/3 19:20
     */
    TpDataPermissionScopeVO view(@Param("personId")String personId, @Param("deptId")String deptId);

    /**
     * 根据部门id，查询部门层级编码
     *
     * @param deptId
     * @return java.lang.String
     * @author 杨占锐
     * @date 2024/12/4 15:52
     */
    String getDeptLevelcode(@Param("deptId") String deptId);
}