package ${mapper.basePackage};

import ${mapper.entityTypeName};
import cn.org.upthink.persistence.mybatis.dao.CrudDao;
import cn.org.upthink.persistence.mybatis.annotation.MyBatisDao;

@MyBatisDao
public interface ${mapper.entitySimpleTypeName}Mapper extends CrudDao<${mapper.entitySimpleTypeName}> {

}