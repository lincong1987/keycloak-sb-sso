package com.jiuxi.app.file.assembler;

import com.jiuxi.app.file.dto.TpAttachinfoVO;
import com.jiuxi.domain.file.model.TpAttachinfo;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件装配器
 */
public class FileAssembler {

    /**
     * 将TpAttachinfo实体转换为TpAttachinfoVO
     *
     * @param entity
     * @return TpAttachinfoVO
     */
    public static TpAttachinfoVO toVO(TpAttachinfo entity) {
        if (entity == null) {
            return null;
        }

        TpAttachinfoVO vo = new TpAttachinfoVO();
        vo.setAttachId(entity.getAttachId());
        vo.setAttachTitle(entity.getAttachTitle());
        vo.setAttachName(entity.getAttachName());
        vo.setReferType(entity.getReferType());
        vo.setReferId(entity.getReferId());
        vo.setAttachTypeCode(entity.getAttachTypeCode());
        vo.setSavePath(entity.getSavePath());
        vo.setSavePathBak1(entity.getSavePathBak1());
        vo.setSavePathBak2(entity.getSavePathBak2());
        vo.setAttachSize(entity.getAttachSize());
        vo.setOrderIndex(entity.getOrderIndex());
        vo.setExtend01(entity.getExtend01());
        vo.setExtend02(entity.getExtend02());
        vo.setExtend03(entity.getExtend03());
        vo.setActived(entity.getActived());
        vo.setCreator(entity.getCreator());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdator(entity.getUpdator());
        vo.setUpdateTime(entity.getUpdateTime());

        return vo;
    }

    /**
     * 将TpAttachinfoVO转换为TpAttachinfo实体
     *
     * @param vo
     * @return TpAttachinfo
     */
    public static TpAttachinfo toEntity(TpAttachinfoVO vo) {
        if (vo == null) {
            return null;
        }

        TpAttachinfo entity = new TpAttachinfo();
        entity.setAttachId(vo.getAttachId());
        entity.setAttachTitle(vo.getAttachTitle());
        entity.setAttachName(vo.getAttachName());
        entity.setReferType(vo.getReferType());
        entity.setReferId(vo.getReferId());
        entity.setAttachTypeCode(vo.getAttachTypeCode());
        entity.setSavePath(vo.getSavePath());
        entity.setSavePathBak1(vo.getSavePathBak1());
        entity.setSavePathBak2(vo.getSavePathBak2());
        entity.setAttachSize(vo.getAttachSize());
        entity.setOrderIndex(vo.getOrderIndex());
        entity.setExtend01(vo.getExtend01());
        entity.setExtend02(vo.getExtend02());
        entity.setExtend03(vo.getExtend03());
        entity.setActived(vo.getActived());
        entity.setCreator(vo.getCreator());
        entity.setCreateTime(vo.getCreateTime());
        entity.setUpdator(vo.getUpdator());
        entity.setUpdateTime(vo.getUpdateTime());

        return entity;
    }

    /**
     * 将TpAttachinfo实体列表转换为TpAttachinfoVO列表
     *
     * @param entities
     * @return List<TpAttachinfoVO>
     */
    public static List<TpAttachinfoVO> toVOList(List<TpAttachinfo> entities) {
        if (entities == null) {
            return null;
        }

        List<TpAttachinfoVO> vos = new ArrayList<>();
        for (TpAttachinfo entity : entities) {
            vos.add(toVO(entity));
        }

        return vos;
    }

    /**
     * 将TpAttachinfoVO列表转换为TpAttachinfo实体列表
     *
     * @param vos
     * @return List<TpAttachinfo>
     */
    public static List<TpAttachinfo> toEntityList(List<TpAttachinfoVO> vos) {
        if (vos == null) {
            return null;
        }

        List<TpAttachinfo> entities = new ArrayList<>();
        for (TpAttachinfoVO vo : vos) {
            entities.add(toEntity(vo));
        }

        return entities;
    }

