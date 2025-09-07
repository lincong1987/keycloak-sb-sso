package com.jiuxi.intf.file.web;

import com.jiuxi.app.file.dto.TpAttachinfoVO;
import com.jiuxi.app.file.service.FileAppService;
import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.core.core.annotation.IgnoreAuthorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 文件控制器
 */
@RestController("newFileController")
@RequestMapping("/app/sys/file/v2")  // 修改URL映射路径以避免冲突
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    /**
     * 文件应用服务
     */
    @Autowired
    private FileAppService fileAppService;

    /**
     * 附件上传接口
     *
     * @param files 上传的附件
     * @param referId 业务id
     * @param jwtpid JWT用户标识
     * @param referType 业务类型
     * @param request HTTP请求
     * @return JsonResponse 响应结果
     */
    @PostMapping(value = "/upload")
    public JsonResponse upload(@RequestParam(value = "file", required = false) MultipartFile[] files,
                               @RequestParam(required = false) String referId,
                               String jwtpid,
                               String referType,
                               HttpServletRequest request) {
        // 校验token
        this.checkToken(request);

        try {
            List<TpAttachinfoVO> list = fileAppService.uploadFile(files, jwtpid, referType, referId);
            if (null != list && list.size() == 1) {
                // 数据返回
                return JsonResponse.buildSuccess(list.get(0));
            } else {
                return JsonResponse.buildSuccess(list);
            }
        } catch (Exception e) {
            LOGGER.error("文件上传失败！", e);
            return JsonResponse.buildFailure("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * app附件下载接口，如果是pdf直接预览。需要加参数type=pdf
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        // 附件id
        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("附件id不能为空！");
        }

        // 校验token
        this.checkToken(request);

        try {
            fileAppService.download(id, request, response);
        } catch (Exception e) {
            LOGGER.error("下载文件失败！", e);
            this.res(response);
        }
    }

    /**
     * 根据文件相对路径下载文件
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    @RequestMapping(value = "/download-by-path")
    public void downloadByPath(HttpServletRequest request, HttpServletResponse response) {

        // 相对路径
        String relaPath = request.getParameter("relaPath");
        if (relaPath == null || relaPath.trim().isEmpty()) {
            throw new RuntimeException("附件相对路径不能为空！");
        }
        // 校验token
        this.checkToken(request);
        // 相对路径
        String downFileName = request.getParameter("downFileName");
        if (downFileName == null || downFileName.trim().isEmpty()) {
            if (relaPath.indexOf("/") >= 0) {
                downFileName = relaPath.substring(relaPath.lastIndexOf("/"), relaPath.lastIndexOf("."));
            } else {
                downFileName = relaPath;
            }
        }

        try {
            fileAppService.downloadByPath(relaPath, downFileName, request, response);
        } catch (Exception e) {
            LOGGER.error("下载文件失败！", e);
            this.res(response);
        }
    }

    /**
     * 文书生成查看
     *
     * @param id 附件ID
     * @param request HTTP请求
     * @param response HTTP响应
     * @return void
     */
    @RequestMapping(value = "/preview-pdf")
    public void previewPDF(String id, HttpServletRequest request, HttpServletResponse response) {
        // 校验token
        this.checkToken(request);

        try {
            fileAppService.download(id, request, response);
        } catch (Exception e) {
            LOGGER.error("下载文件失败！", e);
            this.res(response);
        }
    }

    /**
     * 根据业务id查询附件列表
     *
     * @param referId 关联业务主键
     * @param referType 关联业务类型
     * @param request HTTP请求
     * @return JsonResponse 响应结果
     */
    @RequestMapping(value = "/refer-id")
    @IgnoreAuthorization
    public JsonResponse getByReferId(String referId, String referType, HttpServletRequest request) {
        // 校验token
        this.checkToken(request);
        List<TpAttachinfoVO> list = fileAppService.getByReferId(referId, referType);
        return JsonResponse.buildSuccess(list);
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
     * @param request HTTP请求
     * @return JsonResponse 响应结果
     */
    @RequestMapping(value = "/delete")
    public JsonResponse delete(String referId, String attachId, String jwtpid, HttpServletRequest request) {

        // 如果 referId 不为空， 则根据 数据权限 判断是否能够删除，如果权限不对，则排查异常
        if (referId != null && !referId.trim().isEmpty()) {

            // 多个 签名逗号分隔开
            String sk = request.getHeader("sk");

            if (sk == null || sk.trim().isEmpty()) {
                throw new RuntimeException("删除附件时，横向鉴权验证失败！");
            }

            // 修改/删除/查看操作
            String sign = cn.hutool.crypto.digest.DigestUtil.md5Hex16(referId);
            if (!sign.equals(sk)) {
                throw new RuntimeException("附件删除失败，禁止横向越权操作！");
            }
        }

        int count = fileAppService.delete(referId, attachId, jwtpid);
        return JsonResponse.buildSuccess(count);
    }

    /**
     * 根据referId删除附件
     *
     * @param referId 关联业务主键
     * @param referType 关联业务类型
     * @param jwtpid JWT用户标识
     * @param request HTTP请求
     * @return JsonResponse 响应结果
     */
    @RequestMapping(value = "/delete-by-refer")
    public JsonResponse deleteByRefer(String referId, String referType, String jwtpid, HttpServletRequest request) {

        // 如果 referId 不为空， 则根据 数据权限 判断是否能够删除，如果权限不对，则排查异常
        if (referId != null && !referId.trim().isEmpty()) {

            // 多个 签名逗号分隔开
            String sk = request.getHeader("sk");

            if (sk == null || sk.trim().isEmpty()) {
                throw new RuntimeException("删除附件时，横向鉴权验证失败！");
            }

            // 修改/删除/查看操作
            String sign = cn.hutool.crypto.digest.DigestUtil.md5Hex16(referId);
            if (!sign.equals(sk)) {
                throw new RuntimeException("附件删除失败，禁止横向越权操作！");
            }
        }

        int count = fileAppService.deleteByRefer(referId, referType, jwtpid);
        return JsonResponse.buildSuccess(count);
    }

    /**
     * 校验token
     *
     * @param request HTTP请求
     * @return void
     */
    private void checkToken(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null || token.trim().isEmpty()) {
            token = request.getHeader("Token");
            if (token == null || token.trim().isEmpty()) {
                throw new RuntimeException("鉴权失败！");
            }
        }
        // 这里应该调用实际的token验证逻辑
        // TokenUtil.checkToken(token);
    }

    private void res(HttpServletResponse response) {
        // 重置response
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        JsonResponse jsonResponse = JsonResponse.buildFailure("下载文件失败！");
        try {
            response.getWriter().println(com.alibaba.fastjson.JSON.toJSONString(jsonResponse));
        } catch (java.io.IOException e) {
            throw new RuntimeException("下载文件失败!");
        }
    }
}