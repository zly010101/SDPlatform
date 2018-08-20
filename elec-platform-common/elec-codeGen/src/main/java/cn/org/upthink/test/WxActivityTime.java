package cn.org.upthink.test;

import cn.org.upthink.gen.annotation.TableField;
import cn.org.upthink.persistence.mybatis.entity.BaseDataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by rover on 2017-07-02.
 * 红包发放时间
 */
public class WxActivityTime extends BaseDataEntity<WxActivityTime> {

    private static final long serialVersionUID = 1L;

//    @TableField(name="id")
//    private String id;

    @TableField(name="act_id",remark = "测试id", required = false,isQuery = false)
    private String actId;

    @TableField(name="act_name",remark = "测试id", required = false,isQuery = false)
    private String actName;


    /**用于查询的参数: yyyy-MM-dd HH:mm:ss*/
    private String checkTime;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }
}
