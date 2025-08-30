package com.jiuxi.controller;

import com.jiuxi.common.util.PhoneDataMigrationUtil;
import com.jiuxi.common.bean.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 手机号数据迁移控制器
 * 提供手机号加密迁移相关的API接口
 * 
 * @author System
 * @date 2024-01-20
 */
@RestController
@RequestMapping("/api/migration")
public class PhoneDataMigrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDataMigrationController.class);

    @Autowired
    private PhoneDataMigrationUtil phoneDataMigrationUtil;

    /**
     * 执行手机号数据迁移（加密）
     * 将tp_person_basicinfo表中的明文手机号加密
     * 
     * @return 迁移结果
     */
    @PostMapping("/phone/encrypt")
    public JsonResponse<String> migratePhoneData() {
        try {
            LOGGER.info("开始执行手机号数据迁移...");
            
            int successCount = phoneDataMigrationUtil.migratePhoneData();
            
            String message = String.format("手机号数据迁移完成，成功加密 %d 条记录", successCount);
            LOGGER.info(message);
            
            return JsonResponse.buildSuccess(message);
            
        } catch (Exception e) {
            String errorMessage = "手机号数据迁移失败：" + e.getMessage();
            LOGGER.error(errorMessage, e);
            return JsonResponse.buildFailure(errorMessage);
        }
    }

    /**
     * 回滚手机号数据（解密）
     * 将tp_person_basicinfo表中的加密手机号解密回明文
     * 注意：此接口仅用于测试或紧急回滚，生产环境慎用
     * 
     * @return 回滚结果
     */
    @PostMapping("/phone/decrypt")
    public JsonResponse<String> rollbackPhoneData() {
        try {
            LOGGER.warn("开始执行手机号数据回滚（解密）...");
            
            int successCount = phoneDataMigrationUtil.rollbackPhoneData();
            
            String message = String.format("手机号数据回滚完成，成功解密 %d 条记录", successCount);
            LOGGER.warn(message);
            
            return JsonResponse.buildSuccess(message);
            
        } catch (Exception e) {
            String errorMessage = "手机号数据回滚失败：" + e.getMessage();
            LOGGER.error(errorMessage, e);
            return JsonResponse.buildFailure(errorMessage);
        }
    }

    /**
     * 查询手机号加密状态统计
     * 统计当前数据库中手机号的加密情况
     * 
     * @return 统计结果
     */
    @GetMapping("/phone/status")
    public JsonResponse<String> getPhoneEncryptionStatus() {
        try {
            String status = phoneDataMigrationUtil.getPhoneEncryptionStatus();
            LOGGER.info("手机号加密状态查询：{}", status);
            
            return JsonResponse.buildSuccess(status);
            
        } catch (Exception e) {
            String errorMessage = "查询手机号加密状态失败：" + e.getMessage();
            LOGGER.error(errorMessage, e);
            return JsonResponse.buildFailure(errorMessage);
        }
    }

    /**
     * 测试手机号加密解密功能
     * 用于验证加密工具类的正确性
     * 
     * @param phone 测试手机号
     * @return 测试结果
     */
    @PostMapping("/phone/test")
    public JsonResponse<String> testPhoneEncryption(@RequestParam String phone) {
        try {
            if (phone == null || phone.trim().isEmpty()) {
                return JsonResponse.buildFailure("手机号不能为空");
            }
            
            // 验证手机号格式
            if (!phone.matches("^1[3-9]\\d{9}$")) {
                return JsonResponse.buildFailure("手机号格式不正确");
            }
            
            // 测试加密
            String encrypted = com.jiuxi.common.util.PhoneEncryptionUtils.encrypt(phone);
            LOGGER.info("原始手机号：{}，加密后：{}", phone, encrypted);
            
            // 测试解密
            String decrypted = com.jiuxi.common.util.PhoneEncryptionUtils.decrypt(encrypted);
            LOGGER.info("解密后：{}", decrypted);
            
            // 验证加密解密的一致性
            boolean isConsistent = phone.equals(decrypted);
            
            String result = String.format(
                "测试结果：\n" +
                "原始手机号：%s\n" +
                "加密后：%s\n" +
                "解密后：%s\n" +
                "一致性检查：%s",
                phone, encrypted, decrypted, isConsistent ? "通过" : "失败"
            );
            
            if (isConsistent) {
                return JsonResponse.buildSuccess(result);
            } else {
                return JsonResponse.buildFailure("加密解密测试失败：" + result);
            }
            
        } catch (Exception e) {
            String errorMessage = "测试手机号加密解密失败：" + e.getMessage();
            LOGGER.error(errorMessage, e);
            return JsonResponse.buildFailure(errorMessage);
        }
    }
}