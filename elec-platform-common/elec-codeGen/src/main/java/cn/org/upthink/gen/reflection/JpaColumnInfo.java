package cn.org.upthink.gen.reflection;

/**
 * 字段信息<br/>
 * <功能详细描述>
 */
public class JpaColumnInfo {
    
    /** 
     * 是否为简单类型
     * JdbcUtils.isSupportedSimpleType
     * 如果为简单类型，则可由typeHandle处理对应类型的增删查改
     */
    private boolean simpleType;
    
    /** 对应数据库字段名 */
    private String columnName;
    
    /** 字段名 */
    private String getterName;

    /** 字段对应java类型 */
    private Class<?> getterType;
    
    /** 外键名 */
    private String foreignKeyGetterName;
    
    /** 外键类型 */
    private Class<?> foreignKeyGetterType;
    
    /** 真正的getter名，可以为xxx.xxx的形式，通过column注解根据类型获取 */
    private String realGetterName;
    
    /** 真正的getter类型 */
    private Class<?> realGetterType;
    
    /** 字段对应注解 */
    private String columnComment;
    
    /** 是否是唯一键 */
    private boolean unique;
    
    /** 是否可为空 */
    private boolean nullable;
    
    /** column length */
    private int length = 255;
    
    /** The precision for a decimal */
    private int precision = 0;
    
    /** The scale for a decimal (exact numeric) column */
    private int scale = 0;
    
    /**
     * <默认构造函数>
     */
    JpaColumnInfo() {
        super();
    }
    
    /**
     * @return 返回 simpleType
     */
    public boolean isSimpleType() {
        return simpleType;
    }
    
    /**
     * @param simpleType
     */
    public void setSimpleType(boolean simpleType) {
        this.simpleType = simpleType;
    }
    
    /**
     * @return 返回 getterName
     */
    public String getGetterName() {
        return getterName;
    }
    
    /**
     * @param getterName
     */
    public void setGetterName(String getterName) {
        this.getterName = getterName;
    }
    
    /**
     * @return 返回 getterType
     */
    public Class<?> getGetterType() {
        return getterType;
    }
    
    /**
     */
    public void setGetterType(Class<?> getterType) {
        this.getterType = getterType;
    }
    
    /**
     * @return 返回 realGetterName
     */
    public String getRealGetterName() {
        return realGetterName;
    }
    
    /**
     */
    public void setRealGetterName(String realGetterName) {
        this.realGetterName = realGetterName;
    }
    
    /**
     * @return 返回 realGetterType
     */
    public Class<?> getRealGetterType() {
        return realGetterType;
    }
    
    /**
     */
    public void setRealGetterType(Class<?> realGetterType) {
        this.realGetterType = realGetterType;
    }
    
    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName.trim().toUpperCase();
    }
    
    /**
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName.trim().toUpperCase();
    }
    
    /**
     * @return 返回 columnComment
     */
    public String getColumnComment() {
        return columnComment;
    }
    
    /**
     */
    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
    
    /**
     * @return 返回 unique
     */
    public boolean isUnique() {
        return unique;
    }
    
    /**
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
    }
    
    /**
     * @return 返回 nullable
     */
    public boolean isNullable() {
        return nullable;
    }
    
    /**
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    
    /**
     * @return 返回 length
     */
    public int getLength() {
        return length;
    }
    
    /**
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * @return 返回 precision
     */
    public int getPrecision() {
        return precision;
    }
    
    /**
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    
    /**
     * @return 返回 scale
     */
    public int getScale() {
        return scale;
    }
    
    /**
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * @return 返回 foreignKeyGetterName
     */
    public String getForeignKeyGetterName() {
        return foreignKeyGetterName;
    }

    /**
     */
    public void setForeignKeyGetterName(String foreignKeyGetterName) {
        this.foreignKeyGetterName = foreignKeyGetterName;
    }

    /**
     * @return 返回 foreignKeyGetterType
     */
    public Class<?> getForeignKeyGetterType() {
        return foreignKeyGetterType;
    }

    /**
     */
    public void setForeignKeyGetterType(Class<?> foreignKeyGetterType) {
        this.foreignKeyGetterType = foreignKeyGetterType;
    }

}
