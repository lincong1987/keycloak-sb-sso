package com.jiuxi.admin.core.controller.pc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiuxi.admin.core.bean.OrgTreeChangeHistory;
import com.jiuxi.admin.core.service.OrgTreeChangeHistoryService;
import com.jiuxi.common.bean.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 组织机构变更历史控制器
 * @author AI Assistant
 * @date 2024-01-26
 */
@RestController
@RequestMapping("/api/org-tree-change-history")
public class OrgTreeChangeHistoryController {

    @Autowired
    private OrgTreeChangeHistoryService orgTreeChangeHistoryService;

    /**
     * 分页查询历史记录
     */
    @PostMapping("/list")
    public JsonResponse list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        
        try {
            // 使用现有的分页方法
            java.util.List<OrgTreeChangeHistory> records = orgTreeChangeHistoryService.getByPage(current, size);
            Long total = orgTreeChangeHistoryService.countTotal();
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", records);
            data.put("total", total);
            data.put("current", current);
            data.put("size", size);
            
            return JsonResponse.buildSuccess(data);
        } catch (Exception e) {
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }



    /**
     * 获取最新版本
     */
    @GetMapping("/latest")
    public JsonResponse getLatest() {
        try {
            OrgTreeChangeHistory latest = orgTreeChangeHistoryService.getLatestVersion();
            return JsonResponse.buildSuccess(latest);
        } catch (Exception e) {
            return JsonResponse.buildFailure("获取最新版本失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询详情
     */
    @GetMapping("/{id}")
    public JsonResponse getById(@PathVariable String id) {
        try {
            OrgTreeChangeHistory history = orgTreeChangeHistoryService.getById(id);
            if (history != null) {
                return JsonResponse.buildSuccess(history);
            } else {
                return JsonResponse.buildFailure("记录不存在");
            }
        } catch (Exception e) {
            return JsonResponse.buildFailure("查询失败: " + e.getMessage());
        }
    }
}