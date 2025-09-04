package com.jiuxi.admin.core.controller.pc;

import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.core.core.annotation.Authorization;
import com.jiuxi.datapermission.core.bean.vo.TpDataPermissionScopeVO;
import com.jiuxi.datapermission.core.service.TpDataPermissionScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TpDataPermissionScopeController
 * @Description: 数据权限范围表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@RestController
@RequestMapping("/sys/data-permission-scope")
@Authorization
public class TpDataPermissionScopeController {
    /**
     * 接口配置 passKey
     */
    private static final String PASS_KEY = "personId";

    @Autowired
    private TpDataPermissionScopeService tpDataPermissionScopeService;

    /**
     * 查看
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @RequestMapping("/view")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse view(String personId, String deptId) {
        TpDataPermissionScopeVO vo = tpDataPermissionScopeService.viewWithDeptName(personId, deptId);
        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 保存
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @RequestMapping("/add")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse add(TpDataPermissionScopeVO tpDataPermissionScope, String jwtpid, String jwttid) {
        String permId = tpDataPermissionScopeService.add(tpDataPermissionScope, jwtpid, jwttid);
        return JsonResponse.buildSuccess(permId);
    }
}