    /**
     * 将新的VO转换为旧的实体类
     *
     * @param newVO
     * @return com.jiuxi.admin.core.bean.entity.TpAttachinfo
     */
    public static com.jiuxi.admin.core.bean.entity.TpAttachinfo toOldEntity(TpAttachinfoVO newVO) {
        if (newVO == null) {
            return null;
        }

        com.jiuxi.admin.core.bean.entity.TpAttachinfo entity = new com.jiuxi.admin.core.bean.entity.TpAttachinfo();
        entity.setAttachId(newVO.getAttachId());
        entity.setAttachTitle(newVO.getAttachTitle());
        entity.setAttachName(newVO.getAttachName());
        entity.setReferType(newVO.getReferType());
        entity.setReferId(newVO.getReferId());
        entity.setAttachTypeCode(newVO.getAttachTypeCode());
        entity.setSavePath(newVO.getSavePath());
        entity.setSavePathBak1(newVO.getSavePathBak1());
        entity.setSavePathBak2(newVO.getSavePathBak2());
        entity.setAttachSize(newVO.getAttachSize());
        entity.setOrderIndex(newVO.getOrderIndex());
        entity.setExtend01(newVO.getExtend01());
        entity.setExtend02(newVO.getExtend02());
        entity.setExtend03(newVO.getExtend03());
        entity.setActived(newVO.getActived());
        entity.setCreator(newVO.getCreator());
        entity.setCreateTime(newVO.getCreateTime());
        entity.setUpdator(newVO.getUpdator());
        entity.setUpdateTime(newVO.getUpdateTime());

        return entity;
    }

    /**
     * 将领域模型转换为旧的实体类
     *
     * @param domainModel
     * @return com.jiuxi.admin.core.bean.entity.TpAttachinfo
     */
    public static com.jiuxi.admin.core.bean.entity.TpAttachinfo toOldEntity(com.jiuxi.domain.file.model.TpAttachinfo domainModel) {
        if (domainModel == null) {
            return null;
        }

        com.jiuxi.admin.core.bean.entity.TpAttachinfo entity = new com.jiuxi.admin.core.bean.entity.TpAttachinfo();
        entity.setAttachId(domainModel.getAttachId());
        entity.setAttachTitle(domainModel.getAttachTitle());
        entity.setAttachName(domainModel.getAttachName());
        entity.setReferType(domainModel.getReferType());
        entity.setReferId(domainModel.getReferId());
        entity.setAttachTypeCode(domainModel.getAttachTypeCode());
        entity.setSavePath(domainModel.getSavePath());
        entity.setSavePathBak1(domainModel.getSavePathBak1());
        entity.setSavePathBak2(domainModel.getSavePathBak2());
        entity.setAttachSize(domainModel.getAttachSize());
        entity.setOrderIndex(domainModel.getOrderIndex());
        entity.setExtend01(domainModel.getExtend01());
        entity.setExtend02(domainModel.getExtend02());
        entity.setExtend03(domainModel.getExtend03());
        entity.setActived(domainModel.getActived());
        entity.setCreator(domainModel.getCreator());
        entity.setCreateTime(domainModel.getCreateTime());
        entity.setUpdator(domainModel.getUpdator());
        entity.setUpdateTime(domainModel.getUpdateTime());

        return entity;
    }

    /**
     * 将旧的实体类转换为领域模型
     *
     * @param oldEntity
     * @return TpAttachinfo
     */
    public static com.jiuxi.domain.file.model.TpAttachinfo toDomainModel(com.jiuxi.admin.core.bean.entity.TpAttachinfo oldEntity) {
        if (oldEntity == null) {
            return null;
        }

        com.jiuxi.domain.file.model.TpAttachinfo domainModel = new com.jiuxi.domain.file.model.TpAttachinfo();
        domainModel.setAttachId(oldEntity.getAttachId());
        domainModel.setAttachTitle(oldEntity.getAttachTitle());
        domainModel.setAttachName(oldEntity.getAttachName());
        domainModel.setReferType(oldEntity.getReferType());
        domainModel.setReferId(oldEntity.getReferId());
        domainModel.setAttachTypeCode(oldEntity.getAttachTypeCode());
        domainModel.setSavePath(oldEntity.getSavePath());
        domainModel.setSavePathBak1(oldEntity.getSavePathBak1());
        domainModel.setSavePathBak2(oldEntity.getSavePathBak2());
        domainModel.setAttachSize(oldEntity.getAttachSize());
        domainModel.setOrderIndex(oldEntity.getOrderIndex());
        domainModel.setExtend01(oldEntity.getExtend01());
        domainModel.setExtend02(oldEntity.getExtend02());
        domainModel.setExtend03(oldEntity.getExtend03());
        domainModel.setActived(oldEntity.getActived());
        domainModel.setCreator(oldEntity.getCreator());
        domainModel.setCreateTime(oldEntity.getCreateTime());
        domainModel.setUpdator(oldEntity.getUpdator());
        domainModel.setUpdateTime(oldEntity.getUpdateTime());

        return domainModel;
    }

