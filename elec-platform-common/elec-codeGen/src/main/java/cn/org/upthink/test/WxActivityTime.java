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

    /**该时间段的金额*/
    @TableField(name="cash")
    private BigDecimal cash;

    @TableField(name="start_time")
    private Date startTime;

    @TableField(name="end_time")
    private Date endTime;

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

    public String getActDayCash() {
        return actDayCash;
    }

    public void setActDayCash(String actDayCash) {
        this.actDayCash = actDayCash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}
