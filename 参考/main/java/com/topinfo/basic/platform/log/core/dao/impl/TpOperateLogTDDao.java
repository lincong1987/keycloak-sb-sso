package com.topinfo.basic.platform.log.core.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topinfo.basic.platform.log.core.bean.entity.TpOperateLog;
import com.topinfo.basic.platform.log.core.bean.query.TpOperateLogQuery;
import com.topinfo.basic.platform.log.core.bean.vo.TpOperateLogVO;
import com.topinfo.basic.platform.log.core.dao.TpOperateLogDao;
import com.topinfo.basic.platform.log.core.mapper.TpLogMapper;
import com.topinfo.basic.platform.log.core.mapper.TpOperateLogMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: TpOperateLogTDDao
 * @Description: TDengine
 * @Author: 杨攀
 * @Date: 2022/9/21 17:10
 * @Copyright: 2022 www.tuxun.net Inc. All rights reserved.
 */
public class TpOperateLogTDDao implements TpOperateLogDao {


    @Autowired
    private TpLogMapper tpLogMapper;

    @Override
    public IPage<TpOperateLogVO> getPage(Page<TpOperateLogVO> page, TpOperateLogQuery query) {

        // 开始时间
        String operterTimeStart = query.getOperterTimeStart();
        // 结束时间
        String operterTimeEnd = query.getOperterTimeEnd();

        // TD 需要处理一下 日期格式
        String start =  LocalDateTime.parse(operterTimeStart, DateTimeFormatter.ofPattern("uuuuMMddHHmmss")).format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));
        String end =  LocalDateTime.parse(operterTimeEnd, DateTimeFormatter.ofPattern("uuuuMMddHHmmss")).format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));

        query.setOperterTimeStart(start);
        query.setOperterTimeEnd(end);

        return tpLogMapper.getPage(page, query);
    }

    @Override
    public int add(TpOperateLog bean) {
        return tpLogMapper.add(bean);
    }

    @Override
    public TpOperateLogVO view(String id) {
        return null;
    }
}
