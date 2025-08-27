package com.topinfo.basic.platform.log.core.controller;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.topinfo.basic.platform.common.bean.JsonResponse;
import com.topinfo.basic.platform.common.util.JwtTokenUtils;
import com.topinfo.basic.platform.core.core.annotation.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;


import com.topinfo.basic.platform.log.core.bean.vo.TpOperateLogVO;
import com.topinfo.basic.platform.log.core.bean.query.TpOperateLogQuery;
import com.topinfo.basic.platform.log.core.service.TpOperateLogService;


/**
 * @ClassName: TpOperateLogController
 * @Description: 操作日志表
 * @Author 杨攀
 * @Date 2022-09-21 14:00:19
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@RestController
@RequestMapping("/platform/logger")
public class TpOperateLogController {

    @Autowired
    private TpOperateLogService tpOperateLogService;

    /**
     * 列表
     *
     * @Author 杨攀
     * @Date 2022-09-21 14:00:19
     */
    @RequestMapping("/list")
    public JsonResponse list(TpOperateLogQuery query, HttpServletRequest request) {

        String token = request.getHeader("Token");

        // 判断token 是否为空
        if(StrUtil.isBlank(token)){
            return JsonResponse.buildFailure("token不能为空！");
        }

        // 校验token是否有效
        JwtTokenUtils.checkToken(token);

        IPage<TpOperateLogVO> page = tpOperateLogService.queryPage(query);
        return JsonResponse.buildSuccess(page);
    }


    /**
     * 登录日志采集
     * @author 杨攀
     * @date 2022/9/21 15:42
     * @param ticket 滑动验证票据
     * @param username 用户名
     * @param category 用户类别
     * @param jt token
     * @return void
     */
    @RequestMapping("/login")
    public JsonResponse login(String ticket, String username, String category, String jt, HttpServletRequest request) {

        // 判断token 是否为空
        if(StrUtil.isBlank(jt)){
            return JsonResponse.buildFailure("token不能为空！");
        }

        // 判断滑动验证票据不能为空！
        if(StrUtil.isBlank(ticket)){
            return JsonResponse.buildFailure("滑动验证票据不能为空！");
        }

        // 校验token是否有效
        JwtTokenUtils.checkToken(jt);

        // 保存访问客户端ip
        String ip = request.getHeader("X-Forwarded-For");
        if(StrUtil.isBlank(ip)){
            ip = request.getRemoteAddr();
        }

        // 保存浏览器类型
        String userAgent = request.getHeader("User-Agent");

        // 简单判断，嘎嘎嘎，偷个懒
        String extend01 = request.getParameter("extend01");
        extend01 = Optional.ofNullable(extend01).orElse("");
        extend01 = URLDecoder.decode(extend01, Charset.forName("UTF-8"));

        String extend02 = request.getParameter("extend02");
        extend02 = Optional.ofNullable(extend02).orElse("");
        extend02 = URLDecoder.decode(extend02, Charset.forName("UTF-8"));

        String extend03 = request.getParameter("extend03");
        extend03 = Optional.ofNullable(extend03).orElse("");
        extend03 = URLDecoder.decode(extend03, Charset.forName("UTF-8"));

        // 登录日志采集
        tpOperateLogService.login(ticket, username, category, jt, ip, userAgent, extend01, extend02, extend03);

        return JsonResponse.buildSuccess();
    }


    /**
     * 操作日志采集
     * @author 杨攀
     * @date 2022/9/21 17:23
     * @param moduleCode 模块code
     * @param operterType 操作类型
     * @param operterRid 操作记录ID、修改、删除时，记录ID
     * @param username 账号
     * @param category 人员类别
     * @param jt token
     * @param request
     * @return void
     */
    @RequestMapping("/collection")
    public JsonResponse collection(String moduleCode, String operterType, String operterRid, String username, String category, String jt, HttpServletRequest request) {

        // 判断token 是否为空
        if(StrUtil.isBlank(jt)){
            return JsonResponse.buildFailure("token不能为空！");
        }

        // 校验token是否有效
        JwtTokenUtils.checkToken(jt);

        // 保存访问客户端ip
        String ip = request.getHeader("X-Forwarded-For");
        if(StrUtil.isBlank(ip)){
            ip = request.getRemoteAddr();
        }

        // 保存浏览器类型
        String userAgent = request.getHeader("User-Agent");

        // 简单判断，嘎嘎嘎，偷个懒
        String extend01 = request.getParameter("extend01");
        extend01 = Optional.ofNullable(extend01).orElse("");
        extend01 = URLDecoder.decode(extend01, Charset.forName("UTF-8"));

        String extend02 = request.getParameter("extend02");
        extend02 = Optional.ofNullable(extend02).orElse("");
        extend02 = URLDecoder.decode(extend02, Charset.forName("UTF-8"));

        String extend03 = request.getParameter("extend03");
        extend03 = Optional.ofNullable(extend03).orElse("");
        extend03 = URLDecoder.decode(extend03, Charset.forName("UTF-8"));

        // 操作日志采集
        tpOperateLogService.collection(moduleCode, operterType, operterRid, username, category, jt, ip, userAgent, extend01, extend02, extend03);

        return JsonResponse.buildSuccess();
    }


    /**
     * 信息
     * @author 杨攀
     * @date 2022/9/21 15:41
     * @param logId
     * @return com.topinfo.basic.platform.common.bean.JsonResponse
     */
    @RequestMapping("/view")
    public JsonResponse view(String logId) {
        TpOperateLogVO vo = tpOperateLogService.view(logId);
        return JsonResponse.buildSuccess(vo);
    }
    
    /**
     * 保存
     *
     * @Author 杨攀
     * @Date 2022-09-21 14:00:19
     */
    @RequestMapping("/add")
    public JsonResponse add(TpOperateLogVO tpOperateLog, String jwtpid) {
        String logId = tpOperateLogService.add(tpOperateLog, jwtpid);
        return JsonResponse.buildSuccess(logId);
    }


}
