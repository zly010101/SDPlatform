package cn.org.upthink.gen.model;

import cn.org.upthink.gen.reflection.JpaMetaClass;
import cn.org.upthink.gen.util.GeneratorUtils;

import java.util.List;

/**
 * 更新映射生成器
 * <功能详细描述>
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdateMapper {
    
    private String id;
    
    private List<SqlMapColumn> sqlMapColumnList;
    
    private String tableName;
    
    private String simpleTableName;
    
    private String idColumnName;
    
    private String idPropertyName;
    
    private List<SqlMapColumn> updateSqlMapColumnList;
    
    /** <默认构造函数> */
    public UpdateMapper() {
        super();
    }
    
    /** <默认构造函数> */
    public UpdateMapper(JpaMetaClass<?> jpaMetaClass) {
        super();
        this.id = "update" + jpaMetaClass.getEntitySimpleName();
        
        //this.idPropertyName = jpaMetaClass.getPkGetterName();
        //this.idColumnName = jpaMetaClass.getGetter2columnInfoMapping().get(this.idPropertyName).getColumnName().toUpperCase();
        this.simpleTableName = jpaMetaClass.getSimpleTableName().toUpperCase();
        this.tableName = jpaMetaClass.getTableName().toUpperCase();
        this.sqlMapColumnList = GeneratorUtils.generateSqlMapColumnList(jpaMetaClass);
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
     * @return 返回 simpleTableName
     */
    public String getSimpleTableName() {
        return simpleTableName;
    }
    
    /**
     */
    public void setSimpleTableName(String simpleTableName) {
        this.simpleTableName = simpleTableName;
    }
    
    /**
     * @return 返回 idColumnName
     */
    public String getIdColumnName() {
        return idColumnName;
    }
    
    /**
     */
    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }
    
    /**
     * @return 返回 idPropertyName
     */
    public String getIdPropertyName() {
        return idPropertyName;
    }
    
    /**
     */
    public void setIdPropertyName(String idPropertyName) {
        this.idPropertyName = idPropertyName;
    }
    
    /**
     * @return 返回 updateSqlMapColumnList
     */
    public List<SqlMapColumn> getUpdateSqlMapColumnList() {
        return updateSqlMapColumnList;
    }
    
    /**
     */
    public void setUpdateSqlMapColumnList(
            List<SqlMapColumn> updateSqlMapColumnList) {
        this.updateSqlMapColumnList = updateSqlMapColumnList;
    }
}
