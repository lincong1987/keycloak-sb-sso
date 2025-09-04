package com.topinfo.basic.platform.plugin.data.permission.core.api.impl;

import com.topinfo.basic.platform.plugin.data.permission.constant.DataPermissionConstant;
import com.topinfo.basic.platform.plugin.data.permission.core.api.TpDataPermissionApiService;
import com.topinfo.basic.platform.plugin.data.permission.core.bean.dto.TpDataPermissionDTO;
import com.topinfo.basic.platform.plugin.data.permission.core.bean.vo.TpDataPermissionScopeVO;
import com.topinfo.basic.platform.plugin.data.permission.core.service.TpDataPermissionScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TpDataPermissionApiServiceImpl
 * @Description:
 * @Author 杨占锐
 * @Date 2024/12/3 20:25
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service
public class TpDataPermissionApiServiceImpl implements TpDataPermissionApiService {

    @Autowired
    private TpDataPermissionScopeService tpDataPermissionScopeService;

    /**
     * 查询数据权限
     *
     * @param personId   当前登录人id
     * @param deptId     当前登录人id
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 20:29
     */
    @Override
    public TpDataPermissionDTO getDataPermission(String personId, String deptId) {
        return getDataPermission(personId, deptId,
                DataPermissionConstant.MAIN_TABLE_AS,
                DataPermissionConstant.DEPT_ID_FIELD_NAME,
                DataPermissionConstant.DEPT_LEVELCODE_FIELD_NAME);
    }

    /**
     * 查询数据权限
     *
     * <pre>
     *     1、根据人员id和部门id查询数据权限范围
     *     2、如果范围是指定部门，则再查询指定部门id
     *     3、根据范围编码组装sql
     * </pre>
     * @param personId                当前登录人id
     * @param deptId                  当前登录人id
     * @param mainTableAs             主表别名
     * @param deptIdFieldName         主表中deptId字段名称
     * @param deptLevelcodeFieldName  主表中deptLevlecode字段名称
     * @return void
     * @author 杨占锐
     * @date 2024/12/3 20:29
     */
    @Override
    public TpDataPermissionDTO getDataPermission(String personId, String deptId, String mainTableAs, String deptIdFieldName, String deptLevelcodeFieldName) {

        // 定义返回的对象
        TpDataPermissionDTO dto = new TpDataPermissionDTO();

        // 查看数据权限详情
        TpDataPermissionScopeVO scopeVO = tpDataPermissionScopeService.view(personId, deptId);

        // 添加数据权限范围
        this.addDataScope(dto, scopeVO);

        // 添加数据权限部门id
        this.addDataDeptId(dto, deptId, scopeVO);

        // 添加部门层级编码
        this.addDetpLevelcode(dto);

        // 添加数据权限最终生成的sql
        this.addPermSql(dto, mainTableAs, deptIdFieldName, deptLevelcodeFieldName);

        return dto;
    }

    /**
     * 添加数据权限最终生成的sql
     * <per>
     *     sql 实例
     *     1. tb01.dept_id = 'xxxxxx'
     *     2. tb01.dept_levelcode like '101001%'
     *     3. tb01.dept_id in ('xxx', 'xxxx', 'xxxxx)
     * </per>
     * @param dto                     最终返回的数据权限对象
     * @param mainTableAs             主表别名
     * @param deptIdFieldName         主表中dept字段名称
     * @param deptLevelcodeFieldName  主表中deptLevlecode字段名称
     * @return void
     * @author 杨占锐
     * @date 2024/12/4 15:59
     */
    private void addPermSql(TpDataPermissionDTO dto, String mainTableAs, String deptIdFieldName, String deptLevelcodeFieldName) {
        String dataScope = dto.getDataScope();
        StringBuilder sql = new StringBuilder();
            sql.append(" ")
               .append(mainTableAs)
               .append(".");
        // 1. 等值查询
        if (DataPermissionConstant.SYS4801.equals(dataScope) || (DataPermissionConstant.SYS4803.equals(dataScope) && dto.getDeptList().size() == 1)) {
            sql.append(deptIdFieldName)
               .append(" = ")
               .append("'")
               .append(dto.getDeptId())
               .append("'")
            ;
        } else
        // 2. like查询
        if (DataPermissionConstant.SYS4802.equals(dataScope) || DataPermissionConstant.SYS4804.equals(dataScope)) {
            sql.append(deptLevelcodeFieldName)
               .append(" like ")
               .append("'")
               .append(dto.getDeptLevelcode())
               .append("%'")
            ;
        } else
        // 3. in查询
        if (DataPermissionConstant.SYS4803.equals(dataScope) && dto.getDeptList().size() > 1) {
            sql.append(deptIdFieldName)
                    .append(" in ")
                    .append("(");
            int index = 1;
            for (String deptId: dto.getDeptList()) {
                sql.append("'")
                   .append(deptId)
                   .append("'");
                if (dto.getDeptList().size() > index) {
                    sql.append(",");
                }
                index ++;
            }
            sql.append(")")
            ;
        }
        // 添加sql
        dto.setPermSql(sql.toString());
    }

    /**
     * 添加部门层级编码
     *
     * @param dto
     * @return void
     * @author 杨占锐
     * @date 2024/12/4 15:47
     */
    private void addDetpLevelcode(TpDataPermissionDTO dto) {
        String dataScope = dto.getDataScope();
        // 本部门及下级部门  / 指定部门及下级部门 时，添加层级编码
        if (DataPermissionConstant.SYS4802.equals(dataScope) || DataPermissionConstant.SYS4804.equals(dataScope)) {
            String deptLevelcode = tpDataPermissionScopeService.getDeptLevelcode(dto.getDeptId());
            dto.setDeptLevelcode(deptLevelcode);
        }
    }

    /**
     * 添加数据权限部门id
     *
     * @param dto
     * @param deptId
     * @param scopeVO
     * @return void
     * @author 杨占锐
     * @date 2024/12/4 15:40
     */
    private void addDataDeptId(TpDataPermissionDTO dto, String deptId, TpDataPermissionScopeVO scopeVO) {

        // 本部门 / 本部门及下级部门
        if (DataPermissionConstant.SELF_SCOPE_SET.contains(dto.getDataScope())) {
            dto.setDeptId(deptId);
        } else
        // 指定部门, 可能是一个或多个
        if (DataPermissionConstant.SYS4803.equals(dto.getDataScope())) {
            dto.setDeptList(scopeVO.getDeptList());
            // 只有一个部门时，添加部门id，便于等值查询
            if (scopeVO.getDeptList().size() == 1) {
                dto.setDeptId(scopeVO.getDeptList().get(0));
            }
        } else
        // 指定部门及下级部门
        if (DataPermissionConstant.SYS4804.equals(dto.getDataScope())) {
            dto.setDeptId(scopeVO.getDeptList().get(0));
        }

    }

    /**
     * 添加数据权限范围
     * <per>
     *     1. scopeVO 为空时，默认 本部门及下级部门
     *     2. scopeVO 不为空时，取scopeVO中的范围
     * </per>
     * @param dto
     * @param scopeVO
     * @return void
     * @author 杨占锐
     * @date 2024/12/4 15:34
     */
    private void addDataScope(TpDataPermissionDTO dto, TpDataPermissionScopeVO scopeVO) {
        if (null == scopeVO) {
            // 默认 本部门及下级部门
            dto.setDataScope(DataPermissionConstant.SYS4802);
        } else {
            dto.setDataScope(scopeVO.getDataScope());
        }
    }

}
