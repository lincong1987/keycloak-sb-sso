package com.topinfo.basic.platform.log.core.mapper;

import com.topinfo.basic.platform.log.core.bean.entity.TpOperateLog;
import com.topinfo.basic.platform.log.core.bean.vo.TpOperateLogVO;
import com.topinfo.basic.platform.log.core.bean.query.TpOperateLogQuery;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: TpOperateLogMapper
 * @Description: 操作日志表  - Mysql
 * @Author 杨攀
 * @Date 2022-09-21 14:00:19
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public interface TpOperateLogMapper {

    IPage<TpOperateLogVO> getPage(Page<TpOperateLogVO> page, @Param("query") TpOperateLogQuery query);

    int add(TpOperateLog bean);

    TpOperateLogVO view(String id);
}
