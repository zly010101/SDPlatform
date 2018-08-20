package cn.org.upthink.gen.model;

import cn.org.upthink.gen.reflection.JpaMetaClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

/**
 * <功能简述>
 * <功能详细描述>
 */
public class ControllerGeneratorModel {

    private String bizName;

    private String basePackage;

    private String entityTypeName;

    private String entitySimpleName;

    private String lowerCaseEntitySimpleName;

    public ControllerGeneratorModel() {
        super();
    }

    /** <默认构造函数> */
    public ControllerGeneratorModel(JpaMetaClass<?> jpaMetaClass) {
        super();
        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        this.basePackage = ClassUtils.convertResourcePathToClassName(basePath);
        this.entityTypeName = jpaMetaClass.getEntityTypeName();
        this.entitySimpleName = jpaMetaClass.getEntitySimpleName();
        this.lowerCaseEntitySimpleName = StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName());
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    /**
     * @return 返回 basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * @param basePackage 进行赋值
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    
    /**
     * @return 返回 entitySimpleName
     */
    public String getEntitySimpleName() {
        return entitySimpleName;
    }
    
    /**
     * @param entitySimpleName 进行赋值
     */
    public void setEntitySimpleName(String entitySimpleName) {
        this.entitySimpleName = entitySimpleName;
    }
    
    /**
     * @return 返回 lowerCaseEntitySimpleName
     */
    public String getLowerCaseEntitySimpleName() {
        return lowerCaseEntitySimpleName;
    }
    
    /**
     * @param lowerCaseEntitySimpleName 进行赋值
     */
    public void setLowerCaseEntitySimpleName(String lowerCaseEntitySimpleName) {
        this.lowerCaseEntitySimpleName = lowerCaseEntitySimpleName;
    }
    
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
}
