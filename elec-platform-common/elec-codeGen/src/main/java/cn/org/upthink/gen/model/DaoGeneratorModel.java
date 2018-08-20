package cn.org.upthink.gen.model;

import cn.org.upthink.gen.dialect.Dialect;
import cn.org.upthink.gen.reflection.JpaMetaClass;
import org.springframework.util.ClassUtils;

/**
 * 持久层代码生成模型<br/>
 *     用以支持*Dao实现的生成<br/>
 * <功能详细描述>
 */
public class DaoGeneratorModel {
    
    private String basePackage;
    
    private String entityTypeName;
    
    private String entitySimpleTypeName;
    
    private String lowerCaseEntityTypeName;
    
    public DaoGeneratorModel() {
        super();
    }
    
    /** <默认构造函数> */
    public DaoGeneratorModel(JpaMetaClass<?> jpaMetaClass, Dialect dialect) {
        super();
        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        
        this.basePackage = ClassUtils.convertResourcePathToClassName(basePath);
        this.entityTypeName = jpaMetaClass.getEntityTypeName();
        this.entitySimpleTypeName = jpaMetaClass.getEntitySimpleName();
        this.lowerCaseEntityTypeName = org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName());
    }
    
    /**
     * @return 返回 lowerCaseEntityTypeName
     */
    public String getLowerCaseEntityTypeName() {
        return lowerCaseEntityTypeName;
    }
    
    /**
     * @param lowerCaseEntityTypeName 进行赋值
     */
    public void setLowerCaseEntityTypeName(String lowerCaseEntityTypeName) {
        this.lowerCaseEntityTypeName = lowerCaseEntityTypeName;
    }
    
    /**
     * @return 返回 basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }
    
    /**
     * @param basePackage 进行赋值
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    
    /**
     * @return 返回 entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    /**
     * @param entityTypeName 进行赋值
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getEntitySimpleTypeName() {
        return entitySimpleTypeName;
    }

    public void setEntitySimpleTypeName(String entitySimpleTypeName) {
        this.entitySimpleTypeName = entitySimpleTypeName;
    }
}
