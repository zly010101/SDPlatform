package cn.org.upthink.test;

import cn.org.upthink.gen.JpaEntityFreeMarkerGenerator;

/**
 * Created by rover on 2018/3/26.
 */
public class TestMain {

    public static void main(String[] args){
        JpaEntityFreeMarkerGenerator fmGenerator = new JpaEntityFreeMarkerGenerator();
        fmGenerator.generate(WxActivityTime.class, "/Users/rover/Downloads/codegenerator", true);
    }

}
