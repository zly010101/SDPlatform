package cn.org.upthink.gen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 表字段标识
 * Created by rover on 2017-05-03.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {

    /**
     * <p>
     * 字段名（驼峰命名方式，该值可无）
     * </p>
     */
    String name() default "";

    /**
     * <p>
     * 当该Field为类对象时, 可使用#{对象.属性}来映射到数据表.
     * </p>
     * <p>
     * 支持：@TableField(el = "role, jdbcType=BIGINT)<br>
     * 支持：@TableField(el = "role, typeHandler=com.xx.typehandler.PhoneTypeHandler")
     * </p>
     */
    String el() default "";

    /**
     * <p>
     * 是否为数据库表字段
     * </p>
     * <p>
     * 默认 true 存在，false 不存在
     * </p>
     */
    boolean exist() default true;

    String columnDefinition() default "";

    int length() default 30;

    String m2mKey() default "";

    /**注释*/
    String remark() default "";

    /**是否是必须字段（用于保存、更新、参数传入等操作时）*/
    boolean required() default false;

    /**是否是界面的查询字段*/
    boolean isQuery() default false;

}