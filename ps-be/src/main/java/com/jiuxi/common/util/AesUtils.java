package com.jiuxi.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * @ClassName: AesUtils
 * @Description: AesUtils 基于hutool上加密工具类封装
 * @Author: 杨攀
 * @Date: 2020/5/26 13:23
 * @Copyright: 2020 www.tuxun.net Inc. All rights reserved.
 */
public class AesUtils {

    /**
     * 对象
     */
    private volatile static AES aes;

    /**
     * 秘钥： 字符串长度不能小于16
     * private static String SECRET_KEY = "uKzE3CCNZ5XFwHwW";
     * <p>
     * 盐： 字符串长度不能小于16
     * private static String SECRET_IV = "zGrVju3LPhyhiJR8";
     */

    public static AES newInstance(String SECRET_KEY, String SECRET_IV) {
        if (aes == null) {
            synchronized (AesUtils.class) {
                if (aes == null) {
                    byte[] key = SECRET_KEY.getBytes();
                    byte[] vi = SECRET_IV.getBytes();
                    aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, vi);
                }
            }
        }
        return aes;
    }


    /**
     * @param content 需要加密的内容
     * @return java.lang.String
     * @description: 加密为十六进制
     * @author 杨攀
     * @date 2020/5/26 13:51
     */
    public static String encryptHex(String content) {
        return aes.encryptHex(content);
    }


    /**
     * @param encrypt 十六进制的密文
     * @return java.lang.String
     * @description: 解密十六进制的密文
     * @author 杨攀
     * @date 2020/5/26 13:52
     */
    public static String decryptStr(String encrypt) {
        try {
            return aes.decryptStr(encrypt);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法参数异常！");
        }
    }


    /*public static void main(String[] args) {

        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, Base64.decode("X7ZIUtahLLv+aUxCmNFmeA=="), Base64.decode("Bf4fbe5t07iJ7Ag6fJk34g=="));
        String aaa = aes.decryptStr("4Oh6HaxIPuc0pfrmol9bUZrrrd9NJVKPssGGI2Pv7+YhcX3vxS6mK+oZjTQMJtRn6P4L55tncs77hOtj+fjJhezv66FaRooT021RkJ/ILVW2WCiHvRZ4zLAtm9tbSvyKfDqDc3VbP9HnuDKjrG6JHIx/5KB2bZtiVimxKgYXzoqXNgaTZvzCv4qLhEuoKc22i4S/dZY+J4Fp8N/POC3flfYyETXsr/pj0D6516UVj+OCOwV3EHuLv6GHCAdc39o3G+eRoqMkg4InpTKBTCioxAwPPaUg5Ix8LaTq3CrLPPRC4t3TkKIyhkVtc5O+71dWMddO7TS2ymDGIYH8kYJSnUYtg8n6fNXHeOnmoGVmPIO6F3orx+EMVen5Wpn22JUs");
        // 调试代码已移除
        // System.out.println(aaa);
        // System.out.println(aes.encrypt(aaa));
    }*/

}
