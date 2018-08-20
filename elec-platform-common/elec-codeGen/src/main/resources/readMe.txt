
步骤1: 建实体类

package com.kangmei.test;

import TableField;

public class WxActivityTime {

    private static final long serialVersionUID = 1L;

    @TableField(name="act_id")
    private String actId;

    @TableField(name="act_name")
    private String actName;

    @TableField(name="act_day_cash")
    private String actDayCash;

    @TableField(name="name")
    private String name;

    @TableField(name="remark")
    private String remark;

    @TableField(name="wishing")
    private String wishing;

    /**省略get,set**/

}


步骤2: 在main方法中引入. 以及设置文件的输出路径.

package com.kangmei.test;

import JpaEntityFreeMarkerGenerator;

public class TestMain {

    public static void main(String[] args){
        JpaEntityFreeMarkerGenerator fmGenerator = new JpaEntityFreeMarkerGenerator();
        //建WxActivityTime实体类文件.
        fmGenerator.generate(WxActivityTime.class, "/Users/rover/Downloads/codegenerator", true);
    }

}
