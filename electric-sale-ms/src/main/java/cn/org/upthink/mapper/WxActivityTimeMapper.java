package cn.org.upthink.mapper;

import cn.org.upthink.test.WxActivityTime;
import cn.org.upthink.persistence.mybatis.dao.CrudDao;
import cn.org.upthink.persistence.mybatis.annotation.MyBatisDao;

@MyBatisDao
public interface WxActivityTimeMapper extends CrudDao<WxActivityTime> {

}