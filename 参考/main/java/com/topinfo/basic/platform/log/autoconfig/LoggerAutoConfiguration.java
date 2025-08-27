package com.topinfo.basic.platform.log.autoconfig;

import com.topinfo.basic.platform.log.core.dao.TpOperateLogDao;
import com.topinfo.basic.platform.log.core.dao.impl.TpOperateLogMysqlDao;
import com.topinfo.basic.platform.log.core.dao.impl.TpOperateLogTDDao;
import com.topinfo.basic.platform.log.core.service.TpFileLogService;
import com.topinfo.basic.platform.log.core.service.TpOperateLogService;
import com.topinfo.basic.platform.log.core.service.impl.TpFileLogServiceImpl;
import com.topinfo.basic.platform.log.core.service.impl.TpOperateLogServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author pand
 * @date 2021-08-02 14:01
 */
@EnableConfigurationProperties({LoggerConfigurationProperties.class})
@ComponentScan({"com.topinfo.basic.platform.log.core.controller"})
@MapperScan({"com.topinfo.basic.platform.log.core.mapper"})
public class LoggerAutoConfiguration {


    /**
     * TD dao
     * @author 杨攀
     * @date 2022/9/21 17:42
     * @param
     * @return com.topinfo.basic.platform.log.core.dao.TpOperateLogDao
     */
    @Bean
    @ConditionalOnProperty(prefix = "topinfo.logger", name = "dbtype", havingValue = "TD")
    public TpOperateLogDao tpOperateLogTDDao(){
        return new TpOperateLogTDDao();
    }

    /**
     * Mysql dao
     * @author 杨攀
     * @date 2022/9/21 17:42
     * @param
     * @return com.topinfo.basic.platform.log.core.dao.TpOperateLogDao
     */
    @Bean
    @ConditionalOnMissingBean
    public TpOperateLogDao tpOperateLogMysqlDao(){
        return new TpOperateLogMysqlDao();
    }

    /**
     * 日志服务
     * @author 杨攀
     * @date 2022/9/22 16:03
     * @param
     * @return com.topinfo.basic.platform.log.core.service.TpOperateLogService
     */
    @Bean
    public TpOperateLogService tpOperateLogService(){
        return new TpOperateLogServiceImpl();
    }


    /**
     * 文件上传日志记录
     * @author 杨攀
     * @date 2022/10/18 17:08
     * @param
     * @return com.topinfo.basic.platform.log.core.service.TpFileLogService
     */
    @Bean
    public TpFileLogService tpFileLogService(){
        return new TpFileLogServiceImpl();
    }

}
