package com.topinfo.basic.platform.plugin.data.permission.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: DataPermissionConstant
 * @Description:
 * @Author 杨占锐
 * @Date 2024/12/3 19:53
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class DataPermissionConstant {

    /**
     * 主表别名
     */
    public static final String MAIN_TABLE_AS = "tb01";

    /**
     * 主表中deptId字段名称
     */
    public static final String DEPT_ID_FIELD_NAME = "dept_id";

    /**
     * 主表中deptLevlecode字段名称
     */
    public static final String DEPT_LEVELCODE_FIELD_NAME = "dept_levelcode";

    /**
     * 本部门
     */
    public static final String SYS4801 = "SYS4801";

    /**
     * 本部门及下级部门
     */
    public static final String SYS4802 = "SYS4802";

    /**
     * 指定部门
     */
    public static final String SYS4803 = "SYS4803";

    /**
     * 指定部门及下级部门
     */
    public static final String SYS4804 = "SYS4804";

    /**
     * 本部门 / 本部门及下级部门
     */
    public static final Set SELF_SCOPE_SET = new HashSet() {
        {
            // 本部门
            add(SYS4801);

            // 本部门及下级部门
            add(SYS4802);
        }
    };

    /**
     * 指定部门/ 指定部门及下级部门
     */
    public static final Set ASSIGN_SCOPE_SET = new HashSet() {
        {
            // 指定部门
            add(SYS4803);

            // 指定部门及下级部门
            add(SYS4804);
        }
    };
}
