package com.atguigu.crowd.test;

import com.atguigu.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {

    /**
     * MD5 加密测试
     */
    @Test
    public void testMd5() {
        String source = "123456";
        String encoded = CrowdUtil.md5(source);
        // 结果 => e10adc3949ba59abbe56e057f20f883e
        System.out.println(encoded);
    }

}
