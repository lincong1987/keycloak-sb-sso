package com.topinfo.basic.platform.log.core.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topinfo.basic.platform.log.core.bean.entity.TpOperateLog;
import com.topinfo.basic.platform.log.core.bean.query.TpOperateLogQuery;
import com.topinfo.basic.platform.log.core.bean.vo.TpOperateLogVO;
import com.topinfo.basic.platform.log.core.dao.TpOperateLogDao;
import com.topinfo.basic.platform.log.core.mapper.TpOperateLogMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: TpOperateLogMysqlDao
 * @Description: Mysql
 * @Author: 杨攀
 * @Date: 2022/9/21 17:10
 * @Copyright: 2022 www.tuxun.net Inc. All rights reserved.
 */
public class TpOperateLogMysqlDao implements TpOperateLogDao {

    @Autowired
    private TpOperateLogMapper tpOperateLogMapper;


    @Override
    public IPage<TpOperateLogVO> getPage(Page<TpOperateLogVO> page, TpOperateLogQuery query) {
        return tpOperateLogMapper.getPage(page, query);
    }

    @Override
    public int add(TpOperateLog bean) {
        return tpOperateLogMapper.add(bean);
    }

    @Override
    public TpOperateLogVO view(String id) {
        return null;
    }
}
