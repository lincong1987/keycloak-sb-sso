package com.jiuxi.domain.file.service.impl;

import com.jiuxi.domain.file.model.TpAttachinfo;
import com.jiuxi.domain.file.service.FileDomainService;
import com.jiuxi.domain.file.repo.TpAttachinfoRepository;
import com.jiuxi.admin.core.service.FileExpService;
import com.jiuxi.common.util.SnowflakeIdUtil;
import com.jiuxi.common.util.CommonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件领域服务实现类
 */
@Service
public class FileDomainServiceImpl implements FileDomainService {

    @Autowired
    private TpAttachinfoRepository tpAttachinfoRepository;

    @Autowired
    private FileExpService fileExpService;

    /**
     * 上传文件 - 普通表单
     *
     * @param files      文件字节数组
     * @param fileNames  文件名数组
     * @param creator    创建者
     * @param referType  关联业务类型
     * @param referId    关联业务主键
     * @return List<TpAttachinfo> 附件信息列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TpAttachinfo> uploadFile(byte[][] files, String[] fileNames, String creator, String referType, String referId) {
        try {
            List<TpAttachinfo> list = new ArrayList<>();

            for (int i = 0; i < files.length; i++) {
                byte[] file = files[i];
                String fileName = fileNames[i];
                
                // 获取后缀名 .txt
                String ext = fileName.substring(fileName.lastIndexOf("."));
                // 检查后缀名
                CommonFileUtil.checkWhiteExt(ext);

                // 上传文件
                InputStream inputStream = new ByteArrayInputStream(file);
                String relDir = fileExpService.uploadFile(inputStream, file.length, ext);

                // 初始化数据库数据
                TpAttachinfo model = this.initModel(fileName, relDir, Double.valueOf(file.length), creator, referType, referId);
                list.add(model);

                // 数据入库
                this.tpAttachinfoRepository.save(model);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("附件上传失败: " + e.getMessage(), e);
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
    @Transactional(rollbackFor = Exception.class)
    public List<TpAttachinfo> uploadFile(String[] base64s, String[] base64sFileName, String creator, String referType, String referId) {
        try {
            List<TpAttachinfo> list = new ArrayList<>();

            for (int i = 0; i < base64s.length; i++) {
                String item = base64s[i];

                // 如果格式是：data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAABIUAA......，需要去掉,以前的数据
                String base64;
                int index = item.indexOf(";base64,");
                if (index != -1) {
                    index = index + 8;
                    base64 = item.substring(index);
                } else {
                    base64 = item;
                }

                // 按字节编码
                byte[] encodeImage = cn.hutool.core.codec.Base64.decode(base64);

                // 创建文件名
                String fileName = "";
                String fileExtName = ".jpg";
                if (null != base64sFileName && base64sFileName.length >= 1) {
                    fileName = base64sFileName[i];
                    int dot = fileName.lastIndexOf(".");
                    fileExtName = fileName.substring(dot);
                } else {
                    fileName = "file_" + System.currentTimeMillis() + fileExtName;
                }

                InputStream inputStream = new ByteArrayInputStream(encodeImage);
                // 上传文件
                String relDir = fileExpService.uploadFile(inputStream, encodeImage.length, fileExtName);
                // 初始化数据库数据
                TpAttachinfo model = this.initModel(fileName, relDir, Double.valueOf(encodeImage.length), creator, referType, referId);
                list.add(model);
                // 数据入库
                this.tpAttachinfoRepository.save(model);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("base64图片上传失败: " + e.getMessage(), e);
        }
    }

    /**
     * 初始化附件模型
     *
     * @param filename   文件名
     * @param savePath   保存路径
     * @param size       文件大小
     * @param creator    创建者
     * @param referType  关联业务类型
     * @param referId    关联业务主键
     * @return TpAttachinfo 附件信息
     */
    private TpAttachinfo initModel(String filename, String savePath, Double size, String creator, String referType, String referId) {
        TpAttachinfo model = new TpAttachinfo();
        model.setAttachId(SnowflakeIdUtil.nextIdStr());
        model.setSavePath(savePath);
        model.setAttachSize(size);
        model.setAttachName(filename);
        model.setReferType(referType != null ? referType : "");
        model.setCreator(creator);
        model.setReferId(referId != null ? referId : "");
        return model;
    }

