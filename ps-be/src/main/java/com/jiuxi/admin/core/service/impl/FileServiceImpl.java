package com.jiuxi.admin.core.service.impl;

import com.jiuxi.admin.core.bean.entity.TpAttachinfo;
import com.jiuxi.admin.core.service.FileService;
import com.jiuxi.app.file.service.FileAppService;
import com.jiuxi.core.bean.TopinfoRuntimeException;
import com.jiuxi.app.file.dto.TpAttachinfoVO;
import com.jiuxi.app.file.assembler.FileAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件服务适配器实现类
 * 用于兼容旧的代码依赖，将调用转发到新的FileAppService
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private FileAppService fileAppService;

    /**
     * 上传文件 - 普通表单
     *
     * @param files      文件数组
     * @param creator    创建者
     * @param referType  关联业务类型
     * @param referId    关联业务主键
     * @return List<TpAttachinfo> 附件信息列表
     */
    @Override
    public List<TpAttachinfo> uploadFile(MultipartFile[] files, String creator, String referType, String referId) throws TopinfoRuntimeException {
        try {
            List<TpAttachinfoVO> vos = fileAppService.uploadFile(files, creator, referType, referId);
            return vos.stream()
                    .map(FileAssembler::toOldEntity)  // 使用正确的转换方法
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TopinfoRuntimeException(-1, "文件上传失败: " + e.getMessage());
        }
    }

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
    @Override
    public List<TpAttachinfo> uploadFile(String[] base64s, String[] base64sFileName, String creator, String referType, String referId) throws TopinfoRuntimeException {
        try {
            List<TpAttachinfoVO> vos = fileAppService.uploadFile(base64s, base64sFileName, creator, referType, referId);
            return vos.stream()
                    .map(FileAssembler::toOldEntity)  // 使用正确的转换方法
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TopinfoRuntimeException(-1, "文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 附件下载
     *
     * @param id       附件ID
     * @param request  HTTP请求
     * @param response HTTP响应
     */
    @Override
    public void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileAppService.download(id, request, response);
    }

    /**
     * 根据路径下载附件
     *
     * @param filePath     附件路径
     * @param downFileName 下载后的文件名称
     * @param request      HTTP请求
     * @param response     HTTP响应
     */
    @Override
    public void downloadByPath(String filePath, String downFileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileAppService.downloadByPath(filePath, downFileName, request, response);
    }
}