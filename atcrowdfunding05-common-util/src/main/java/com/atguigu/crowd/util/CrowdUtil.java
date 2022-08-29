package com.atguigu.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.atguigu.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CrowdUtil {

    /**
     * 给远程第三方接口发送请求把验证码发送到用户手机上
     *
     * @param appCode     用来调用第三方短信 API 的 AppCode
     * @param templateId  模板的编号
     * @param host        短信接口调用的 URL 地址
     * @param path        具体发送短信功能的地址
     * @param method      请求方式
     * @param phoneNumber 接收短信的手机号码
     * @return 成功返回: 验证码
     */
    public static ResultEntity<String> sendShortMessage(String appCode, String templateId, String host, String path, String method, String phoneNumber) {
        // 生成验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            code.append(random);
        }

        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();

        bodys.put("content", "code:" + code);
        bodys.put("phone_number", phoneNumber);
        if (templateId == null) {
            // 测试模板的 Id
            bodys.put("template_id", "TPL_0000");
        } else {
            bodys.put("template_id", templateId);
        }

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));

            StatusLine statusLine = response.getStatusLine();
            // 状态码: [{200: 正常}, {400: 请求参数错误}, {403: 套餐余额用完}, {500: 服务器内部错误}]
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == 200) {
                return ResultEntity.successWithData(code.toString());
            } else {
                return ResultEntity.failed(reasonPhrase);
            }
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }
    }

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
