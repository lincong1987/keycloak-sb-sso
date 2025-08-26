package com.jiuxi.admin.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuxi.admin.core.bean.OrgTreeChangeHistory;
import com.jiuxi.admin.core.mapper.OrgTreeChangeHistoryMapper;
import com.jiuxi.admin.core.service.OrgTreeChangeHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 组织机构树变更历史记录服务实现类
 *
 * @author 系统生成
 * @since 1.0
 */
@Service
@Transactional
public class OrgTreeChangeHistoryServiceImpl implements OrgTreeChangeHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(OrgTreeChangeHistoryServiceImpl.class);

    @Autowired
    private OrgTreeChangeHistoryMapper changeHistoryMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Long recordChange(String operationType, Long operatorId, String beforeData, String afterData) {
        try {
            // 创建变更记录
            OrgTreeChangeHistory changeHistory = new OrgTreeChangeHistory();
            changeHistory.setOperationType(operationType);
            changeHistory.setOperationTime(LocalDateTime.now());
            changeHistory.setOperatorId(operatorId);
            changeHistory.setBeforeData(beforeData);
            changeHistory.setAfterData(afterData);

            // 保存记录
            changeHistoryMapper.insert(changeHistory);
            
            logger.info("记录组织机构树变更成功: 操作类型={}, 版本号={}, 操作用户={}", 
                       operationType, changeHistory.getVersion(), operatorId);
            
            return changeHistory.getId();
        } catch (Exception e) {
            logger.error("记录组织机构树变更失败: 操作类型={}, 操作用户={}", operationType, operatorId, e);
            throw new RuntimeException("记录变更失败", e);
        }
    }

    @Override
    public OrgTreeChangeHistory getByVersion(Long version) {
        return changeHistoryMapper.selectByVersion(version);
    }

    @Override
    public OrgTreeChangeHistory getById(String id) {
        return changeHistoryMapper.selectById(Long.valueOf(id));
    }

    @Override
    public OrgTreeChangeHistory getLatestVersion() {
        return changeHistoryMapper.selectLatestVersion();
    }

    @Override
    public Long getLatestVersionNumber() {
        Long latestVersion = changeHistoryMapper.selectLatestVersionNumber();
        return latestVersion != null ? latestVersion : 0L;
    }

    @Override
    public List<OrgTreeChangeHistory> getByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return changeHistoryMapper.selectByTimeRange(startTime, endTime);
    }

    @Override
    public List<OrgTreeChangeHistory> getByOperatorId(Long operatorId) {
        return changeHistoryMapper.selectByOperatorId(operatorId);
    }

    @Override
    public List<OrgTreeChangeHistory> getByOperationType(String operationType) {
        return changeHistoryMapper.selectByOperationType(operationType);
    }

    @Override
    public List<OrgTreeChangeHistory> getByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return changeHistoryMapper.selectByPage(offset, pageSize);
    }

    @Override
    public Long countTotal() {
        return changeHistoryMapper.countTotal();
    }

    @Override
    public List<Map<String, Object>> getOperationStatistics() {
        return changeHistoryMapper.selectOperationStatistics();
    }

    @Override
    public List<OrgTreeChangeHistory> getByVersionRange(Long startVersion, Long endVersion) {
        return changeHistoryMapper.selectByVersionRange(startVersion, endVersion);
    }

    @Override
    public Map<String, Object> compareVersions(Long fromVersion, Long toVersion) {
        try {
            OrgTreeChangeHistory fromRecord = changeHistoryMapper.selectByVersion(fromVersion);
            OrgTreeChangeHistory toRecord = changeHistoryMapper.selectByVersion(toVersion);
            
            Map<String, Object> result = new HashMap<>();
            result.put("fromVersion", fromVersion);
            result.put("toVersion", toVersion);
            result.put("fromRecord", fromRecord);
            result.put("toRecord", toRecord);
            
            if (fromRecord != null && toRecord != null) {
                // 比较JSON数据差异
                Map<String, Object> differences = compareJsonData(
                    fromRecord.getAfterData(), 
                    toRecord.getAfterData()
                );
                result.put("differences", differences);
            }
            
            return result;
        } catch (Exception e) {
            logger.error("比较版本差异失败: fromVersion={}, toVersion={}", fromVersion, toVersion, e);
            throw new RuntimeException("比较版本失败", e);
        }
    }

    @Override
    public int cleanupBefore(LocalDateTime beforeTime) {
        try {
            int deletedCount = changeHistoryMapper.deleteBeforeTime(beforeTime);
            logger.info("清理历史记录完成，删除了 {} 条记录", deletedCount);
            return deletedCount;
        } catch (Exception e) {
            logger.error("清理历史记录失败", e);
            throw new RuntimeException("清理记录失败", e);
        }
    }

    @Override
    public List<OrgTreeChangeHistory> getByOperatorAndTimeRange(Long operatorId, LocalDateTime startTime, LocalDateTime endTime) {
        return changeHistoryMapper.selectByOperatorAndTimeRange(operatorId, startTime, endTime);
    }

    @Override
    public List<OrgTreeChangeHistory> getByTypeAndTimeRange(String operationType, LocalDateTime startTime, LocalDateTime endTime) {
        return changeHistoryMapper.selectByTypeAndTimeRange(operationType, startTime, endTime);
    }

    @Override
    public Map<String, Object> validateVersionIntegrity() {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 检查版本号连续性
            Long latestVersion = getLatestVersionNumber();
            Long totalCount = countTotal();
            
            result.put("latestVersion", latestVersion);
            result.put("totalCount", totalCount);
            result.put("isIntegrityValid", Objects.equals(latestVersion, totalCount));
            
            // 检查是否有重复版本号
            // 这里可以添加更多的完整性检查逻辑
            
            return result;
        } catch (Exception e) {
            logger.error("验证版本完整性失败", e);
            throw new RuntimeException("验证完整性失败", e);
        }
    }

    /**
     * 比较两个JSON数据的差异
     *
     * @param fromJson 源JSON
     * @param toJson   目标JSON
     * @return 差异信息
     */
    private Map<String, Object> compareJsonData(String fromJson, String toJson) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (!StringUtils.hasText(fromJson) && !StringUtils.hasText(toJson)) {
                result.put("hasChanges", false);
                return result;
            }
            
            if (!StringUtils.hasText(fromJson) || !StringUtils.hasText(toJson)) {
                result.put("hasChanges", true);
                result.put("changeType", !StringUtils.hasText(fromJson) ? "CREATED" : "DELETED");
                return result;
            }
            
            JsonNode fromNode = objectMapper.readTree(fromJson);
            JsonNode toNode = objectMapper.readTree(toJson);
            
            boolean hasChanges = !fromNode.equals(toNode);
            result.put("hasChanges", hasChanges);
            
            if (hasChanges) {
                result.put("changeType", "MODIFIED");
                // 这里可以添加更详细的差异分析逻辑
            }
            
        } catch (JsonProcessingException e) {
            logger.warn("解析JSON数据失败，无法比较差异", e);
            result.put("hasChanges", true);
            result.put("changeType", "UNKNOWN");
            result.put("error", "JSON解析失败");
        }
        
        return result;
    }
}