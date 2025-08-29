package com.jiuxi.admin.core.controller.pc;

import com.jiuxi.admin.core.bean.entity.TpMenuHistory;
import com.jiuxi.admin.core.service.TpMenuHistoryService;
import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.core.core.annotation.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: TpMenuHistoryController
 * @Description: 菜单修改历史记录控制器
 * @Author: AI Assistant
 * @Date: 2025-01-29
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@RestController
@RequestMapping("/sys/menu/history")
@Authorization
public class TpMenuHistoryController {

    @Autowired
    private TpMenuHistoryService tpMenuHistoryService;

    /**
     * 根据菜单ID查询历史记录
     *
     * @param menuId 菜单ID
     * @return 历史记录列表
     */
    @RequestMapping("/listByMenuId")
    public JsonResponse listByMenuId(@RequestParam String menuId) {
        List<TpMenuHistory> historyList = tpMenuHistoryService.getHistoryByMenuId(menuId);
        return JsonResponse.buildSuccess(historyList);
    }

    /**
     * 根据操作人ID查询历史记录
     *
     * @param operatorId 操作人ID
     * @return 历史记录列表
     */
    @RequestMapping("/listByOperatorId")
    public JsonResponse listByOperatorId(@RequestParam String operatorId) {
        List<TpMenuHistory> historyList = tpMenuHistoryService.getHistoryByOperatorId(operatorId);
        return JsonResponse.buildSuccess(historyList);
    }

    /**
     * 根据操作类型查询历史记录
     *
     * @param operationType 操作类型（ADD/UPDATE/DELETE）
     * @return 历史记录列表
     */
    @RequestMapping("/listByOperationType")
    public JsonResponse listByOperationType(@RequestParam String operationType) {
        List<TpMenuHistory> historyList = tpMenuHistoryService.getHistoryByOperationType(operationType);
        return JsonResponse.buildSuccess(historyList);
    }

    /**
     * 根据时间范围查询历史记录
     *
     * @param startTime 开始时间（yyyyMMddHHmmss）
     * @param endTime   结束时间（yyyyMMddHHmmss）
     * @return 历史记录列表
     */
    @RequestMapping("/listByTimeRange")
    public JsonResponse listByTimeRange(@RequestParam String startTime, @RequestParam String endTime) {
        List<TpMenuHistory> historyList = tpMenuHistoryService.getHistoryByTimeRange(startTime, endTime);
        return JsonResponse.buildSuccess(historyList);
    }

    /**
     * 查询所有历史记录
     *
     * @return 历史记录列表
     */
    @RequestMapping("/listAll")
    public JsonResponse listAll() {
        List<TpMenuHistory> historyList = tpMenuHistoryService.getAllHistory();
        return JsonResponse.buildSuccess(historyList);
    }
}