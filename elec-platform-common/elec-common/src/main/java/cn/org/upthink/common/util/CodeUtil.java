package cn.org.upthink.common.util;


import java.util.Arrays;
import java.util.Random;

/**
 * @author mask
 * @date 2018-6-5
 */
public class CodeUtil {
    public static String prefixRandom(String prefix,int bit){
        if(bit < 10 || bit >32){
            return java.util.UUID.randomUUID().toString();
        }
        //FIXME 修改编码生成规格
        long timeMill = System.currentTimeMillis();

        String suffix = String.format("%06d", new Random().nextInt(100000));
        return prefix + Long.parseLong(String.valueOf(timeMill),bit)+suffix;
    }
}