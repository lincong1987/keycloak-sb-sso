package com.jiuxi.domain.file.repo;

import com.jiuxi.domain.file.model.TpAttachinfo;
import java.util.List;

/**
 * 附件仓储接口
 */
public interface TpAttachinfoRepository {

    int save(TpAttachinfo bean);

    TpAttachinfo view(String attachId);

    List<TpAttachinfo> selectByReferIdAndType(String referId, String referType);

    int update(TpAttachinfo bean, String pid);

    /**
     * 删除附件
     * <pre>
     * 根据 referId 和 attachId 删除附件：
     *    如果 referId 为空，则只能删除当前登录人自己创建的附件，物理删除
     *    如果 referId 不为空， 则根据 数据权限 判断是否能够删除，如果权限不对，则抛出异常 逻辑删除
     *</pre>
     *
     * @param attachId:
     * @param jwtpid:
     * @return int
     */
    int remove(String referId, String attachId, String jwtpid);

    /**
     * 物理删除
     *
     * @param attachId:
     * @return int
     */
    int delete(String attachId);

    int deleteByReferId(TpAttachinfo bean, String pid);

    /**
     * 根据 referId 删除附件
     * @param referId
     * @param referType
     * @param pid
     * @return int
     */
    int deleteByReferIdAndReferType(String referId, String referType, String pid);

    /**
     * 修改附件的rederId
     *
     * @param attachIds 多个附件id
     * @param referId   业务id
     * @param pid       操作人员id
     * @return void
     */
    void modifyReferId(List<String> attachIds, String referId, String pid);

    /**
     * 逻辑删除附件
     *
     * @param attachId 附件id
     * @param pid      操作人员id
     * @return void
     */
    void deleteByAttachId(String attachId, String pid);
    
    /**
     * 判断附件是否存在 （忽略actived字段）
     *
     * @param attachId
     * @return boolean
     */
    boolean exists(String attachId);
}