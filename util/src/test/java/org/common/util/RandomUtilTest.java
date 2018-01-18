package org.common.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

import com.lark.middleware.util.RandomUtil;

/***
 * 随机数测试工具
 * 
 * @author liguogang
 * @version $Id: RandomUtilTest.java, v 0.1 2016年10月25日 下午12:42:09 liguogang Exp $
 */
public class RandomUtilTest {
    private final static Set<String> repeat = new HashSet<>();

    public static void testGetRandomNum() {
        String str = RandomUtil.getRandomNum(7);
        System.out.println("随机的" + 7 + "位密码是：" + str);
    }

    public static void testRandom(int num, int len) {
        int last = 0;
        int repeatSkipNum = 0;
        for (int i = 0; i < num; i++) {
            String random = RandomUtil.random(len);
            System.out.println("生成的随机数位,[" + random + "]");
            boolean validate = RandomUtil.validate(random);
            boolean isRpeat = repeat.add(random);
            if (!isRpeat) {
                int skip = (i - last);
                System.out.println("重复的验证码,code=[" + random + "],生成次数,i=[" + i + "],重复间隔[" + skip + "]");
                if (i > 900000) {
                    repeatSkipNum++;
                }
                last = i;
                try {
                    // System.in.read();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("随机数校验结果[" + validate + "],code=[" + random + "]");
            Assert.assertTrue(validate);
        }

        System.out.println("重复次数=[" + repeatSkipNum + "]");
    }

    public static void main(String[] args) {
        //testGetRandomNum();
        testRandom(1000000, 6);
    }
}
