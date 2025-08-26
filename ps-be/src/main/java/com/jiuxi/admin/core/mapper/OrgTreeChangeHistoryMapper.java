package com.jiuxi.admin.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuxi.admin.core.bean.OrgTreeChangeHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 组织机构树变更历史记录表 Mapper 接口
 *
 * @author 系统生成
 * @since 1.0
 */
@Mapper
public interface OrgTreeChangeHistoryMapper extends BaseMapper<OrgTreeChangeHistory> {

    /**
     * 根据版本号查询记录
     *
     * @param version 版本号
     * @return 版本记录
     */
    @Select("SELECT * FROM org_tree_change_history WHERE version = #{version}")
    OrgTreeChangeHistory selectByVersion(@Param("version") Long version);

    /**
     * 根据ID查询记录
     *
     * @param id 记录ID
     * @return 记录详情
     */
    @Select("SELECT * FROM org_tree_change_history WHERE id = #{id}")
    OrgTreeChangeHistory selectById(@Param("id") Long id);

    /**
     * 根据时间范围查询记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history WHERE operation_time BETWEEN #{startTime} AND #{endTime} ORDER BY operation_time DESC")
    List<OrgTreeChangeHistory> selectByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                                 @Param("endTime") LocalDateTime endTime);

    /**
     * 根据操作用户ID查询记录
     *
     * @param operatorId 操作用户ID
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history WHERE operator_id = #{operatorId} ORDER BY operation_time DESC")
    List<OrgTreeChangeHistory> selectByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 根据操作类型查询记录
     *
     * @param operationType 操作类型
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history WHERE operation_type = #{operationType} ORDER BY operation_time DESC")
    List<OrgTreeChangeHistory> selectByOperationType(@Param("operationType") String operationType);

    /**
     * 获取最新版本记录
     *
     * @return 最新版本记录
     */
    @Select("SELECT * FROM org_tree_change_history ORDER BY version DESC LIMIT 1")
    OrgTreeChangeHistory selectLatestVersion();

    /**
     * 获取最新版本号
     *
     * @return 最新版本号
     */
    @Select("SELECT MAX(version) FROM org_tree_change_history")
    Long selectLatestVersionNumber();

    /**
     * 分页查询记录
     *
     * @param offset 偏移量
     * @param limit  限制数量
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history ORDER BY operation_time DESC LIMIT #{offset}, #{limit}")
    List<OrgTreeChangeHistory> selectByPage(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计总记录数
     *
     * @return 总记录数
     */
    @Select("SELECT COUNT(*) FROM org_tree_change_history")
    Long countTotal();

    /**
     * 获取操作统计信息
     *
     * @return 统计信息
     */
    @Select("SELECT operation_type, COUNT(*) as count FROM org_tree_change_history GROUP BY operation_type")
    List<Map<String, Object>> selectOperationStatistics();

    /**
     * 根据版本范围查询记录
     *
     * @param startVersion 开始版本
     * @param endVersion   结束版本
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history WHERE version BETWEEN #{startVersion} AND #{endVersion} ORDER BY version ASC")
    List<OrgTreeChangeHistory> selectByVersionRange(@Param("startVersion") Long startVersion, 
                                                    @Param("endVersion") Long endVersion);

    /**
     * 删除指定时间之前的记录
     *
     * @param beforeTime 时间点
     * @return 删除的记录数
     */
    @Select("DELETE FROM org_tree_change_history WHERE operation_time < #{beforeTime}")
    int deleteBeforeTime(@Param("beforeTime") LocalDateTime beforeTime);

    /**
     * 根据操作用户和时间范围查询记录
     *
     * @param operatorId 操作用户ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history WHERE operator_id = #{operatorId} AND operation_time BETWEEN #{startTime} AND #{endTime} ORDER BY operation_time DESC")
    List<OrgTreeChangeHistory> selectByOperatorAndTimeRange(@Param("operatorId") Long operatorId,
                                                            @Param("startTime") LocalDateTime startTime,
                                                            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据操作类型和时间范围查询记录
     *
     * @param operationType 操作类型
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 记录列表
     */
    @Select("SELECT * FROM org_tree_change_history WHERE operation_type = #{operationType} AND operation_time BETWEEN #{startTime} AND #{endTime} ORDER BY operation_time DESC")
    List<OrgTreeChangeHistory> selectByTypeAndTimeRange(@Param("operationType") String operationType,
                                                        @Param("startTime") LocalDateTime startTime,
                                                        @Param("endTime") LocalDateTime endTime);
}