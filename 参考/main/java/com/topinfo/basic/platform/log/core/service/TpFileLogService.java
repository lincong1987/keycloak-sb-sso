package com.topinfo.basic.platform.log.core.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @ClassName: TpFileLogService
 * @Description: 文件上传日志记录
 * @Author: 杨攀
 * @Date: 2022/10/18 16:40
 * @Copyright: 2022 www.tuxun.net Inc. All rights reserved.
 */
public interface TpFileLogService {


    /**
     * 文件上传 操作日志采集
     * @author 杨攀
     * @date 2022/10/18 16:55
     * @param moduleCode 模块code
     * @param operterRid 业务记录ID、
     * @param token
     * @param file 文件
     * @param request
     * @return void
     */
    void collection(String moduleCode, String operterRid, String token, MultipartFile file, HttpServletRequest request);
}