    /**
     * 附件下载
     *
     * @param id 附件ID
     * @return InputStream 文件输入流
     */
    @Override
    public InputStream download(String id) throws Exception {
        TpAttachinfo model = tpAttachinfoRepository.view(id);
        if (model != null && model.getSavePath() != null) {
            // 下载文件路径
            String filePath = model.getSavePath();
            // 获取文件流
            return this.getInputStream(filePath, model.getAttachName());
        } else {
            throw new RuntimeException("该附件不存在！");
        }
    }

    /**
     * 根据路径下载附件
     *
     * @param filePath     附件路径
     * @param downFileName 附件名称
     * @return InputStream 文件输入流
     */
    @Override
    public InputStream downloadByPath(String filePath, String downFileName) throws Exception {
        // 获取文件流
        return this.getInputStream(filePath, downFileName);
    }

    /**
     * 上传文件
     *
     * @param fileContent 文件内容
     * @param fileName    文件名
     * @return String 保存路径
     */
    @Override
    public String uploadFile(byte[] fileContent, String fileName) {
        try {
            InputStream inputStream = new ByteArrayInputStream(fileContent);
            // 获取后缀名 .txt
            String ext = fileName.substring(fileName.lastIndexOf("."));
            // 上传文件
            return fileExpService.uploadFile(inputStream, fileContent.length, ext);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

    /**
     * 上传文件
     *
     * @param fileContent  文件内容
     * @param fileSize     文件大小
     * @param fileExtName  扩展名，如：.jpg
     * @return String 保存路径
     */
    @Override
    public String uploadFile(byte[] fileContent, long fileSize, String fileExtName) {
        try {
            InputStream inputStream = new ByteArrayInputStream(fileContent);
            // 上传文件
            return fileExpService.uploadFile(inputStream, fileSize, fileExtName);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

    /**
     * 下载文件
     *
     * @param fileid   文件服务器fileid，如：/D1/02/89/xxxxxxxxxxxxxxx.txt
     * @param filename 下载后的文件名称
     * @return InputStream 文件输入流
     */
    @Override
    public InputStream getInputStream(String fileid, String filename) {
        return fileExpService.getInputStream(fileid, filename);
    }

    /**
     * 根据业务id查询附件列表
     *
     * @param referId   关联业务主键
     * @param referType 关联业务类型
     * @return List<TpAttachinfo> 附件信息列表
     */
    @Override
    public List<TpAttachinfo> getByReferId(String referId, String referType) {
        return tpAttachinfoRepository.selectByReferIdAndType(referId, referType);
    }

    /**
     * 删除附件
     * <pre>
     * 根据 referId 和 attachId 删除附件：
     *    如果 referId 为空，则只能删除当前登录人自己创建的附件，物理删除
     *    如果 referId 不为空， 则根据 数据权限 判断是否能够删除，如果权限不对，则排查异常 逻辑删除
     * </pre>
     *
     * @param referId 关联业务主键
     * @param attachId 附件标识
     * @param jwtpid  JWT用户标识
     * @return int 删除结果
     */
    @Override
    public int delete(String referId, String attachId, String jwtpid) {
        return tpAttachinfoRepository.remove(referId, attachId, jwtpid);
    }

    /**
     * 根据referId删除附件
     *
     * @param referId   关联业务主键
     * @param referType 关联业务类型
     * @param jwtpid    JWT用户标识
     * @return int 删除结果
     */
    @Override
    public int deleteByRefer(String referId, String referType, String jwtpid) {
        return tpAttachinfoRepository.deleteByReferIdAndReferType(referId, referType, jwtpid);
    }
}