package com.jiuxi.admin.core.controller;

import com.jiuxi.common.util.PhoneDataMigrationUtil;
import com.jiuxi.common.util.TpAccountPhoneDataMigrationUtil;
import com.jiuxi.common.util.PhoneEncryptionUtils;
import com.jiuxi.core.bean.TopinfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据迁移控制器
 * 提供手机号加密相关的数据迁移和测试接口
 * 
 * @author system
 * @date 2024-01-20
 */
@RestController
@RequestMapping("/api/migration")
public class MigrationController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationController.class);
    
    @Autowired
    private PhoneDataMigrationUtil phoneDataMigrationUtil;
    
    @Autowired
    private TpAccountPhoneDataMigrationUtil tpAccountPhoneDataMigrationUtil;
    
    /**
     * 迁移tp_person_basicinfo表手机号数据
     */
    @PostMapping("/phone/person")
    public TopinfoResult migratePersonPhoneData() {
        try {
            int count = phoneDataMigrationUtil.migratePhoneData();
            return TopinfoResult.success("tp_person_basicinfo表手机号迁移完成，成功处理 " + count + " 条记录");
        } catch (Exception e) {
            LOGGER.error("tp_person_basicinfo表手机号迁移失败", e);
            return TopinfoResult.error("迁移失败：" + e.getMessage());
        }
    }
    
    /**
     * 迁移tp_account表手机号数据
     */
    @PostMapping("/phone/account")
    public TopinfoResult migrateAccountPhoneData() {
        try {
            int count = tpAccountPhoneDataMigrationUtil.migrateAccountPhoneData();
            return TopinfoResult.success("tp_account表手机号迁移完成，成功处理 " + count + " 条记录");
        } catch (Exception e) {
            LOGGER.error("tp_account表手机号迁移失败", e);
            return TopinfoResult.error("迁移失败：" + e.getMessage());
        }
    }
    
    /**
     * 验证tp_person_basicinfo表手机号迁移结果
     */
    @GetMapping("/phone/person/validate")
    public TopinfoResult validatePersonPhoneMigration() {
        try {
            String result = phoneDataMigrationUtil.validateMigration();
            return TopinfoResult.success(result);
        } catch (Exception e) {
            LOGGER.error("tp_person_basicinfo表手机号迁移验证失败", e);
            return TopinfoResult.error("验证失败：" + e.getMessage());
        }
    }
    
    /**
     * 验证tp_account表手机号迁移结果
     */
    @GetMapping("/phone/account/validate")
    public TopinfoResult validateAccountPhoneMigration() {
        try {
            String result = tpAccountPhoneDataMigrationUtil.validateMigration();
            return TopinfoResult.success(result);
        } catch (Exception e) {
            LOGGER.error("tp_account表手机号迁移验证失败", e);
            return TopinfoResult.error("验证失败：" + e.getMessage());
        }
    }
    
    /**
     * 测试手机号加密功能
     */
    @PostMapping("/phone/test")
    public TopinfoResult testPhoneEncryption(@RequestBody Map<String, String> request) {
        try {
            String phone = request.get("phone");
            if (phone == null || phone.trim().isEmpty()) {
                return TopinfoResult.error("手机号不能为空");
            }
            
            // 加密
            String encrypted = PhoneEncryptionUtils.encrypt(phone);
            
            // 解密
            String decrypted = PhoneEncryptionUtils.decrypt(encrypted);
            
            Map<String, Object> result = new HashMap<>();
            result.put("original", phone);
            result.put("encrypted", encrypted);
            result.put("decrypted", decrypted);
            result.put("success", phone.equals(decrypted));
            
            return TopinfoResult.success("手机号加密测试完成", result);
        } catch (Exception e) {
            LOGGER.error("手机号加密测试失败", e);
            return TopinfoResult.error("测试失败：" + e.getMessage());
        }
    }
    
    /**
     * 批量迁移所有表的手机号数据
     */
    @PostMapping("/phone/all")
    public TopinfoResult migrateAllPhoneData() {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 迁移tp_person_basicinfo表
            int personCount = phoneDataMigrationUtil.migratePhoneData();
            result.put("tp_person_basicinfo", personCount);
            
            // 迁移tp_account表
            int accountCount = tpAccountPhoneDataMigrationUtil.migrateAccountPhoneData();
            result.put("tp_account", accountCount);
            
            result.put("total", personCount + accountCount);
            
            return TopinfoResult.success("所有表手机号迁移完成", result);
        } catch (Exception e) {
            LOGGER.error("批量手机号迁移失败", e);
            return TopinfoResult.error("迁移失败：" + e.getMessage());
        }
    }
}