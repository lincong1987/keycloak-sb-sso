package com.jiuxi.admin.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuxi.admin.core.mapper.TpAccountThirdMapper;
import com.jiuxi.admin.core.service.TpAccountThirdService;
import com.jiuxi.common.exception.ExceptionUtils;
import com.jiuxi.common.util.CommonDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

import com.jiuxi.admin.core.bean.entity.TpAccountThird;
import com.jiuxi.admin.core.bean.vo.TpAccountThirdVO;
import com.jiuxi.admin.core.bean.query.TpAccountThirdQuery;
import com.jiuxi.core.bean.TopinfoRuntimeException;

import java.util.Optional;
import java.util.List;

/**
 * @ClassName: TpAccountThirdServiceImpl
 * @Description: 合作方管理表
 * @Author pand
 * @Date 2022-04-20 15:02:39
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service("tpAccountThirdService")
public class TpAccountThirdServiceImpl implements TpAccountThirdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TpAccountThirdServiceImpl.class);

    @Autowired
    private  TpAccountThirdMapper  tpAccountThirdMapper;

    @Override
    public IPage<TpAccountThirdVO> queryPage(TpAccountThirdQuery query) {

        try {
            Integer pageNum = Optional.ofNullable(query.getCurrent()).orElse(1);
            Integer pageSize = Optional.ofNullable(query.getSize()).orElse(10);

            Page<TpAccountThirdVO> page = new Page<>(pageNum, pageSize);
            IPage<TpAccountThirdVO> iPage = tpAccountThirdMapper.getPage(page, query);

            return iPage;
        } catch (Exception e) {
            LOGGER.error("列表信息查询失败！query:{}, 错误: {}", JSONObject.toJSONString(query), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "列表信息查询失败！");
        }

    }

    @Override
    public String add(TpAccountThirdVO vo, String jwtpid) {
        TpAccountThird bean = new TpAccountThird();
        // 转换成数据库对象
        BeanUtil.copyProperties(vo, bean);
        
        // 生成主键
        String appKey = vo.getAppKey();
        if (StrUtil.isBlank(appKey)) {
            appKey = CommonDateUtil.now().substring(0, 14) + (int) (Math.random() * 9000 + 1000);
        }
        bean.setAppKey(appKey);
        
        // 创建人
        bean.setCreator(jwtpid);
        // 创建时间
        bean.setCreateTime(CommonDateUtil.now());
        // 是否有效
        bean.setActived(1);

        try {
            tpAccountThirdMapper.add(bean);
            return appKey;
        } catch (Exception e) {
            LOGGER.error("新增失败！vo:{}, 错误: {}", JSONObject.toJSONString(vo), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, StrUtil.isBlank(e.getMessage()) ? "新增失败！" : e.getMessage());
        }
    }

    @Override
    public int reset(String accountId, String jwtpid) {
        TpAccountThird bean = new TpAccountThird();
        bean.setAppKey(accountId);
        
        // 更新人
        bean.setUpdator(jwtpid);
        // 更新时间
        bean.setUpdateTime(CommonDateUtil.now());

        try {
            int count = tpAccountThirdMapper.reset(bean);
            return count;
        } catch (Exception e) {
            LOGGER.error("重置失败！accountId:{}, 错误: {}", accountId, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "重置失败！");
        }
    }

    @Override
    public TpAccountThirdVO view(String id) {

        try {
            TpAccountThirdVO vo = tpAccountThirdMapper.view(id);
            return vo;
        } catch (Exception e) {
            LOGGER.error("查看失败！id:{}, 错误: {}", id, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "查看失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = TopinfoRuntimeException.class)
    public int deleteByIds(List<String> ids, String jwtpid) {
        String now = CommonDateUtil.now();

        try {
            if (null == ids || ids.isEmpty()) {
                throw new TopinfoRuntimeException(-1, "删除数据id不能为空！");
            }
            // 使用SQL批量操作删除数据，提高性能
            tpAccountThirdMapper.batchDelete(ids.toArray(new String[0]), now, jwtpid);
            return ids.size();
        } catch (Exception e) {
            LOGGER.error("删除失败！ids:{}, 错误: {}", ids, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "删除失败！");
        }
    }

}