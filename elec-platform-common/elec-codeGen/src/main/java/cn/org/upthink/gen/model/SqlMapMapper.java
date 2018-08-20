package cn.org.upthink.gen.model;

import cn.org.upthink.gen.reflection.JpaMetaClass;

/**
 * 对应sqlMap顶层相关配置
 * <功能详细描述>
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapMapper {
    
    /** 命名空间 */
    private String namespace;

    private String tableName;

    private String id;

    private String entityTypeName;

    private String entitySimpleTypeName;

    private String entitySimpleName;

    private String lowerCaseEntityTypeName;

    private String lowerCaseEntitySimpleName;
    
    /** <默认构造函数> */
    public SqlMapMapper() {
        super();
    }
    
    /** <默认构造函数> */
    public SqlMapMapper(JpaMetaClass<?> jpaMetaClass) {
        super();
        //this.namespace = org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName());
        this.namespace = "mapper.I"+jpaMetaClass.getEntitySimpleName()+"Mapper";
    }
    
    /**
     * @return 返回 namespace
     */
    public String getNamespace() {
        return namespace;
    }
    
    /**
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getEntitySimpleTypeName() {
        return entitySimpleTypeName;
    }

    public void setEntitySimpleTypeName(String entitySimpleTypeName) {
        this.entitySimpleTypeName = entitySimpleTypeName;
    }

    public String getLowerCaseEntityTypeName() {
        return lowerCaseEntityTypeName;
    }

    public void setLowerCaseEntityTypeName(String lowerCaseEntityTypeName) {
        this.lowerCaseEntityTypeName = lowerCaseEntityTypeName;
    }

    public String getEntitySimpleName() {
        return entitySimpleName;
    }

    public void setEntitySimpleName(String entitySimpleName) {
        this.entitySimpleName = entitySimpleName;
    }

    public String getLowerCaseEntitySimpleName() {
        return lowerCaseEntitySimpleName;
    }

    public void setLowerCaseEntitySimpleName(String lowerCaseEntitySimpleName) {
        this.lowerCaseEntitySimpleName = lowerCaseEntitySimpleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