    /**
     * 将旧的VO转换为领域模型
     *
     * @param oldVO
     * @return TpAttachinfo
     */
    public static TpAttachinfo toDomainModel(com.jiuxi.admin.core.bean.vo.TpAttachinfoVO oldVO) {
        if (oldVO == null) {
            return null;
        }

        TpAttachinfo domainModel = new TpAttachinfo();
        domainModel.setAttachId(oldVO.getAttachId());
        domainModel.setAttachTitle(oldVO.getAttachTitle());
        domainModel.setAttachName(oldVO.getAttachName());
        domainModel.setReferType(oldVO.getReferType());
        domainModel.setReferId(oldVO.getReferId());
        domainModel.setAttachTypeCode(oldVO.getAttachTypeCode());
        domainModel.setSavePath(oldVO.getSavePath());
        domainModel.setSavePathBak1(oldVO.getSavePathBak1());
        domainModel.setSavePathBak2(oldVO.getSavePathBak2());
        domainModel.setAttachSize(oldVO.getAttachSize());
        domainModel.setOrderIndex(oldVO.getOrderIndex());
        domainModel.setExtend01(oldVO.getExtend01());
        domainModel.setExtend02(oldVO.getExtend02());
        domainModel.setExtend03(oldVO.getExtend03());
        domainModel.setActived(oldVO.getActived());
        domainModel.setCreator(oldVO.getCreator());
        domainModel.setCreateTime(oldVO.getCreateTime());
        domainModel.setUpdator(oldVO.getUpdator());
        domainModel.setUpdateTime(oldVO.getUpdateTime());

        return domainModel;
    }

    /**
     * 将新的VO转换为旧的VO
     *
     * @param newVO
     * @return com.jiuxi.admin.core.bean.vo.TpAttachinfoVO
     */
    public static com.jiuxi.admin.core.bean.vo.TpAttachinfoVO toOldVO(TpAttachinfoVO newVO) {
        if (newVO == null) {
            return null;
        }

        com.jiuxi.admin.core.bean.vo.TpAttachinfoVO oldVO = new com.jiuxi.admin.core.bean.vo.TpAttachinfoVO();
        oldVO.setAttachId(newVO.getAttachId());
        oldVO.setAttachTitle(newVO.getAttachTitle());
        oldVO.setAttachName(newVO.getAttachName());
        oldVO.setReferType(newVO.getReferType());
        oldVO.setReferId(newVO.getReferId());
        oldVO.setAttachTypeCode(newVO.getAttachTypeCode());
        oldVO.setSavePath(newVO.getSavePath());
        oldVO.setSavePathBak1(newVO.getSavePathBak1());
        oldVO.setSavePathBak2(newVO.getSavePathBak2());
        oldVO.setAttachSize(newVO.getAttachSize());
        oldVO.setOrderIndex(newVO.getOrderIndex());
        oldVO.setExtend01(newVO.getExtend01());
        oldVO.setExtend02(newVO.getExtend02());
        oldVO.setExtend03(newVO.getExtend03());
        oldVO.setActived(newVO.getActived());
        oldVO.setCreator(newVO.getCreator());
        oldVO.setCreateTime(newVO.getCreateTime());
        oldVO.setUpdator(newVO.getUpdator());
        oldVO.setUpdateTime(newVO.getUpdateTime());

        return oldVO;
    }
}