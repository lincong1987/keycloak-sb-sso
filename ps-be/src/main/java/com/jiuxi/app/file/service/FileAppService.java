package com.jiuxi.app.file.service;

import com.jiuxi.app.file.dto.TpAttachinfoVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 文件应用服务接口
 */
public interface FileAppService {

    /**
     * 上传文件 - 普通表单
     *
     * @param files 上传的文件数组
     * @param creator 创建者
     * @param referType 关联业务类型
     * @param referId 关联业务主键
     * @return List<TpAttachinfoVO> 附件信息列表
     */
    List<TpAttachinfoVO> uploadFile(MultipartFile[] files, String creator, String referType, String referId);

    /**
     * 上传文件 - base64
     *
     * @param base64s base64编码的文件内容数组
     * @param base64sFileName 文件名数组
     * @param creator 创建者
     * @param referType 关联业务类型
     * @param referId 关联业务主键
     * @return List<TpAttachinfoVO> 附件信息列表
     */
    List<TpAttachinfoVO> uploadFile(String[] base64s, String[] base64sFileName, String creator, String referType, String referId);

    /**
     * 附件下载
     * @param id 附件ID
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 根据路径下载附件
     *
     * @param filePath 附件路径
     * @param downFileName 下载后的文件名称
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    void downloadByPath(String filePath, String downFileName, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 根据业务id查询附件列表
     *
     * @param referId 关联业务主键
     * @param referType 关联业务类型
     * @return List<TpAttachinfoVO> 附件信息列表
     */
    List<TpAttachinfoVO> getByReferId(String referId, String referType);

    /**
     * 删除附件
     * <pre>
     * 根据 referId 和 attachId 删除附件：
     *    如果 referId 为空，则只能删除当前登录人自己创建的附件，物理删除
     *    如果 referId 不为空， 则根据 数据权限 判断是否能够删除，如果权限不对，则排查异常 逻辑删除
     *</pre>
     *
     * @param referId 关联业务主键
     * @param attachId 附件标识
     * @param jwtpid JWT用户标识
     * @return int 删除结果
     */
    int delete(String referId, String attachId, String jwtpid);

    /**
     * 根据referId删除附件
     *
     * @param referId 关联业务主键
     * @param referType 关联业务类型
     * @param jwtpid JWT用户标识
     * @return int 删除结果
     */
    int deleteByRefer(String referId, String referType, String jwtpid);
}