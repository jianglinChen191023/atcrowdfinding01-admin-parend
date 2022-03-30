package com.atguigu.crowd.util;

import com.atguigu.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {

    /**
     * MD5 加密
     *
     * @param source 明文字符串
     * @return 加密后的字符串
     */
    public static String md5(String source) {
        // 1. 判断 source 是否有效
        if (source == null || source.length() == 0) {
            // 2. 如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 3. 获取 MessageDigest 对象
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4. 获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            // 5. 执行加密
            byte[] output = messageDigest.digest(input);

            // 6. 创建 BigInteger 对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7. 按照 16 进制将 bigInteger 的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix);

            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断当前请求是否为 Ajax 请求
     *
     * @param request 请求对象
     * @return true: 当前请求是 Ajax 请求
     * false: 当前请求不是 Ajax 请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1. 获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWith = request.getHeader("X-Requested-With");

        // 2. 判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                ("XMLHttpRequest".equals(xRequestedWith));
    }

}
