package cn.org.upthink.gen.model;

/**
 * sqlMap中column定义
 * <功能详细描述>
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapColumn {
    
    public SqlMapColumn() {
        super();
    }
    
    public SqlMapColumn(boolean isSimpleType, String propertyName,
            String columnName, Class<?> javaType, String joinPropertyName) {
        super();
        this.isSimpleType = isSimpleType;
        this.propertyName = propertyName;
        this.columnName = columnName;
        this.javaType = javaType;
        this.joinPropertyName = joinPropertyName;
        this.isSameName = this.columnName.toUpperCase().equals(this.propertyName.toUpperCase());
    }
    
    /** 是否为主键字段 */
    private boolean isId = false;
    
    /** 
     * 是否为基本类型
     * 是否为typeHandle能够处理的类型  
     */
    private boolean isSimpleType;
    
    private String propertyName;
    
    private String columnName;
    
    private Class<?> javaType;
    
    private String joinPropertyName;
    
    private boolean isSameName = false;
    
    private String getterMethodSimpleName;
    private String setterMethodSimpleName;

    /**映射字段－备注，注释*/
    private String remark;
    /**映射字段－必须项*/
    private boolean isRequired = false;
    /**映射字段－字段类别*/
    private String fieldType;
    /**映射字段－查询条件的字段*/
    private boolean isQuery = false;

    /**
     * @return 返回 getterMethodSimpleName
     */
    public String getGetterMethodSimpleName() {
        return getterMethodSimpleName;
    }

    /**
     */
    public void setGetterMethodSimpleName(String getterMethodSimpleName) {
        this.getterMethodSimpleName = getterMethodSimpleName;
    }

    /**
     * @return 返回 isSameName
     */
    public boolean isSameName() {
        return isSameName;
    }

    /**
     */
    public void setSameName(boolean isSameName) {
        this.isSameName = isSameName;
    }

    /**
     * @return 返回 isSimpleType
     */
    public boolean isSimpleType() {
        return isSimpleType;
    }
    
    /**
     */
    public void setSimpleType(boolean isSimpleType) {
        this.isSimpleType = isSimpleType;
    }
    
    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }
    
    /**
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * @return 返回 javaType
     */
    public Class<?> getJavaType() {
        return javaType;
    }
    
    /**
     */
    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
    
    /**
     * @return 返回 joinPropertyName
     */
    public String getJoinPropertyName() {
        return joinPropertyName;
    }
    
    /**
     */
    public void setJoinPropertyName(String joinPropertyName) {
        this.joinPropertyName = joinPropertyName;
    }

    /**
     * @return 返回 isId
     */
    public boolean isId() {
        return isId;
    }

    /**
     */
    public void setId(boolean isId) {
        this.isId = isId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getSetterMethodSimpleName() {
        return setterMethodSimpleName;
    }

    public void setSetterMethodSimpleName(String setterMethodSimpleName) {
        this.setterMethodSimpleName = setterMethodSimpleName;
    }

    public boolean isQuery() {
        return isQuery;
    }

    public void setQuery(boolean query) {
        isQuery = query;
    }
}
