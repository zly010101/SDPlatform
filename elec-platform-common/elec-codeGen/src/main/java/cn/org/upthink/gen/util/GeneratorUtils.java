package cn.org.upthink.gen.util;

import java.util.*;
import java.util.Map.Entry;

import cn.org.upthink.gen.annotation.TableField;
import cn.org.upthink.gen.reflection.JpaColumnInfo;
import cn.org.upthink.gen.reflection.JpaMetaClass;
import cn.org.upthink.gen.reflection.ReflectionUtils;
import cn.org.upthink.gen.model.SqlMapColumn;
import org.apache.commons.lang3.StringUtils;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GeneratorUtils {
    
    /**
     * 生成字段列表
     *<功能详细描述>
     * @param jpaMetaClass
     * @return [参数说明]
     * 
     * @return List<SqlMapColumn> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <TYPE> List<SqlMapColumn> generateSqlMapColumnList(JpaMetaClass<TYPE> jpaMetaClass) {
        List<SqlMapColumn> columnList = new ArrayList<SqlMapColumn>();
        String idPropertyName = jpaMetaClass.getPkGetterName();

//        Iterator<String> it = jpaMetaClass.getSetterNames().iterator();
//        while (it.hasNext()) {
//            String str = it.next();
//            System.out.println("field="+str);
//        }

        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping().entrySet()) {
            String getterName = entryTemp.getKey();
            if(ReflectionUtils.isHasAnnotationForGetter(jpaMetaClass.getType(), getterName, TableField.class)) { //Column.class存在TableField注解的才获取.
                JpaColumnInfo jpaColumnInfo = entryTemp.getValue();

                SqlMapColumn columnTemp = null;
                if (jpaColumnInfo.isSimpleType()) {
                    columnTemp = new SqlMapColumn(true,
                            jpaColumnInfo.getGetterName(),
                            jpaColumnInfo.getColumnName(),
                            jpaColumnInfo.getRealGetterType(), null);
                } else {
                    columnTemp = new SqlMapColumn(false,
                            jpaColumnInfo.getGetterName(),
                            jpaColumnInfo.getColumnName(),
                            jpaColumnInfo.getRealGetterType(),
                            jpaColumnInfo.getForeignKeyGetterName());
                }

                TableField columnAnno = ReflectionUtils.getGetterAnnotation(jpaMetaClass.getType(), getterName, TableField.class);
                if(StringUtils.isNotEmpty(columnAnno.m2mKey())){
                    columnTemp.setPropertyName(columnTemp.getPropertyName()+"."+columnAnno.m2mKey());
                }

                if(StringUtils.isNotEmpty(columnAnno.remark())){
                    columnTemp.setRemark(columnAnno.remark());
                }
                columnTemp.setRequired(columnAnno.required());
                columnTemp.setFieldType(jpaColumnInfo.getRealGetterType().getSimpleName());

                if(columnAnno.isQuery()){
                    columnTemp.setQuery(columnAnno.isQuery());
                }

                if (idPropertyName.equals(getterName)) {
                    columnTemp.setId(true);
                }

                columnTemp.setGetterMethodSimpleName(ReflectionUtils.getGetMethodNameByGetterNameAndType(jpaColumnInfo.getGetterName(),
                        jpaColumnInfo.getGetterType()));

                //System.out.println("field="+ReflectionUtils.getSetMethodName(columnTemp.getPropertyName(), jpaColumnInfo.getGetterType()));

                columnTemp.setSetterMethodSimpleName(ReflectionUtils.getSetMethodName(columnTemp.getPropertyName(), jpaColumnInfo.getGetterType()));

                columnList.add(columnTemp);
            }
        }
        
        Collections.sort(columnList, COLUMN_COMPARATOR);
        return columnList;
    }
    
    /** 默认的字段比较器，用以排序 */
    private static final Comparator<SqlMapColumn> COLUMN_COMPARATOR = new Comparator<SqlMapColumn>() {
        /**
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(SqlMapColumn o1, SqlMapColumn o2) {
            if (o1.isId()) {
                return -1;
            }
            if (o2.isId()) {
                return 1;
            }
            if (o1.getClass().getName().length() - o2.getClass().getName().length() > 0) {
                return 1;
            } else if (o1.getClass().getName().length() - o2.getClass().getName().length() < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    };
    
}
