package com.jiuxi.admin.core.controller.pc;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuxi.common.constant.TpConstant;
import com.jiuxi.admin.core.bean.query.TpPersonBasicQuery;
import com.jiuxi.admin.core.bean.vo.TpAccountVO;
import com.jiuxi.admin.core.bean.vo.TpPersonBasicinfoVO;
import com.jiuxi.admin.core.bean.vo.TpPersonExinfoVO;
import com.jiuxi.admin.core.bean.vo.TpPersonRoleVO;
import com.jiuxi.admin.core.service.TpAccountService;
import com.jiuxi.admin.core.service.TpCityService;
import com.jiuxi.admin.core.service.TpPersonBasicinfoService;
import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.core.core.annotation.Authorization;
import com.jiuxi.core.core.annotation.IgnoreAuthorization;
import com.jiuxi.core.core.validator.group.AddGroup;
import com.jiuxi.core.core.validator.group.UpdateGroup;
import com.jiuxi.security.core.entity.vo.PersonVO;
import com.jiuxi.security.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: TpPersonBasicinfoController
 * @Description: 人员基本信息表
 * @Author pand
 * @Date 2020-11-18 11:05:18
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@RestController
@RequestMapping("/sys/person")
@Authorization
public class TpPersonBasicinfoController {

    /**
     * 接口配置 passKey
     */
    private static final String PASS_KEY = "personId";

    @Autowired
    private TpPersonBasicinfoService tpPersonBasicinfoService;

    @Autowired
    private TpAccountService tpAccountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private TpCityService cityService;

