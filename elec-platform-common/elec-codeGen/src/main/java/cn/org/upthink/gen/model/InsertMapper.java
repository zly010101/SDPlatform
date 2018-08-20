package cn.org.upthink.gen.model;

import cn.org.upthink.gen.reflection.JpaMetaClass;
import cn.org.upthink.gen.util.GeneratorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 插入的映射器描述
 * <功能详细描述>
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class InsertMapper {
    
    private String id;
    
    private String parameterType;
    
    private boolean isUseSelectKey = false;
    private boolean isUseUUIDKey = false;
    private boolean isAutoByTable = false;
    
    private SqlMapSelectKey selectKey;
    
    private String tableName;
    
    private List<SqlMapColumn> sqlMapColumnList = new ArrayList<SqlMapColumn>();
    
    public InsertMapper() {
        super();
    }
    
    public InsertMapper(JpaMetaClass<?> jpaMetaClass) {
        super();
        this.id = "insert" + jpaMetaClass.getEntitySimpleName();
        this.parameterType = jpaMetaClass.getEntityTypeName();
        this.isUseSelectKey = false;
        this.isUseUUIDKey = false;
        this.tableName = jpaMetaClass.getTableName().toUpperCase();
        this.sqlMapColumnList = GeneratorUtils.generateSqlMapColumnList(jpaMetaClass);
    }
    
    /**
     * @return 返回 isUseSelectKey
     */
    public boolean isUseSelectKey() {
        return isUseSelectKey;
    }
    
    /**
     */
    public void setUseSelectKey(boolean isUseSelectKey) {
        this.isUseSelectKey = isUseSelectKey;
    }
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }
    
    /**
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return 返回 parameterType
     */
    public String getParameterType() {
        return parameterType;
    }
    
    /**
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
    
    /**
     * @return 返回 selectKey
     */
    public SqlMapSelectKey getSelectKey() {
        return selectKey;
    }
    
    /**
     */
    public void setSelectKey(SqlMapSelectKey selectKey) {
        this.selectKey = selectKey;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * @return 返回 sqlMapColumnList
     */
    public List<SqlMapColumn> getSqlMapColumnList() {
        return sqlMapColumnList;
    }
    
    /**
     */
    public void setSqlMapColumnList(List<SqlMapColumn> sqlMapColumnList) {
        this.sqlMapColumnList = sqlMapColumnList;
    }

	public boolean isUseUUIDKey() {
		return isUseUUIDKey;
	}

	public void setUseUUIDKey(boolean isUseUUIDKey) {
		this.isUseUUIDKey = isUseUUIDKey;
	}

	public boolean isAutoByTable() {
		return isAutoByTable;
	}

	public void setAutoByTable(boolean isAutoByTable) {
		this.isAutoByTable = isAutoByTable;
	}
    
}
