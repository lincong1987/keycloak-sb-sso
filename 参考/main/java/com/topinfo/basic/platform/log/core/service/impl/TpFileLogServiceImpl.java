package com.topinfo.basic.platform.log.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.topinfo.basic.platform.common.util.JwtTokenUtils;
import com.topinfo.basic.platform.common.util.SnowflakeIdUtil;
import com.topinfo.basic.platform.jdfs.common.service.JdfsClientService;
import com.topinfo.basic.platform.log.core.bean.entity.TpOperateLog;
import com.topinfo.basic.platform.log.core.constant.LoggerConstant;
import com.topinfo.basic.platform.log.core.dao.TpOperateLogDao;
import com.topinfo.basic.platform.log.core.service.TpFileLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @ClassName: TpFileLogServiceImpl
 * @Description: 文件上传日志记录
 * @Author: 杨攀
 * @Date: 2022/10/18 16:45
 * @Copyright: 2022 www.tuxun.net Inc. All rights reserved.
 */
public class TpFileLogServiceImpl implements TpFileLogService {


    private static final String OPERTER_TYPE = "uploadFile";


    @Autowired
    private TpOperateLogDao tpOperateLogDao;


    /**
     * 文件服务器客户端， 如果没注入文件服务器，则不执行留痕， 因为有些场景可能不需要文件服务器进行上传留痕
     */
    @Autowired(required = false)
    private JdfsClientService jdfsClientService;

    /**
     * 文件上传 操作日志采集
     * @author 杨攀
     * @date 2022/10/18 16:55
     * @param moduleCode 模块code
     * @param operterRid 业务记录ID、
     * @param token
     * @param file 上传文件
     * @param request
     * @return void
     */
    @Override
    public void collection(String moduleCode, String operterRid, String token, MultipartFile file, HttpServletRequest request) {

        // 如果没注入文件服务器，则不执行留痕， 因为有些场景可能不需要文件服务器进行上传留痕
        if(jdfsClientService == null){
            return;
        }

        try {

            // 上传附件
            String  relDir = jdfsClientService.uploadFile(file);

            // 保存访问客户端ip
            String ip = request.getHeader("X-Forwarded-For");
            if(StrUtil.isBlank(ip)){
                ip = request.getRemoteAddr();
            }

            // 保存浏览器类型
            String userAgent = request.getHeader("User-Agent");

            // 生成主键
            String id = SnowflakeIdUtil.nextIdStr();
            String now = LocalDateTime.now().format(LoggerConstant.DateFormatter.yyyyMMddHHmmss);

            String pid = JwtTokenUtils.getPersonId(token);
            String ascnId = JwtTokenUtils.getAscnId(token);
            String cityCode = JwtTokenUtils.getCityCode(token);

            TpOperateLog bean = new TpOperateLog();
            // 设置主键id
            bean.setLogId(id);
            // 操作人员id
            bean.setPersonId(pid);
            // 模块code
            bean.setModuleCode(moduleCode);
            // 操作类型
            bean.setOperterType(OPERTER_TYPE);
            // 操作时间
            bean.setOperterTime(now);
            // 操作记录ID、修改、删除时，记录ID
            bean.setOperterRid(operterRid);
            // 操作人IP
            bean.setOperterIp(ip);
            // 单位ID
            bean.setAscnId(ascnId);
            // 操作人浏览器
            bean.setOperterBrowser(userAgent);
            // 行政区划
            bean.setCityCode(cityCode);
            // 创建人
            bean.setCreator(pid);
            // 创建时间
            bean.setCreateTime(now);
            // 修改人
            bean.setUpdator(pid);
            // 修改时间
            bean.setUpdateTime(now);
            // 是否有效
            bean.setActived(LoggerConstant.YES);
            // 附件路径
            bean.setExtend01(relDir);

            // 新增
            tpOperateLogDao.add(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