    /**
     * 登录成功后,获取用户信息
     */
    @RequestMapping("/getUserInfo")
    @IgnoreAuthorization
    public JsonResponse getUserInfo(String jwtdid, String jwtpid, String jwtrids, String jwtCityCode) {

        PersonVO vo = personService.getUserInfo(jwtdid, jwtpid);
        vo.setRoleIds(jwtrids);
        vo.setCityCode(jwtCityCode);
        String cityName = cityService.selectCityNameByCityCode(jwtCityCode);
        vo.setCityName(cityName);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 政府人员列表
     */
    @RequestMapping(value = "/org/list")
    public JsonResponse orgList(TpPersonBasicQuery query, String jwtpid) {
        query.setCategory(TpConstant.Category.ORG);
        IPage<TpPersonBasicinfoVO> page = tpPersonBasicinfoService.queryPage(query);
        return JsonResponse.buildSuccess(page).buildPassKey(jwtpid, PASS_KEY);
    }

    /**
     * 企业人员列表
     */
    @RequestMapping(value = "/ent/list")
    public JsonResponse entList(TpPersonBasicQuery query, String jwtpid) {
        if (StrUtil.isBlank(query.getDeptId())) {
            return JsonResponse.buildFailure("请选择部门查询！");
        }
        query.setCategory(TpConstant.Category.ENT);
        IPage<TpPersonBasicinfoVO> page = tpPersonBasicinfoService.queryPage(query);
        return JsonResponse.buildSuccess(page).buildPassKey(jwtpid, PASS_KEY);
    }

    /**
     * 添加用户信息（政府）
     */
    @RequestMapping(value = "/org/add")
    public JsonResponse orgAdd(@Validated(value = AddGroup.class) @RequestBody TpPersonBasicinfoVO vo, String jwtpid) {
        vo = tpPersonBasicinfoService.add(vo, jwtpid, TpConstant.Category.ORG);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 添加用户信息（企业）
     */
    @RequestMapping(value = "/ent/add")
    public JsonResponse entAdd(@Validated(value = AddGroup.class) @RequestBody TpPersonBasicinfoVO vo, String jwtpid) {
        vo = tpPersonBasicinfoService.add(vo, jwtpid, TpConstant.Category.ENT);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 添加用户扩展信息
     */
    @RequestMapping(value = "/exp-add")
    public JsonResponse expAdd(@Validated(value = AddGroup.class) TpPersonExinfoVO vo) {
        vo = tpPersonBasicinfoService.expAdd(vo);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 账号新增和修改合并，如果传了账号id，进行修改操作，如果没传，进行新增操作。
     * <p>
     * <p>
     * 添加用户账号信息
     */
    @RequestMapping(value = "/account-manage")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse accountManage(@Validated(value = AddGroup.class) TpAccountVO vo) {
        int count = tpAccountService.accountManage(vo);

        return JsonResponse.buildSuccess(count);
    }

    /**
     * 查看用户信息
     */
    @RequestMapping(value = "/view")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse view(String personId, String deptId) {
        TpPersonBasicinfoVO vo = tpPersonBasicinfoService.view(personId, deptId);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 查看用户扩展信息
     */
    @RequestMapping(value = "/exp-view")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse expView(String personId) {
        TpPersonExinfoVO vo = tpPersonBasicinfoService.expView(personId);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 查看用户账号信息
     */
    @RequestMapping(value = "/account-view")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse accountView(String personId) {
        TpAccountVO vo = tpAccountService.accountView(personId);

        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 修改用户基本信息
     */
    @RequestMapping(value = "/update")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse update(@Validated(value = UpdateGroup.class) @RequestBody TpPersonBasicinfoVO vo, String jwtpid) {
        int count = tpPersonBasicinfoService.update(vo, jwtpid);

        return JsonResponse.buildSuccess(count);
    }

    /**
     * 当前登录用户修改密码
     */
    @RequestMapping(value = "/update-pwd")
    @IgnoreAuthorization
    public JsonResponse updatePwd(String oldUserpwd, String userpwd, String jwtpid) {
        int count = tpAccountService.updatePwd(jwtpid, oldUserpwd, userpwd);

        return JsonResponse.buildSuccess(count);
    }

    /**
     * 重置密码
     */
    @RequestMapping("/account-resetpwd")
    public JsonResponse accountResetpwd(String accountId) {
        String restPwd = tpAccountService.accountResetpwd(accountId);

        return JsonResponse.buildSuccess(restPwd);
    }

    /**
     * 找回密码,发送验证码
     */
    @RequestMapping("/account-findpwd")
    @IgnoreAuthorization
    public JsonResponse accountFindpwd(@RequestParam(value = "phone") String phone) {
        String userName = tpAccountService.accountFindpwd(phone);

        return JsonResponse.buildSuccess(userName);
    }

    /**
     * 找回密码,校验验证码并修改密码
     */
    @RequestMapping("/check-vcode")
    @IgnoreAuthorization
    public JsonResponse accountCheckVcode(String phone, String vcode, String userpwd) {
        int count = tpAccountService.accountCheckVcode(phone, vcode, userpwd);
        if (count == 1) {
            return JsonResponse.buildSuccess("密码修改成功！");
        } else {
            return JsonResponse.buildSuccess("密码修改失败！");
        }
    }

    /**
     * 账号 冻结/解冻
     */
    @RequestMapping("/account-locked")
    public JsonResponse accountLocked(String accountId, Integer locked) {
        int count = tpAccountService.accountLocked(accountId, locked);

        return JsonResponse.buildSuccess(count);
    }

    /**
     * 账号 启动/禁用
     */
    @RequestMapping("/account-enabled")
    public JsonResponse accountEnabled(String accountId, Integer enabled) {
        int count = tpAccountService.accountEnabled(accountId, enabled);

        return JsonResponse.buildSuccess(count);
    }

    /**
     * 批量删除用户信息
     */
    @RequestMapping(value = "/delete")
    public JsonResponse deletes(String deptIds, String personIds, String jwtpid) {
        tpPersonBasicinfoService.deletes(deptIds, personIds, jwtpid);

        return JsonResponse.buildSuccess();
    }

    /**
     * 用户增加兼职部门
     */
    @RequestMapping(value = "/parttime-add")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse parttimeAdd(String personId, String deptIds) {
        tpPersonBasicinfoService.parttime(personId, deptIds);

        return JsonResponse.buildSuccess();
    }

    /**
     * 查询用户拥有的角色
     */
    @RequestMapping(value = "/person-roles")
    @IgnoreAuthorization
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse personRoles(String deptId, String personId) {
        List<TpPersonRoleVO> list = tpPersonBasicinfoService.personRoles(deptId, personId);

        return JsonResponse.buildSuccess(list);
    }

    /**
     * 用户授权，增加角色
     */
    @RequestMapping(value = "/auth-add")
    @Authorization(businessKey = PASS_KEY)
    public JsonResponse authAdd(String personId, String deptId, String roleIds) {
        tpPersonBasicinfoService.auth(personId, deptId, roleIds);

        return JsonResponse.buildSuccess();
    }

    /**
     * 导出用户信息到Excel
     */
    @RequestMapping(value = "/export-excel")
    public void exportExcel(@RequestBody TpPersonBasicQuery query, String jwtpid, HttpServletResponse response) {
        try {
            tpPersonBasicinfoService.exportExcel(query, jwtpid, response);
        } catch (Exception e) {
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导入用户信息从Excel
     */
    @RequestMapping(value = "/import-excel")
    public JsonResponse importExcel(@RequestParam("file") MultipartFile file, String jwtpid) {
        try {
            return tpPersonBasicinfoService.importExcel(file, jwtpid);
        } catch (Exception e) {
            throw new RuntimeException("导入Excel失败", e);
        }
    }

    /**
     * 下载Excel导入模板
     */
    @RequestMapping(value = "/download-template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            tpPersonBasicinfoService.downloadTemplate(response);
        } catch (Exception e) {
            throw new RuntimeException("下载模板失败", e);
        }
    }

}
