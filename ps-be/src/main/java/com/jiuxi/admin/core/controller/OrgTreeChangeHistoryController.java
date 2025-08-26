package com.jiuxi.admin.core.controller;

import com.jiuxi.admin.core.bean.OrgTreeChangeHistory;
import com.jiuxi.admin.core.service.OrgTreeChangeHistoryService;
import com.jiuxi.common.bean.JsonResponse;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织机构树变更历史记录控制器
 *
 * @author 系统生成
 * @since 1.0
 */
// @Api(tags = "组织机构树变更历史记录管理")
@RestController
@RequestMapping("/api/org-tree-change-history")
public class OrgTreeChangeHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(OrgTreeChangeHistoryController.class);

    @Autowired
    private OrgTreeChangeHistoryService changeHistoryService;

    /**
     * 分页查询变更记录
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 分页结果
     */
    // @ApiOperation("分页查询变更记录")
    @GetMapping("/page")
    public JsonResponse getChangeRecordsPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<OrgTreeChangeHistory> records = changeHistoryService.getByPage(pageNum, pageSize);
            Long total = changeHistoryService.countTotal();
            
            Map<String, Object> result = new HashMap<>();
            result.put("records", records);
            result.put("total", total);
            result.put("pageNum", pageNum);
            result.put("pageSize", pageSize);
            
            return JsonResponse.buildSuccess(result);
        } catch (Exception e) {
            logger.error("分页查询变更记录失败", e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据版本号查询记录
     *
     * @param version 版本号
     * @return 版本记录
     */
    // @ApiOperation("根据版本号查询记录")
    @GetMapping("/version/{version}")
    public JsonResponse getByVersion(@PathVariable Long version) {
        try {
            OrgTreeChangeHistory record = changeHistoryService.getByVersion(version);
            if (record != null) {
                return JsonResponse.buildSuccess(record);
            } else {
                return JsonResponse.buildFailure("未找到指定版本的记录");
            }
        } catch (Exception e) {
            logger.error("根据版本号查询记录失败: version={}", version, e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 获取最新版本记录
     *
     * @return 最新版本记录
     */
    // @ApiOperation("获取最新版本记录")
    @GetMapping("/latest")
    public JsonResponse getLatestVersion() {
        try {
            OrgTreeChangeHistory record = changeHistoryService.getLatestVersion();
            return JsonResponse.buildSuccess(record);
        } catch (Exception e) {
            logger.error("获取最新版本记录失败", e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据时间范围查询记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 记录列表
     */
    // @ApiOperation("根据时间范围查询记录")
    @GetMapping("/time-range")
    public JsonResponse getByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            List<OrgTreeChangeHistory> records = changeHistoryService.getByTimeRange(startTime, endTime);
            return JsonResponse.buildSuccess(records);
        } catch (Exception e) {
            logger.error("根据时间范围查询记录失败: startTime={}, endTime={}", startTime, endTime, e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据操作用户查询记录
     *
     * @param operatorId 操作用户ID
     * @return 记录列表
     */
    // @ApiOperation("根据操作用户查询记录")
    @GetMapping("/operator/{operatorId}")
    public JsonResponse getByOperatorId(@PathVariable Long operatorId) {
        try {
            List<OrgTreeChangeHistory> records = changeHistoryService.getByOperatorId(operatorId);
            return JsonResponse.buildSuccess(records);
        } catch (Exception e) {
            logger.error("根据操作用户查询记录失败: operatorId={}", operatorId, e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据操作类型查询记录
     *
     * @param operationType 操作类型
     * @return 记录列表
     */
    // @ApiOperation("根据操作类型查询记录")
    @GetMapping("/type/{operationType}")
    public JsonResponse getByOperationType(@PathVariable String operationType) {
        try {
            List<OrgTreeChangeHistory> records = changeHistoryService.getByOperationType(operationType);
            return JsonResponse.buildSuccess(records);
        } catch (Exception e) {
            logger.error("根据操作类型查询记录失败: operationType={}", operationType, e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 比较版本差异
     *
     * @param fromVersion 起始版本
     * @param toVersion   目标版本
     * @return 差异信息
     */
    // @ApiOperation("比较版本差异")
    @GetMapping("/compare")
    public JsonResponse compareVersions(
            @RequestParam Long fromVersion,
            @RequestParam Long toVersion) {
        try {
            Map<String, Object> result = changeHistoryService.compareVersions(fromVersion, toVersion);
            return JsonResponse.buildSuccess(result);
        } catch (Exception e) {
            logger.error("比较版本差异失败: fromVersion={}, toVersion={}", fromVersion, toVersion, e);
            return JsonResponse.buildFailure("比较失败: " + e.getMessage());
        }
    }

    /**
     * 获取操作统计信息
     *
     * @return 统计信息
     */
    // @ApiOperation("获取操作统计信息")
    @GetMapping("/statistics")
    public JsonResponse getOperationStatistics() {
        try {
            List<Map<String, Object>> statistics = changeHistoryService.getOperationStatistics();
            return JsonResponse.buildSuccess(statistics);
        } catch (Exception e) {
            logger.error("获取操作统计信息失败", e);
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }

    /**
     * 清理旧记录
     *
     * @param beforeTime 时间点
     * @return 清理结果
     */
    // @ApiOperation("清理旧记录")
    @DeleteMapping("/cleanup")
    public JsonResponse cleanupOldRecords(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beforeTime) {
        try {
            int deletedCount = changeHistoryService.cleanupBefore(beforeTime);
            Map<String, Object> result = new HashMap<>();
            result.put("deletedCount", deletedCount);
            return JsonResponse.buildSuccess(result);
        } catch (Exception e) {
            logger.error("清理旧记录失败: beforeTime={}", beforeTime, e);
            return JsonResponse.buildFailure("清理失败: " + e.getMessage());
        }
    }

    /**
     * 验证版本完整性
     *
     * @return 验证结果
     */
    // @ApiOperation("验证版本完整性")
    @GetMapping("/validate")
    public JsonResponse validateVersionIntegrity() {
        try {
            Map<String, Object> result = changeHistoryService.validateVersionIntegrity();
            return JsonResponse.buildSuccess(result);
        } catch (Exception e) {
            logger.error("验证版本完整性失败", e);
            return JsonResponse.buildFailure("验证失败: " + e.getMessage());
        }
    }

    /**
     * 记录变更（手动触发）
     *
     * @param request 变更请求
     * @return 记录结果
     */
    // @ApiOperation("记录变更")
    @PostMapping("/record")
    public JsonResponse recordChange(@RequestBody Map<String, Object> request) {
        try {
            String operationType = (String) request.get("operationType");
            Long operatorId = Long.valueOf(request.get("operatorId").toString());
            String beforeData = (String) request.get("beforeData");
            String afterData = (String) request.get("afterData");
            
            Long recordId = changeHistoryService.recordChange(operationType, operatorId, beforeData, afterData);
            Map<String, Object> result = new HashMap<>();
            result.put("recordId", recordId);
            return JsonResponse.buildSuccess(result);
        } catch (Exception e) {
            logger.error("记录变更失败", e);
            return JsonResponse.buildFailure("记录失败: " + e.getMessage());
        }
    }
}