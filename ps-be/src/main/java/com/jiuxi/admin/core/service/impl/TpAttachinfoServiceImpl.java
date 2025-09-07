package com.jiuxi.admin.core.service.impl;

import com.jiuxi.admin.core.bean.entity.TpAttachinfo;
import com.jiuxi.admin.core.bean.vo.TpAttachinfoRefVO;
import com.jiuxi.admin.core.bean.vo.TpAttachinfoVO;
import com.jiuxi.admin.core.service.TpAttachinfoService;
import com.jiuxi.domain.file.repo.TpAttachinfoRepository;
import com.jiuxi.app.file.assembler.FileAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 附件服务适配器实现类
 * 用于兼容旧的代码依赖，将调用转发到新的TpAttachinfoRepository
 */
@Service("tpAttachinfoService")
public class TpAttachinfoServiceImpl implements TpAttachinfoService {

    @Autowired
    private TpAttachinfoRepository tpAttachinfoRepository;

    @Override
    public int save(TpAttachinfo bean) {
        return tpAttachinfoRepository.save(FileAssembler.toDomainModel(bean));
    }

    @Override
    public TpAttachinfo view(String attachId) {
        com.jiuxi.domain.file.model.TpAttachinfo domainModel = tpAttachinfoRepository.view(attachId);
        return FileAssembler.toOldEntity(domainModel);
    }

    @Override
    public List<TpAttachinfoVO> selectByReferIdAndType(String referId, String referType) {
        List<com.jiuxi.domain.file.model.TpAttachinfo> domainModels = tpAttachinfoRepository.selectByReferIdAndType(referId, referType);
        return domainModels.stream()
                .map(domainModel -> {
                    com.jiuxi.app.file.dto.TpAttachinfoVO dto = FileAssembler.toVO(domainModel);
                    return FileAssembler.toOldVO(dto);
                })
                .collect(Collectors.toList());
    }

    @Override
    public int update(TpAttachinfo bean, String pid) {
        return tpAttachinfoRepository.update(FileAssembler.toDomainModel(bean), pid);
    }

    @Override
    public int remove(String referId, String attachId, String jwtpid) {
        return tpAttachinfoRepository.remove(referId, attachId, jwtpid);
    }

    @Override
    public int delete(String attachId) {
        return tpAttachinfoRepository.delete(attachId);
    }

    @Override
    public int deleteByReferId(TpAttachinfo bean, String pid) {
        return tpAttachinfoRepository.deleteByReferId(FileAssembler.toDomainModel(bean), pid);
    }

    @Override
    public int deleteByReferIdAndReferType(String referId, String referType, String pid) {
        return tpAttachinfoRepository.deleteByReferIdAndReferType(referId, referType, pid);
    }

    @Override
    public void listBand(List<TpAttachinfoRefVO> list, String referId, String pid) {
        // 这个方法在新的架构中没有对应的实现，暂时留空
    }

    @Override
    public void band(TpAttachinfoRefVO vo, String referId, String pid) {
        // 这个方法在新的架构中没有对应的实现，暂时留空
    }

    @Override
    public void modifyReferId(List<String> attachIds, String referId, String pid) {
        tpAttachinfoRepository.modifyReferId(attachIds, referId, pid);
    }

    @Override
    public void deleteByAttachId(String attachId, String pid) {
        tpAttachinfoRepository.deleteByAttachId(attachId, pid);
    }

    @Override
    public boolean exists(String attachId) {
        return tpAttachinfoRepository.exists(attachId);
    }
}