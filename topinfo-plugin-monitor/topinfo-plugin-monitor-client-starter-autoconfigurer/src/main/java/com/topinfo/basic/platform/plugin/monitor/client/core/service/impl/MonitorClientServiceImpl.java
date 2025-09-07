package com.topinfo.basic.platform.plugin.monitor.client.core.service.impl;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.common.util.CommonServerInfoUtil;
import com.topinfo.basic.platform.plugin.monitor.client.autoconfig.MonitorClientAutoConfigurationProperties;
import com.topinfo.basic.platform.plugin.monitor.client.constant.MonitorClientConstant;
import com.topinfo.basic.platform.plugin.monitor.client.core.service.MonitorClientService;
import com.topinfo.basic.platform.plugin.monitor.client.core.util.MacAddressUtil;
import com.topinfo.basic.platform.plugin.monitor.common.bean.ClientHeartbeatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;


/**
 * @ClassName: MonitorClientServiceImpl
 * @Description:
 * @Author 杨占锐
 * @Date 2024/11/14 17:44
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service
public class MonitorClientServiceImpl implements MonitorClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorClientServiceImpl.class);

    @Autowired
    private MonitorClientAutoConfigurationProperties properties;
    @Autowired
    private MonitorHealthServiceComposite healthServiceComposite;

    @Value("${spring.application.id:}")
    private String applicationId;
    @Value("${spring.application.name:}")
    private String applicationName;

    /**
     * 发送心跳
     *
     * @param
     * @return void
     * @author 杨占锐
     * @date 2024/11/14 18:24
     */
    @Override
    public void heartbeat() {

        // 构建心跳
        ClientHeartbeatInfo serverInfo = buildServerInfo();

        // 发送心跳
        if (null != serverInfo) {
            sendHeartbeat(serverInfo);
        }
    }

    /**
     * 发送心跳
     *
     * @param serverInfo
     * @return void
     * @author 杨占锐
     * @date 2024/11/14 18:24
     */
    private void sendHeartbeat(ClientHeartbeatInfo serverInfo) {

        try {
            // 发送心跳
            String response = HttpUtil.createPost(properties.getServerUrl())
                    .header("token", MonitorClientConstant.TOKEN)
                    .body(JSONObject.toJSONString(serverInfo))
                    .setConnectionTimeout(properties.getConnectionTimeout())
                    .setReadTimeout(properties.getReadTimeout())
                    .execute()
                    .body();
            if (StrUtil.isNotBlank(response)) {
                LOGGER.info("http发送心跳成功, response: {}", response);
            } else {
                LOGGER.error("http发送心跳失败, response: {}", response);
            }
        } catch (Exception e) {
            LOGGER.error("http发送心跳失败，错误：{}", ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * 构建心跳信息
     *
     * @return com.topinfo.basic.platform.plugin.monitor.client.core.bean.ClientServerInfo
     * @author 杨占锐
     * @date 2024/11/14 18:24
     */
    public ClientHeartbeatInfo buildServerInfo() {

        try {
            ClientHeartbeatInfo serverInfo = new ClientHeartbeatInfo();
            // 服务器信息
            CommonServerInfoUtil.buildServerInfo(serverInfo);
            // mac地址
            serverInfo.setMacAddr(MacAddressUtil.getMacAddr());
            // 部署路径
            serverInfo.setAbsolutePath(this.getAbsolutePath());
            // 客户端id
            serverInfo.setClientId(properties.getClientId());
            // 系统描述
            serverInfo.setSystemDesc(properties.getSystemDesc());
            // applicationId
            serverInfo.setApplicationId(applicationId);
            // applicationName
            serverInfo.setApplicationName(applicationName);
            // 不健康的服务
            serverInfo.setNotHealthServer(healthServiceComposite.getNotHealthServer());
            return serverInfo;
        } catch (Exception e) {
            LOGGER.error("构建心跳信息失败，错误：{}，properties：{}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(properties));
        }

        return null;
    }

    /**
     * 获取项目路径
     *
     * @return java.lang.String
     * @author 杨占锐
     * @date 2024/12/18 14:37
     */
    private String getAbsolutePath() {
        URL resource = ClassLoaderUtil.getClassLoader().getResource("");
        if (null == resource) {
            return System.getProperty("user.dir");
        }
        String path = resource.getPath();
        String webInfo = "WEB-INF";
        if (path.contains(webInfo)) {
            path = path.substring(0, path.indexOf(webInfo));
        }
        String classes = "classes";
        if (path.contains(classes)) {
            path = path.substring(0, path.indexOf(classes));
        }
        return path;
    }

}
