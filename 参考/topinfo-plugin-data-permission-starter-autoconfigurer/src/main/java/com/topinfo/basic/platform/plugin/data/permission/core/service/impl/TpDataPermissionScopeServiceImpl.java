package com.topinfo.basic.platform.plugin.data.permission.core.service.impl;
import cn.hutool.core.collection.CollectionUtil;
import com.topinfo.basic.platform.plugin.data.permission.constant.DataPermissionConstant;
import com.topinfo.basic.platform.plugin.data.permission.core.service.TpDataPermissionDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topinfo.basic.platform.common.util.CommonDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.bean.BeanUtil;
import com.topinfo.basic.platform.common.util.SnowflakeIdUtil;
import com.topinfo.basic.platform.common.constant.TpConstant;
import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.topinfo.basic.platform.plugin.data.permission.core.mapper.TpDataPermissionScopeMapper;
import com.topinfo.basic.platform.plugin.data.permission.core.bean.entity.TpDataPermissionScope;
import com.topinfo.basic.platform.plugin.data.permission.core.bean.vo.TpDataPermissionScopeVO;
import com.topinfo.basic.platform.plugin.data.permission.core.bean.query.TpDataPermissionScopeQuery;
import com.topinfo.basic.platform.plugin.data.permission.core.service.TpDataPermissionScopeService;
import com.topinfo.basic.platform.core.bean.TopinfoRuntimeException;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

/**
 * @ClassName: TpDataPermissionScopeServiceImpl
 * @Description: 数据权限范围表
 * @Author yangzr
 * @Date 2024-12-03 17:33:54
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service
public class TpDataPermissionScopeServiceImpl implements TpDataPermissionScopeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TpDataPermissionScopeServiceImpl.class);

    @Autowired
    private  TpDataPermissionScopeMapper  tpDataPermissionScopeMapper;

    @Autowired
    private TpDataPermissionDeptService tpDataPermissionDeptService;

    /**
     * 保存
     * <per>
     *     1. 根据personId和deptId查询数据权限，查询不到则新增数据权限范围，查询到则修改数据权限范围
     *     2. 保存指定的部门
     * </per>
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(TpDataPermissionScopeVO vo, String jwtpid, String tenantId) {

        // 先查询
        TpDataPermissionScopeVO view = tpDataPermissionScopeMapper.view(vo.getPersonId(), vo.getDeptId());
        if (null != view) {
            vo.setPermId(view.getPermId());
            // 存在则修改
            this.update(vo, jwtpid, tenantId);

            // 返回主键
            return view.getPermId();
        }

        try {

            // 构建新增的对象
            TpDataPermissionScope bean = this.buildAddBean(vo, jwtpid, tenantId);

            // 新增数据权限
            tpDataPermissionScopeMapper.add(bean);

            // 保存指定的部门
            tpDataPermissionDeptService.save(bean.getPermId(), vo.getDeptIds(), tenantId);

            return bean.getPermId();
        } catch (Exception e) {
            LOGGER.error("新增失败！vo: {}, e: {}", JSONObject.toJSONString(vo), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "新增失败！");
        }
    }


    /**
     * 修改
     *
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    public int update(TpDataPermissionScopeVO vo, String jwtpid, String tenantId) {

        TpDataPermissionScope bean = new TpDataPermissionScope();
        // 转换成数据库对象
        BeanUtil.copyProperties(vo, bean);
        bean.setUpdator(jwtpid);
        bean.setUpdateTime(CommonDateUtil.now());

        try {
            // 更新数据权限
            int count = tpDataPermissionScopeMapper.update(bean);
            // 保存指定的部门
            tpDataPermissionDeptService.save(bean.getPermId(), vo.getDeptIds(), tenantId);
            return count;
        } catch (Exception e) {
            LOGGER.error("修改失败！vo: {}, e: {}", JSONObject.toJSONString(vo), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "修改失败！");
        }
    }

    /**
     * 查看数据权限详情
     * <per>
     *     1. 根据人员id和部门id查询数据权限范围
     *     2. 如果范围是指定部门，则再查询指定部门id
     * </per>
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @Override
    public TpDataPermissionScopeVO view(String personId, String deptId) {
        // 查询
        TpDataPermissionScopeVO view = tpDataPermissionScopeMapper.view(personId, deptId);

        if (null != view) {
            // 指定部门权限时，需查询指定的部门
            if (DataPermissionConstant.ASSIGN_SCOPE_SET.contains(view.getDataScope())) {
                List<String> deptList = tpDataPermissionDeptService.getById(view.getPermId());
                if (CollectionUtil.isEmpty(deptList)) {
                    throw new TopinfoRuntimeException(-1, "未查询到指定部门信息");
                }
                view.setDeptList(deptList);
            }
        }

        return view;
    }

    /**
     * 查看数据权限详情 - 并返回部门名称，用于前端页面回显
     * <per>
     *     1. 根据人员id和部门id查询数据权限范围
     *     2. 如果范围是指定部门，则再查询指定部门id
     *     3. 如果有指定部门，则查询部门名称
     * </per>
     * @Author yangzr
     * @Date 2024-12-03 17:33:54
     */
    @Override
    public TpDataPermissionScopeVO viewWithDeptName(String personId, String deptId) {
        TpDataPermissionScopeVO view = this.view(personId, deptId);
        if (null != view) {
            view.setDeptNodes(tpDataPermissionDeptService.listDeptNodes(view.getDeptList()));
        }
        return view;
    }

    /**
     * 根据部门id，查询部门层级编码
     *
     * @param deptId
     * @return java.lang.String
     * @author 杨占锐
     * @date 2024/12/4 15:52
     */
    @Override
    public String getDeptLevelcode(String deptId) {
        return tpDataPermissionScopeMapper.getDeptLevelcode(deptId);
    }

    /**
     * 构建新增时的bean
     *
     * @param vo
     * @param jwtpid
     * @return com.topinfo.basic.platform.plugin.data.permission.core.bean.entity.TpDataPermissionScope
     * @author 杨占锐
     * @date 2024/12/3 19:29
     */
    private TpDataPermissionScope buildAddBean(TpDataPermissionScopeVO vo, String jwtpid, String tenantId) {

        // 生成主键
        String id = SnowflakeIdUtil.nextIdStr();
        String now = CommonDateUtil.now();
        TpDataPermissionScope bean = new TpDataPermissionScope();
        // 转换成数据库对象
        BeanUtil.copyProperties(vo, bean);
        // 设置主键id
        bean.setPermId(id);
        // 创建人
        bean.setCreator(jwtpid);
        // 创建时间
        bean.setCreateTime(now);
        // 修改人
        bean.setUpdator(jwtpid);
        // 修改时间
        bean.setUpdateTime(now);
        bean.setTenantId(tenantId);
        return bean;
    }

}