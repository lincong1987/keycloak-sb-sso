package com.topinfo.basic.platform.plugin.monitor.server.bean;

import com.topinfo.basic.platform.common.server.bean.ServerInfo;

import java.util.List;

/**
 * @ClassName: ClientServerInfo
 * @Description:
 * @Author 杨占锐
 * @Date 2024/11/14 17:45
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class ClientHeartbeatInfo extends ServerInfo {

    /**
     * 本机mac地址
     */
    private String macAddr;

    /**
     * 本系统部署的绝对路径
     */
    private String absolutePath;

    /**
     * 本系统唯一id
     */
    private String clientId;

    /**
     * 系统描述
     */
    private String systemDesc;

    /**
     * 不健康的服务
     */
    private List<String> notHealthServer;


    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }

    public List<String> getNotHealthServer() {
        return notHealthServer;
    }

    public void setNotHealthServer(List<String> notHealthServer) {
        this.notHealthServer = notHealthServer;
    }
}
