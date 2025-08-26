package com.jiuxi.admin.core.service;

import com.jiuxi.admin.core.bean.OrgTreeChangeHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 组织机构树变更历史记录服务接口
 *
 * @author 系统生成
 * @since 1.0
 */
public interface OrgTreeChangeHistoryService {

    /**
     * 记录组织机构树变更
     *
     * @param operationType 操作类型
     * @param operatorId    操作用户ID
     * @param beforeData    变更前数据
     * @param afterData     变更后数据
     * @return 记录ID
     */
    Long recordChange(String operationType, Long operatorId, String beforeData, String afterData);

    /**
     * 根据版本号查询记录
     *
     * @param version 版本号
     * @return 版本记录
     */
    OrgTreeChangeHistory getByVersion(Long version);

    /**
     * 根据ID查询记录
     *
     * @param id 记录ID
     * @return 记录详情
     */
    OrgTreeChangeHistory getById(String id);

    /**
     * 获取最新版本记录
     *
     * @return 最新版本记录
     */
    OrgTreeChangeHistory getLatestVersion();

    /**
     * 获取最新版本号
     *
     * @return 最新版本号
     */
    Long getLatestVersionNumber();

    /**
     * 根据时间范围查询记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据操作用户ID查询记录
     *
     * @param operatorId 操作用户ID
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByOperatorId(Long operatorId);

    /**
     * 根据操作类型查询记录
     *
     * @param operationType 操作类型
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByOperationType(String operationType);

    /**
     * 分页查询记录
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByPage(int pageNum, int pageSize);

    /**
     * 统计总记录数
     *
     * @return 总记录数
     */
    Long countTotal();

    /**
     * 获取操作统计信息
     *
     * @return 统计信息
     */
    List<Map<String, Object>> getOperationStatistics();

    /**
     * 根据版本范围查询记录
     *
     * @param startVersion 开始版本
     * @param endVersion   结束版本
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByVersionRange(Long startVersion, Long endVersion);

    /**
     * 比较两个版本的差异
     *
     * @param fromVersion 起始版本
     * @param toVersion   目标版本
     * @return 差异信息
     */
    Map<String, Object> compareVersions(Long fromVersion, Long toVersion);

    /**
     * 清理指定时间之前的记录
     *
     * @param beforeTime 时间点
     * @return 删除的记录数
     */
    int cleanupBefore(LocalDateTime beforeTime);

    /**
     * 根据操作用户和时间范围查询记录
     *
     * @param operatorId 操作用户ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByOperatorAndTimeRange(Long operatorId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据操作类型和时间范围查询记录
     *
     * @param operationType 操作类型
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 记录列表
     */
    List<OrgTreeChangeHistory> getByTypeAndTimeRange(String operationType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 验证版本完整性
     *
     * @return 验证结果
     */
    Map<String, Object> validateVersionIntegrity();
}