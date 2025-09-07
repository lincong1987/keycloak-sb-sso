package com.jiuxi.admin.core.service;

import com.jiuxi.admin.core.bean.entity.TpAttachinfo;
import com.jiuxi.core.bean.TopinfoRuntimeException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件服务适配器接口
 * 用于兼容旧的代码依赖
 */
public interface FileService {

    /**
     * 上传文件 - 普通表单
     *
     * @param files      文件数组
     * @param creator    创建者
     * @param referType  关联业务类型
     * @param referId    关联业务主键
     * @return List<TpAttachinfo> 附件信息列表
     */
    List<TpAttachinfo> uploadFile(MultipartFile[] files, String creator, String referType, String referId) throws TopinfoRuntimeException;

    /**
     * 上传文件 - base64
     *
     * @param base64s           base64编码的文件内容数组
     * @param base64sFileName   文件名数组
     * @param creator           创建者
     * @param referType         关联业务类型
     * @param referId           关联业务主键
     * @return List<TpAttachinfo> 附件信息列表
     */
    List<TpAttachinfo> uploadFile(String[] base64s, String[] base64sFileName, String creator, String referType, String referId) throws TopinfoRuntimeException;

    /**
     * 附件下载
     *
     * @param id       附件ID
     * @param request  HTTP请求
     * @param response HTTP响应
     */
    void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 根据路径下载附件
     *
     * @param filePath     附件路径
     * @param downFileName 下载后的文件名称
     * @param request      HTTP请求
     * @param response     HTTP响应
     */
    void downloadByPath(String filePath, String downFileName, HttpServletRequest request, HttpServletResponse response) throws Exception;
}