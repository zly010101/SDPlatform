package ${mapper.basePackage};

import ${mapper.entityTypeName};
import com.kangmei.persistence.mybatis.dao.CrudDao;
import com.kangmei.persistence.mybatis.annotation.MyBatisDao;

@MyBatisDao
public interface ${mapper.entitySimpleTypeName}Mapper extends CrudDao<${mapper.entitySimpleTypeName}> {

}