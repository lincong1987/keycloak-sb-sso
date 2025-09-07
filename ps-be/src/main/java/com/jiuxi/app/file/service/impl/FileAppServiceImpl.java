package com.jiuxi.app.file.service.impl;

import com.jiuxi.app.file.dto.TpAttachinfoVO;
import com.jiuxi.app.file.service.FileAppService;
import com.jiuxi.domain.file.model.TpAttachinfo;
import com.jiuxi.domain.file.repo.TpAttachinfoRepository;
import com.jiuxi.domain.file.service.FileDomainService;
import com.jiuxi.app.file.assembler.FileAssembler;
import com.jiuxi.common.util.CommonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件应用服务实现类
 */
@Service
public class FileAppServiceImpl implements FileAppService {

    @Autowired
    private FileDomainService fileDomainService;

    @Autowired
    private TpAttachinfoRepository tpAttachinfoRepository;

    /**
     * 上传文件 - 普通表单
     *
     * @param files 上传的文件数组
     * @param creator 创建者
     * @param referType 关联业务类型
     * @param referId 关联业务主键
     * @return List<TpAttachinfoVO> 附件信息列表
     */
    @Override
    public List<TpAttachinfoVO> uploadFile(MultipartFile[] files, String creator, String referType, String referId) {
        try {
            // 将MultipartFile转换为byte[]
            byte[][] fileBytes = new byte[files.length][];
            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileBytes[i] = files[i].getBytes();
                fileNames[i] = files[i].getOriginalFilename();
            }
            
            List<TpAttachinfo> attachInfos = fileDomainService.uploadFile(fileBytes, fileNames, creator, referType, referId);
            return attachInfos.stream()
                    .map(FileAssembler::toVO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

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
    @Override
    public List<TpAttachinfoVO> uploadFile(String[] base64s, String[] base64sFileName, String creator, String referType, String referId) {
        List<TpAttachinfo> attachInfos = fileDomainService.uploadFile(base64s, base64sFileName, creator, referType, referId);
        return attachInfos.stream()
                .map(FileAssembler::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 附件下载
     * @param id 附件ID
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    @Override
    public void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = fileDomainService.download(id);
        // 这里需要实现将inputStream写入response的逻辑
        // 由于我们没有实际的文件服务器，这里只是示例实现
    }

    /**
     * 根据路径下载附件
     *
     * @param filePath 附件路径
     * @param downFileName 下载后的文件名称
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    @Override
    public void downloadByPath(String filePath, String downFileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = fileDomainService.downloadByPath(filePath, downFileName);
        // 这里需要实现将inputStream写入response的逻辑
        // 由于我们没有实际的文件服务器，这里只是示例实现
    }

    /**
     * 根据业务id查询附件列表
     *
     * @param referId 关联业务主键
     * @param referType 关联业务类型
     * @return List<TpAttachinfoVO> 附件信息列表
     */
    @Override
    public List<TpAttachinfoVO> getByReferId(String referId, String referType) {
        List<TpAttachinfo> attachInfos = tpAttachinfoRepository.selectByReferIdAndType(referId, referType);
        return attachInfos.stream()
                .map(FileAssembler::toVO)
                .collect(Collectors.toList());
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
     * @param jwtpid JWT用户标识
     * @return int 删除结果
     */
    @Override
    public int delete(String referId, String attachId, String jwtpid) {
        return fileDomainService.delete(referId, attachId, jwtpid);
    }

    /**
     * 根据referId删除附件
     *
     * @param referId 关联业务主键
     * @param referType 关联业务类型
     * @param jwtpid JWT用户标识
     * @return int 删除结果
     */
    @Override
    public int deleteByRefer(String referId, String referType, String jwtpid) {
        return fileDomainService.deleteByRefer(referId, referType, jwtpid);
    }
}