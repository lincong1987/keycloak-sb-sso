package com.jiuxi.infra.file.repository;

import com.jiuxi.domain.file.model.TpAttachinfo;
import com.jiuxi.domain.file.repo.TpAttachinfoRepository;
import com.jiuxi.admin.core.mapper.TpAttachinfoMapper;
import com.jiuxi.app.file.assembler.FileAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 附件仓储实现类
 */
@Repository
public class TpAttachinfoRepositoryImpl implements TpAttachinfoRepository {

    @Autowired
    private TpAttachinfoMapper tpAttachinfoMapper;

    @Override
    public int save(TpAttachinfo bean) {
        com.jiuxi.admin.core.bean.entity.TpAttachinfo entity = FileAssembler.toOldEntity(bean);
        return tpAttachinfoMapper.save(entity);
    }

    @Override
    public TpAttachinfo view(String attachId) {
        com.jiuxi.admin.core.bean.entity.TpAttachinfo entity = tpAttachinfoMapper.view(attachId);
        return FileAssembler.toDomainModel(entity);
    }

    @Override
    public List<TpAttachinfo> selectByReferIdAndType(String referId, String referType) {
        List<com.jiuxi.admin.core.bean.vo.TpAttachinfoVO> vos = tpAttachinfoMapper.selectByReferIdAndType(referId, referType);
        return vos.stream()
                .map(FileAssembler::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int update(TpAttachinfo bean, String pid) {
        com.jiuxi.admin.core.bean.entity.TpAttachinfo entity = FileAssembler.toOldEntity(bean);
        return tpAttachinfoMapper.update(entity);
    }

    @Override
    public int remove(String referId, String attachId, String jwtpid) {
        int count = 0;
        if (referId == null || referId.isEmpty()) {
            // 如果 referId 为空，则只能删除当前登录人自己创建的附件，物理删除
            count = tpAttachinfoMapper.physicsDelete(attachId, jwtpid);
        } else {
            // 逻辑删除
            String updateTime = com.jiuxi.common.util.CommonDateUtil.now();
            count = tpAttachinfoMapper.logicDelete(attachId, jwtpid, updateTime);
        }
        return count;
    }

    @Override
    public int delete(String attachId) {
        return tpAttachinfoMapper.delete(attachId);
    }

    @Override
    public int deleteByReferId(TpAttachinfo bean, String pid) {
        com.jiuxi.admin.core.bean.entity.TpAttachinfo entity = FileAssembler.toOldEntity(bean);
        String updateTime = com.jiuxi.common.util.CommonDateUtil.now();
        return tpAttachinfoMapper.deleteByReferId(entity.getReferId(), pid, updateTime);
    }

    @Override
    public int deleteByReferIdAndReferType(String referId, String referType, String pid) {
        String updateTime = com.jiuxi.common.util.CommonDateUtil.now();
        return tpAttachinfoMapper.deleteByReferIdAndReferType(referId, referType, pid, updateTime);
    }

    @Override
    public void modifyReferId(List<String> attachIds, String referId, String pid) {
        String now = com.jiuxi.common.util.CommonDateUtil.now();
        for (String attachId : attachIds) {
            com.jiuxi.admin.core.bean.entity.TpAttachinfo bean = new com.jiuxi.admin.core.bean.entity.TpAttachinfo();
            bean.setAttachId(attachId);
            bean.setReferId(referId);
            bean.setUpdateTime(now);
            bean.setUpdator(pid);
            tpAttachinfoMapper.update(bean);
        }
    }

    @Override
    public void deleteByAttachId(String attachId, String pid) {
        String updateTime = com.jiuxi.common.util.CommonDateUtil.now();
        tpAttachinfoMapper.logicDelete(attachId, pid, updateTime);
    }

    @Override
    public boolean exists(String attachId) {
        String id = tpAttachinfoMapper.exists(attachId);
        return id != null && !id.isEmpty();
    }
}