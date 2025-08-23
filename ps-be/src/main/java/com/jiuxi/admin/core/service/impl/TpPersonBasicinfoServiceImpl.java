package com.jiuxi.admin.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiuxi.admin.core.bean.entity.TpAccount;
import com.jiuxi.admin.core.bean.entity.TpPersonBasicinfo;
import com.jiuxi.admin.core.bean.entity.TpPersonDept;
import com.jiuxi.admin.core.bean.entity.TpPersonExinfo;
import com.jiuxi.admin.core.bean.entity.TpPersonRole;
import com.jiuxi.admin.core.bean.query.TpPersonBasicQuery;
import com.jiuxi.admin.core.bean.vo.TpAccountVO;
import com.jiuxi.admin.core.bean.vo.TpDeptBasicinfoVO;
import com.jiuxi.admin.core.bean.vo.TpPersonBasicinfoVO;
import com.jiuxi.admin.core.bean.vo.TpPersonDeptVO;
import com.jiuxi.admin.core.bean.vo.TpPersonExinfoVO;
import com.jiuxi.admin.core.bean.vo.TpPersonRoleVO;
import com.jiuxi.admin.core.event.TpPersonBasicinfoEvent;
import com.jiuxi.admin.core.listener.service.TpPersonBasicinfoEventService;
import com.jiuxi.admin.core.mapper.TpAccountMapper;
import com.jiuxi.admin.core.mapper.TpDeptBasicinfoMapper;
import com.jiuxi.admin.core.mapper.TpPersonBasicinfoMapper;
import com.jiuxi.admin.core.mapper.TpPersonDeptMapper;
import com.jiuxi.admin.core.mapper.TpPersonExinfoMapper;
import com.jiuxi.admin.core.mapper.TpPersonRoleMapper;
import com.jiuxi.admin.core.service.PersonAccountService;
import com.jiuxi.admin.core.service.TpAccountService;
import com.jiuxi.admin.core.service.TpAttachinfoService;
import com.jiuxi.admin.core.service.TpPersonBasicinfoService;
import com.jiuxi.common.bean.SessionVO;
import com.jiuxi.common.exception.ExceptionUtils;
import com.jiuxi.common.util.CommonDateUtil;
import com.jiuxi.common.util.CommonUniqueIndexUtil;
import com.jiuxi.common.util.SnowflakeIdUtil;
import com.jiuxi.core.bean.TopinfoRuntimeException;
import com.jiuxi.security.core.holder.SessionHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
// import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: TpPersonBasicinfoServiceImpl
 * @Description: 人员基本信息表
 * @Author pand
 * @Date 2020-11-18 11:05:18
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Service("tpPersonBasicinfoService")
public class TpPersonBasicinfoServiceImpl implements TpPersonBasicinfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TpPersonBasicinfoServiceImpl.class);

    @Autowired
    private TpPersonBasicinfoMapper tpPersonBasicinfoMapper;

    @Autowired
    private PersonAccountService personAccountService;

    @Autowired
    private TpPersonExinfoMapper tpPersonExinfoMapper;

    @Autowired
    private TpPersonDeptMapper tpPersonDeptMapper;

    @Autowired
    private TpPersonRoleMapper tpPersonRoleMapper;

    @Autowired
    private TpAccountMapper tpAccountMapper;

    @Autowired
    private TpAccountService tpAccountService;

    @Autowired
    private TpDeptBasicinfoMapper tpDeptBasicinfoMapper;

    @Autowired
    private TpAttachinfoService tpAttachinfoService;

    /**
     * 上下文对象
     */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private TpPersonBasicinfoEventService tpPersonBasicinfoEventService;

    /**
     * 用户信息分页查询
     *
     * @param query:
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.jiuxi.admin.core.bean.vo.TpPersonBasicinfoVO>
     * @author pand
     * @date 2020-12-04 13:38
     */
    @Override
    public IPage<TpPersonBasicinfoVO> queryPage(TpPersonBasicQuery query) {
        try {
            if (StrUtil.isBlank(query.getDeptId())) {
                throw new TopinfoRuntimeException(500, "请选择部门查询数据!");
            }

            Integer pageNum = Optional.ofNullable(query.getCurrent()).orElse(1);
            Integer pageSize = Optional.ofNullable(query.getSize()).orElse(10);

            Page<TpPersonBasicinfoVO> page = new Page<>(pageNum, pageSize);
            IPage<TpPersonBasicinfoVO> iPage;
            if (query.getLevelFlag() == 0) {
                // 0：本级
                iPage = tpPersonBasicinfoMapper.getPage(page, query);
            } else {
                // 1：本级及下级
                String deptId = query.getDeptId();
                // 先根据deptId查询出部门信息，设置查询的层级code，根据层级code模糊查询
                TpDeptBasicinfoVO deptBasicinfoVO = tpDeptBasicinfoMapper.view(deptId);
                query.setDeptId("");
                query.setDeptLevelcode(deptBasicinfoVO.getDeptLevelcode());

                iPage = tpPersonBasicinfoMapper.getPage(page, query);
            }

            List<TpPersonBasicinfoVO> list = iPage.getRecords();
            if (!list.isEmpty()){
                list.forEach(vo -> {
                    TpDeptBasicinfoVO tpDeptBasicinfoVO = tpDeptBasicinfoMapper.selectDeptById(vo.getAscnId());
                    vo.setAscnName(tpDeptBasicinfoVO.getDeptFullName());
                });
            }

            return iPage;
        } catch (Exception e) {
            LOGGER.error("用户列表信息失败！query:{}, e: {}", JSONObject.toJSONString(query), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, StrUtil.isBlank(e.getMessage()) ? "用户列表信息失败！" : e.getMessage());
        }
    }

    /**
     * 新增用户基本信息，维护用户部门关系表，默认主部门。
     *
     * @param vo:       部门信息
     * @param pid:      当前登陆人id
     * @param category: 0政府 1企业 2其他
     * @author pand
     * @date 2020-11-23 15:37
     */
    @Override
    @Transactional(rollbackFor = TopinfoRuntimeException.class)
    public TpPersonBasicinfoVO add(TpPersonBasicinfoVO vo, String pid, int category) {
        // 校验手机号唯一
        if (StrUtil.isNotBlank(vo.getPhone()) && personAccountService.selectByPhone(vo.getPhone())) {
            LOGGER.error("手机号已被占用phone = {}", vo.getPhone());
            throw new TopinfoRuntimeException(-1, "手机号已被占用");
        }

        try {

            String personId = SnowflakeIdUtil.nextIdStr();
            String createTime = CommonDateUtil.now();

            vo.setPersonId(personId);
            TpPersonBasicinfo bean = new TpPersonBasicinfo();
            // 转换成数据库对象
            BeanUtil.copyProperties(vo, bean);
            bean.setCreator(pid);
            bean.setCreateTime(createTime);
            bean.setUpdator(pid);
            bean.setUpdateTime(createTime);

            // 设置人员类别 政府人员or企业人员or其他
            bean.setCategory(category);
            TpPersonDept personDeptBean = new TpPersonDept();
            personDeptBean.setDeptId(vo.getDeptId());
            personDeptBean.setPersonId(personId);
            personDeptBean.setDefaultDept(1);

            // 附件绑定
            tpAttachinfoService.band(vo.getFile(), personId, createTime);

            // 新增人员
            int count = tpPersonBasicinfoMapper.save(bean);
            // 维护部门，人员关系
            tpPersonDeptMapper.save(personDeptBean);

            // 发布事件，推送人员给第三方系统
            if (null != tpPersonBasicinfoEventService) {
                applicationContext.publishEvent(new TpPersonBasicinfoEvent("人员信息新增同步监听", tpPersonBasicinfoEventService, bean, 1));
            }

            return vo;
        } catch (Exception e) {
            LOGGER.error("新增用户基本信息失败！vo:{}, e: {}", JSONObject.toJSONString(vo), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "新增用户基本信息失败！");
        }
    }

    /**
     * 添加人员扩展信息
     *
     * @param vo: 扩展信息
     * @author pand
     * @date 2020-11-23 15:37
     */
    @Override
    public TpPersonExinfoVO expAdd(TpPersonExinfoVO vo) {
        try {
            TpPersonExinfo bean = new TpPersonExinfo();
            // 转换成数据库对象
            BeanUtil.copyProperties(vo, bean);
            int count = tpPersonExinfoMapper.count(vo.getPersonId());
            if (count == 0) {
                tpPersonExinfoMapper.save(bean);
            } else {
                tpPersonExinfoMapper.update(bean);
            }

            return vo;
        } catch (Exception e) {
            LOGGER.error("新增用户扩展信息失败！vo:{}, e: {}", JSONObject.toJSONString(vo), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "新增用户扩展信息失败！");
        }
    }

    /**
     * 查看用户基本信息
     *
     * @param personId: 用户id
     * @return com.jiuxi.admin.core.bean.vo.TpDeptBasicinfoVO
     * @author pand
     * @date 2020-11-23 16:55
     */
    @Override
    public TpPersonBasicinfoVO view(String personId, String deptId) {
        try {
            TpPersonBasicinfoVO vo = tpPersonBasicinfoMapper.view(personId);

            // 根据用户id查询所在部门
            List<TpDeptBasicinfoVO> list = tpDeptBasicinfoMapper.personDepts(personId);
            StringBuffer deptIds = new StringBuffer(100);
            StringBuffer deptNames = new StringBuffer(100);
            if (!list.isEmpty()) {
                for (TpDeptBasicinfoVO bean : list) {
                    if (StrUtil.equals(bean.getDeptId(), deptId)) {
                        vo.setDeptId(deptId);
                        vo.setDeptFullName(bean.getDeptFullName());
                        vo.setDefaultDept(bean.getDefaultDept());
                    }
                    if (bean.getDefaultDept() == 0) {
                        deptIds.append(bean.getDeptId()).append(",");
                        deptNames.append(bean.getDeptFullName()).append(",");
                    }
                }
            }
            vo.setDeptIds(StrUtil.isNotBlank(deptIds.toString()) ? deptIds.toString().substring(0, deptIds.length() - 1) : "");
            vo.setDeptFullNames(StrUtil.isNotBlank(deptNames.toString()) ? deptNames.toString().substring(0, deptNames.length() - 1) : "");

            return vo;
        } catch (Exception e) {
            LOGGER.error("查看用户基本信息失败！personId:{}, deptId:{}, e: {}", personId, deptId, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "查看用户基本信息失败！");
        }
    }

    /**
     * 查看用户扩展信息
     *
     * @param personId: 用户id
     * @return com.jiuxi.admin.core.bean.vo.TpDeptExinfoVO
     * @author pand
     * @date 2020-11-23 17:13
     */
    @Override
    public TpPersonExinfoVO expView(String personId) {
        try {
            TpPersonExinfoVO vo = tpPersonExinfoMapper.view(personId);
            return vo;
        } catch (Exception e) {
            LOGGER.error("查看用户扩展信息失败！personId:{}, e: {}", personId, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "查看用户扩展信息失败！");
        }
    }

    /**
     * 更新用户基本信息
     * 1、修改用户信息，如果手机号码有修改，需要同步修改账号的手机号码，及用户表手机号码与账号表手机号码需要保持一致。
     * 2、如果人员主部门变更，需要将原来人员的主部门删除，重新绑定
     * 3、如果主部门发生变更，将人员部门角色删除，如果主部门没有变更，人员部门角色不删除
     *
     * @param vo:
     * @return int
     * @author pand
     * @date 2020-11-24 20:13
     */
    @Override
    @Transactional(rollbackFor = TopinfoRuntimeException.class)
    @CacheEvict(cacheNames = "platform.{TpPersonBasicinfoService}$[86400]", allEntries = true)
    public int update(TpPersonBasicinfoVO vo, String pid) {
        String phone = vo.getPhone();
        // 人员id
        String personId = vo.getPersonId();
        // 新的主部门id
        String deptId = vo.getDeptId();
        String updateTime = CommonDateUtil.now();

        // 除了当前登陆人，有其他人员手机号等于前端传过来的手机号，说明手机号已被别人占用。
        if (StrUtil.isNotBlank(phone)){
            TpPersonBasicinfo personBasicinfo = tpPersonBasicinfoMapper.selectByPhoneAndPersonId(phone, personId);
            if (null != personBasicinfo) {
                LOGGER.error("手机号已被占用phone : {}, personId: {}", phone, personId);
                throw new TopinfoRuntimeException(-1, "手机号已被占用");
            }
        }

        // 维护人员部门角色关系，如果主部门没有变化，人员部门角色不用删除，所以根据人员id先查询主部门信息
        List<TpPersonDeptVO> tpPersonDeptVOS = tpPersonDeptMapper.select("", personId, 1);
        if (null == tpPersonDeptVOS || tpPersonDeptVOS.size() != 1) {
            LOGGER.error("修改用户的主部门有问题:tpPersonDeptVOS = {}", JSONUtil.toJsonStr(tpPersonDeptVOS));
            throw new TopinfoRuntimeException(-1, "修改用户的主部门有问题");
        }
        // 获取未变化前的主部门id
        String oldDeptId = tpPersonDeptVOS.get(0).getDeptId();

        TpPersonBasicinfo bean = new TpPersonBasicinfo();
        // 转换成数据库对象
        BeanUtil.copyProperties(vo, bean);
        bean.setUpdator(pid);
        bean.setUpdateTime(updateTime);

        try {
            if (StrUtil.isNotBlank(deptId)){
                // 如果主部门发生变化，删除人员部门角色关联关系，同时更新人员表的单位id字段ASCN_ID
                if (!StrUtil.equals(oldDeptId, deptId)) {
                    List<String> roleDepts = new ArrayList<>();
                    roleDepts.add(oldDeptId);
                    LOGGER.warn("操作人:{}, 被操作人: {} 的部门发生变化，删除部门: {} 关联关系.",pid, personId, deptId);
                    tpPersonRoleMapper.deleteByPersonIdAndDeptIds(personId, roleDepts);
                    // 查询新部门的单位id,并赋值
                    TpDeptBasicinfoVO tpDeptBasicinfoVO = tpDeptBasicinfoMapper.selectDeptById(deptId);
                    bean.setAscnId(tpDeptBasicinfoVO.getAscnId());

                    // 维护人员部门关系
                    TpPersonDept personDeptBean = new TpPersonDept();
                    personDeptBean.setPersonId(personId);
                    personDeptBean.setDeptId(deptId);
                    personDeptBean.setDefaultDept(1);
                    // 更新主部门信息
                    tpPersonDeptMapper.update(personDeptBean);
                }
            }
            // 如果手机号有更改，需要将账号表的手机号更新掉
            TpAccountVO tpAccountVO = tpAccountService.accountView(personId);
            if (null != tpAccountVO && !StrUtil.equals(tpAccountVO.getPhone(), phone)) {
                // 更新该人员账号的手机号
                TpAccount tpAccount = new TpAccount();
                tpAccount.setPersonId(personId);
                tpAccount.setPhone(vo.getPhone());
                tpAccountMapper.updateByPersonId(tpAccount);
            }

            // 附件绑定
            tpAttachinfoService.band(vo.getFile(), personId, updateTime);

            // 更新人员基本信息
            int count = tpPersonBasicinfoMapper.update(bean);

            // 发布事件，推送人员给第三方系统
            if (null != tpPersonBasicinfoEventService) {
                applicationContext.publishEvent(new TpPersonBasicinfoEvent("人员信息修改同步监听", tpPersonBasicinfoEventService, bean, 2));
            }

            return count;
        } catch (Exception e) {
            // 事务回滚
            LOGGER.error("用户基本信息修改失败！vo:{}, e: {}", JSONObject.toJSONString(vo), ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "用户基本信息修改失败！");
        }
    }

    /**
     * 批量删除用户信息，只删除以当前部门为主部门的人员和账号信息，如果是兼职部门，只删除关联关系
     * 1、删除用户信息，用户账户设置为无效
     * <p>
     * 3、删除用户信息，是逻辑删除，设置为无效
     *
     * @param personIds:
     * @return void
     * @author pand
     * @date 2020-11-25 11:07
     */
    @Override
    @Transactional(rollbackFor = TopinfoRuntimeException.class)
    @CacheEvict(cacheNames = "platform.{TpPersonBasicinfoService}$[86400]", allEntries = true)
    public void deletes(String deptIds, String personIds, String pid) {

        // 判断删除的人员是否包含自己, 可以删除兼职部门中的自己
        if (personIds.contains(pid)) {
            SessionVO sessionVO = SessionHolder.get();
            if (deptIds.contains(sessionVO.getDeptId())){
                throw new TopinfoRuntimeException(-1, "不能删除自己！");
            }
        }

        String[] personIdArr = StringUtils.split(personIds, ",");
        String[] deptIdArr = StringUtils.split(deptIds, ",");
        String updateTime = CommonDateUtil.now();

        TpPersonBasicinfo tpPersonBasicinfo = new TpPersonBasicinfo();

        for (int i = 0; i < personIdArr.length; i++) {
            try {
                String personId = personIdArr[i];
                String deptId = deptIdArr[i];

                int defaultDept = tpPersonDeptMapper.selectByDeptIdAndPersonId(deptId, personId);
                if (defaultDept == 1) {
                    // 1. 用户账户设置为无效
                    tpAccountService.deleteByPersonId(personId);

                    // 2. 用户信息逻辑删除
                    tpPersonBasicinfo.setPersonId(personId);
                    tpPersonBasicinfo.setUpdator(pid);
                    tpPersonBasicinfo.setActived(0);
                    tpPersonBasicinfo.setUpdateTime(updateTime);
                    tpPersonBasicinfo.setPhone(CommonUniqueIndexUtil.addDeleteTime(tpPersonBasicinfo.getPhone()));
                    tpPersonBasicinfoMapper.deleteByPersonId(tpPersonBasicinfo);
                }

                // 3. 用户，部门关联关系删除
                tpPersonDeptMapper.deleteByDeptIdPersonId(deptId, personId);

                // 4. 用户，角色关联关系删除
                LOGGER.warn("操作人:{}, 被操作人: {} , 部门：{}, 删除用户信息，删除人员部门角色关联关系.",pid, personId, deptId);
                tpPersonRoleMapper.delete(deptId, personId);


                // 发布事件，推送人员给第三方系统
                if (null != tpPersonBasicinfoEventService) {
                    TpPersonBasicinfo bean = new TpPersonBasicinfo();
                    bean.setPersonId(personId);
                    applicationContext.publishEvent(new TpPersonBasicinfoEvent("人员信息删除同步监听", tpPersonBasicinfoEventService, bean, 3));
                }
            } catch (Exception e) {
                // 事务回滚
                LOGGER.error("删除用户信息失败！deptIds:{}, personIds:{}, e: {}", deptIds, personIds, ExceptionUtils.getStackTrace(e));
                throw new TopinfoRuntimeException(-1, "删除用户信息失败！");
            }
        }
    }

    /**
     * 人员兼职/解除兼职，人员挂职到兼职部门/人员取消兼职部门
     *
     * @param personId: 人员id
     * @param deptIds:  部门id
     * @return int
     * @author pand
     * @date 2020-11-25 11:19
     */
    @Override
    @Transactional(rollbackFor = TopinfoRuntimeException.class)
    public int parttime(String personId, String deptIds) {
        try {
            // 兼职部门标识
            int parttimeJobDept = 0;
            int defaultDept = 1;
            // 前端传回部门ids
            String[] deptIdArr = StringUtils.split(deptIds, ",");
            int len = deptIdArr.length;
            // 查询tp_person_dept,查处所有兼职部门
            List<TpPersonDeptVO> parttimeDept = tpPersonDeptMapper.select("", personId, 0);
            // 所有兼职部门的ids
            List<String> oldDeptIds = parttimeDept.stream().map(TpPersonDeptVO::getDeptId).collect(Collectors.toList());
            if (len == 0) {
                // 兼职部门不为空时执行删除
                if (CollectionUtil.isNotEmpty(oldDeptIds)) {
                    // 清空人员部门关联数据以及清空人员角色关联数据
                    // 1、根据人员id删除兼职部门
                    tpPersonDeptMapper.deleteByPersonId(personId, parttimeJobDept);
                    // 2、根据人员id和部门id删除兼职部门
                    LOGGER.warn("被操作人: {} , 部门：{}, 兼职/解除兼职，删除人员部门角色关联关系1.",personId, parttimeJobDept);
                    tpPersonRoleMapper.deleteByPersonIdAndDeptIds(personId, oldDeptIds);
                }
            } else {
                List<String> newDeptIds = Arrays.asList(deptIdArr);
                // 重新绑定人员部门关联关系和人员角色关联关系
                List<String> oldDept = new ArrayList<>();
                for (String oldDeptId : oldDeptIds) {
                    if (!newDeptIds.contains(oldDeptId)) {
                        // 删除oldDeptId
                        // 1、根据人员id删除兼职部门
                        tpPersonDeptMapper.delete(oldDeptId, personId, parttimeJobDept);
                        // 2、根据人员id和部门id删除兼职部门
                        oldDept.add(oldDeptId);
                    }
                }
                if (oldDept.size() > 0) {
                    LOGGER.warn("被操作人: {} , 部门：{}, 兼职/解除兼职，删除人员部门角色关联关系2.",personId, parttimeJobDept);
                    tpPersonRoleMapper.deleteByPersonIdAndDeptIds(personId, oldDept);
                }

                // 查询主部门ID，如果传过来的兼职部门包含主部门，不做任何操作
                List<TpPersonDeptVO> list = tpPersonDeptMapper.select("", personId, defaultDept);
                if (list.size() != 1) {
                    throw new TopinfoRuntimeException(-1, "用户默认所在部门为空！");
                }

                for (String newDeptId : newDeptIds) {
                    if (!StrUtil.equals(newDeptId, list.get(0).getDeptId()) && !oldDeptIds.contains(newDeptId)) {
                        // 新增newDeptId
                        // 1、新增人员部门关系数据
                        TpPersonDept bean = new TpPersonDept();
                        bean.setDeptId(newDeptId);
                        bean.setPersonId(personId);
                        bean.setDefaultDept(parttimeJobDept);
                        tpPersonDeptMapper.save(bean);
                    }
                }
            }

            return len;
        } catch (Exception e) {
            // 事务回滚
            LOGGER.error("用户兼职部门失败！ personId:{}, deptIds:{}, e: {}", personId, deptIds, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "用户兼职部门失败！");
        }
    }

    /**
     * 查询用户所拥有的角色
     *
     * @param personId:
     * @return java.util.List<com.jiuxi.admin.core.bean.vo.TpPersonRoleVO>
     * @author pand
     * @date 2020-11-25 13:43
     */
    @Override
    public List<TpPersonRoleVO> personRoles(String deptId, String personId) {
        try {
            List<TpPersonRoleVO> list = tpPersonRoleMapper.selectByPersonIdAndDeptId(deptId, personId);
            return list;
        } catch (Exception e) {
            LOGGER.error("查询用户角色失败！ deptId:{}, personId:{}, e: {}", deptId, personId, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "查询用户角色失败！");
        }
    }

    /**
     * 用户授权
     *
     * @param personId: 用户id
     * @param deptId:   部门id
     * @param roleIds:  角色id
     * @return void
     * @author pand
     * @date 2020-11-25 14:00
     */
    @Override
    @Transactional(rollbackFor = TopinfoRuntimeException.class)
    public int auth(String personId, String deptId, String roleIds) {

        Validate.notBlank(personId, "人员id不能为空！");
        Validate.notBlank(deptId, "部门id不能为空！");

        try {

            // 先删除拥有角色

            // 人员id和部门id不能为空！
            if (StrUtil.isBlank(personId) || StrUtil.isBlank(deptId)) {
                throw new TopinfoRuntimeException(-1, "人员id和部门id不能为空！");
            }
            LOGGER.warn("被操作人: {} , 部门：{}, 用户授权，删除人员部门角色关联关系.",personId, deptId);
            tpPersonRoleMapper.delete(deptId, personId);

            String[] roleIdArr = StringUtils.split(roleIds, ",");
            int len = roleIdArr.length;

            for (int i = 0; i < len; i++) {
                TpPersonRole bean = new TpPersonRole();
                bean.setPersonId(personId);
                bean.setDeptId(deptId);
                bean.setRoleId(roleIdArr[i]);
                tpPersonRoleMapper.save(bean);
            }

            return len;
        } catch (Exception e) {
            // 事务回滚
            LOGGER.error("用户授权失败！deptId:{}, personId:{}, roleIds:{}, e: {}", deptId, personId, roleIds, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "用户授权失败！");
        }
    }

    /**
     * 根据人员id 获取人员基本信息
     * @author 杨攀
     * @date 2023/11/15 16:28
     * @param personId
     * @return com.jiuxi.admin.core.bean.vo.TpPersonBasicinfoVO
     */
    @Override
    @Cacheable(cacheNames = "platform.{TpPersonBasicinfoService}$[86400]", key = "#root.methodName + ':' + #personId")
    public TpPersonBasicinfoVO getPersonBasicinfo(String personId) {
        return  tpPersonBasicinfoMapper.view(personId);
    }

    @Override
    public TpPersonBasicinfoVO getBaseInfoByIdCard(String idcard) {
        List<TpPersonBasicinfoVO> list = tpPersonBasicinfoMapper.getBaseInfoByIdCard(idcard);

        if (list.size() > 1){
            LOGGER.error("根据身份证号，查询到多条数据。idcard: {}", idcard);
            throw new TopinfoRuntimeException(-1, "根据身份证号，查询到多条数据");
        }
        if (list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    /**
     * 校验手机号是否存在
     *
     * @param phone     手机号
     * @param personId  人员id（修改校验时必填）
     * @return boolean 存在返回 true, 不存在返回 false
     * @author 杨占锐
     * @date 2024/5/30 15:49
     */
    @Override
    public boolean existsPhone(String phone, String personId) {
        Validate.notBlank(phone, "手机号不能为空！");
        TpPersonBasicinfo tpPersonBasicinfo = tpPersonBasicinfoMapper.selectByPhoneAndPersonId(phone, personId);

        return tpPersonBasicinfo != null;
    }

    /**
     * 查询出在该单位所有部门下的所有主部门关联关系的人员id
     *
     * @param ascnId 单位id
     * @return java.util.List<java.lang.String>
     * @author 杨占锐
     * @date 2024/6/4 19:12
     */
    @Override
    public List<String> selectPersonIdByAscnId(String ascnId) {
        return tpPersonBasicinfoMapper.selectPersonIdByAscnId(ascnId);
    }

    /**
     * 根据企业id删除人员
     *
     * @param ascnId 单位id
     * @param jwtpid 操作人id
     * @return void
     * @author 杨占锐
     * @date 2024/6/4 19:24
     */
    @Override
    @CacheEvict(cacheNames = "platform.{TpPersonBasicinfoService}$[86400]", allEntries = true)
    public void deletePersonDeptByAscnId(String ascnId, String jwtpid) {
        tpPersonBasicinfoMapper.deletePersonDeptByAscnId(ascnId, jwtpid, CommonDateUtil.now());
    }

    /**
     * 修改手机号
     *
     * @param personId 人员id
     * @param phone    手机号
     * @param jwtpid   操作人id
     * @return int
     * @author 杨占锐
     * @date 2024/5/30 17:10
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "platform.{TpPersonBasicinfoService}$[86400]", allEntries = true)
    public void updatePhone(String personId, String phone, String jwtpid) {

        // 查询手机号是否重复
        TpPersonBasicinfo tpPersonBasicinfo = tpPersonBasicinfoMapper.selectByPhoneAndPersonId(phone, personId);
        if (tpPersonBasicinfo != null) {
            LOGGER.error("手机号已被占用phone : {}, personId: {}", phone, personId);
            throw new TopinfoRuntimeException(-1, "手机号已被占用");
        }

        try {
            // 更新人员表手机号
            TpPersonBasicinfo bean = new TpPersonBasicinfo();
            bean.setPersonId(personId);
            bean.setPhone(phone);
            bean.setUpdateTime(CommonDateUtil.now());
            bean.setUpdator(jwtpid);
            tpPersonBasicinfoMapper.update(bean);

            // 更新账号表手机号
            personAccountService.updatePhone(personId, phone);
        } catch (Exception e){
            LOGGER.error("修改手机号失败 phone: {}, personId: {}，e: {}", phone, personId, ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "修改手机号失败");
        }

    }
}
