package com.jiuxi.admin.core.controller.pc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuxi.common.bean.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiuxi.admin.core.bean.vo.TpTraceVO;
import com.jiuxi.admin.core.bean.entity.TpTrace;
import com.jiuxi.admin.core.bean.query.TpTraceQuery;
import com.jiuxi.admin.core.service.TpTraceService;
import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * @ClassName: TpTraceController
 * @Description: 修改痕迹表
 * @Author yangp
 * @Date 2021-02-26 15:48:55
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@RestController
@RequestMapping("/core/tptrace")
public class TpTraceController {

    @Autowired
    private TpTraceService tpTraceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public JsonResponse list(TpTraceQuery query) {
        IPage<TpTraceVO> page = tpTraceService.queryPage(query);
        return JsonResponse.buildSuccess(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/view")
    public JsonResponse view(String id){
        TpTraceVO vo = tpTraceService.view(id);
        return JsonResponse.buildSuccess(vo);
    }

    /**
     * 保存
     */
    @RequestMapping("/add")
    public JsonResponse add(@RequestBody TpTrace tpTrace){
        // 获取当前用户ID（这里需要从请求上下文中获取，暂时用占位符）
        String jwtpid = "CURRENT_USER_ID"; // TODO: 需要从请求上下文中获取真实用户ID
        TpTraceVO vo = new TpTraceVO();
        BeanUtil.copyProperties(tpTrace, vo);
        String id = tpTraceService.add(vo, jwtpid);
        return JsonResponse.buildSuccess("保存成功", id);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public JsonResponse update(@RequestBody TpTrace tpTrace){
        // 获取当前用户ID（这里需要从请求上下文中获取，暂时用占位符）
        String jwtpid = "CURRENT_USER_ID"; // TODO: 需要从请求上下文中获取真实用户ID
        TpTraceVO vo = new TpTraceVO();
        BeanUtil.copyProperties(tpTrace, vo);
        int count = tpTraceService.update(vo, jwtpid);
        return JsonResponse.buildSuccess("修改成功，影响记录数：" + count);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public JsonResponse delete(@RequestBody String[] ids){
        ArrayList<String> idList = new ArrayList<>(Arrays.asList(ids));
        int count = tpTraceService.deleteByIds(idList);
        return JsonResponse.buildSuccess("删除成功，影响记录数：" + count);
    }

}