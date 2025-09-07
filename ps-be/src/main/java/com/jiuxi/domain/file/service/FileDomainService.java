package com.jiuxi.domain.file.service;

import com.jiuxi.domain.file.model.TpAttachinfo;
import java.io.InputStream;
import java.util.List;

/**
 * 文件领域服务接口
 */
public interface FileDomainService {

    /**
     * 上传文件 - 普通表单
     *
     * @param files
     * @param creator
     * @param referType
     * @param referId
     * @return boolean
     */
    List<TpAttachinfo> uploadFile(byte[][] files, String[] fileNames, String creator, String referType, String referId);

    /**
     * 上传文件 - base64
     *
     * @param base64s
     * @param base64sFileName
     * @param creator
     * @param referType
     * @param referId
     * @return boolean
     */
    List<TpAttachinfo> uploadFile(String[] base64s, String[] base64sFileName, String creator, String referType, String referId);

    /**
     * 附件下载
     * @param id 附件ID
     * @return InputStream
     */
    InputStream download(String id) throws Exception;

    /**
     * 根据路径下载附件
     *
     * @param filePath     附件路径
     * @param downFileName 附件名称
     * @return InputStream
     */
    InputStream downloadByPath(String filePath, String downFileName) throws Exception;

    /**
     * 上传文件
     * @param fileContent
     * @param fileName
     * @return java.lang.String
     */
    String uploadFile(byte[] fileContent, String fileName);

    /**
     * 上传文件
     * @param fileContent
     * @param fileSize 文件大小
     * @param fileExtName 扩展名，如：.jpg
     * @return java.lang.String
     */
    String uploadFile(byte[] fileContent, long fileSize, String fileExtName);

    /**
     * 下载文件
     * @param fileid  文件服务器fileid，如：/D1/02/89/xxxxxxxxxxxxxxx.txt
     * @param filename 下载后的文件名称
     * @return java.io.InputStream
     */
    InputStream getInputStream(String fileid, String filename);

    /**
     * 根据业务id查询附件列表
     *
     * @param referId
     * @param referType
     * @return List<TpAttachinfo>
     */
    List<TpAttachinfo> getByReferId(String referId, String referType);

    /**
     * 删除附件
     * <pre>
     * 根据 referId 和 attachId 删除附件：
     *    如果 referId 为空，则只能删除当前登录人自己创建的附件，物理删除
     *    如果 referId 不为空， 则根据 数据权限 判断是否能够删除，如果权限不对，则排查异常 逻辑删除
     * </pre>
     *
     * @param referId
     * @param attachId
     * @param jwtpid
     * @return int
     */
    int delete(String referId, String attachId, String jwtpid);

    /**
     * 根据referId删除附件
     *
     * @param referId
     * @param referType
     * @param jwtpid
     * @return int
     */
    int deleteByRefer(String referId, String referType, String jwtpid);
}