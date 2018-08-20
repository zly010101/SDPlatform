package cn.org.upthink.gen.reflection;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import com.kangmei.frame.modules.gen2.exception.InvalidGetterMethodException;
//import com.kangmei.frame.modules.gen2.exception.InvalidSetterMethodException;
import cn.org.upthink.gen.exception.InvalidGetterMethodException;
import cn.org.upthink.gen.exception.InvalidSetterMethodException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  反射工具类<br/>
 * <功能详细描述>
 */
public class ReflectionUtils {
    
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    
    /**
     * 需要跳过的getter方法
     *     getClass需要跳过
     */
    private static Map<String, Class<?>> needSkipGetterMethod = new HashMap<String, Class<?>>();
    
    static {
        needSkipGetterMethod.put("getClass", Class.class);
    }
    
    /**
      * 是否为getter方法
      *<功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isGetterMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        
        //如果对应方法有入参则该方法不为getter对应方法
        if (method.getParameterTypes().length > 0
                && !Void.TYPE.equals(returnType)) {
            return false;
        }
        
        //如果为getClass则认为该方法非get方法
        if (needSkipGetterMethod.containsKey(methodName)
                && needSkipGetterMethod.get(methodName)
                        .isAssignableFrom(returnType)) {
            return false;
        }
        
        if (boolean.class.isAssignableFrom(returnType)) {
            if (methodName.length() > 2 && methodName.startsWith("is")
                    && Character.isUpperCase(methodName.charAt(2))) {
                return true;
            }
        } else {
            if (methodName.length() > 3 && methodName.startsWith("get")
                    && Character.isUpperCase(methodName.charAt(3))) {
                return true;
            }
        }
        return false;
    }
    
    /**
      * 根据方法名和返回类型获得getterName
      *<功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getGetterNameByMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        
        //如果对应方法有入参则该方法不为getter对应方法
        if (method.getParameterTypes().length > 0
                && !Void.TYPE.equals(returnType)) {
            throw new InvalidGetterMethodException(
                    "方法入参不为空，或返回类型为空.paramterTypes:{};returnType:{}",
                    new Object[] { method.getParameterTypes(), returnType });
        }
        
        //如果为getClass则认为该方法非get方法
        if (needSkipGetterMethod.containsKey(methodName)
                && needSkipGetterMethod.get(methodName)
                        .isAssignableFrom(returnType)) {
            throw new InvalidGetterMethodException(
                    "getClass非get方法，it include needSkipGetterMethod.",
                    new Object[] { method.getParameterTypes(), returnType });
        }
        
        String getterName = null;
        if (boolean.class.isAssignableFrom(returnType)) {
            if (methodName.length() > 2 && methodName.startsWith("is")
                    && Character.isUpperCase(methodName.charAt(2))) {
                getterName = StringUtils.uncapitalize(methodName.substring(2));
            }
        } else {
            if (methodName.length() > 3 && methodName.startsWith("get")
                    && Character.isUpperCase(methodName.charAt(3))) {
                getterName = StringUtils.uncapitalize(methodName.substring(3));
            }
        }
        
        if (!StringUtils.isEmpty(getterName)) {
            return getterName;
        } else {
            throw new InvalidGetterMethodException(
                    "方法名应该以is/get+首写字母为大写字母的字符串组成.methodName:{}",
                    new Object[] { methodName });
        }
    }
    
    /**
     * 获取方法对应的setter
     *<功能详细描述>
     * @param method
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean isSetterMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        //Class<?> firstParameterType = null;
        
        //如果对应方法返回类型为空，且入参为一个
        if (!Void.TYPE.equals(returnType)
                || method.getParameterTypes().length != 1) {
            return false;
        }
        
        if (methodName.length() > 3 && methodName.startsWith("set")
                && Character.isUpperCase(methodName.charAt(3))) {
            return true;
        }
        return false;
    }
    
    /**
      * 获取方法对应的setter
      *<功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getSetterNameByMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        //Class<?> firstParameterType = null;
        
        //如果对应方法返回类型为空，且入参为一个
        if (!Void.TYPE.equals(returnType)
                || method.getParameterTypes().length != 1) {
            throw new InvalidSetterMethodException(
                    "方法入参为空，或返回不为空.paramterTypes:{};returnType:{}",
                    new Object[] { method.getParameterTypes(), returnType });
        }
        
        String setterName = null;
        if (methodName.length() > 3 && methodName.startsWith("set")
                && Character.isUpperCase(methodName.charAt(3))) {
            setterName = StringUtils.uncapitalize(methodName.substring(3));
        }
        
        if (!StringUtils.isEmpty(setterName)) {
            return setterName;
        } else {
            throw new InvalidSetterMethodException(
                    "方法名应该以set+首写字母为大写字母的字符串组成.methodName:{}",
                    new Object[] { methodName });
        }
    }
    
    /**
      * 根据字段名推论得出字段对应的get方法<br/>
      *<功能详细描述>
      * @param field
      *
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getGetMethodNameByField(Field field) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        String methodName = null;
        if (boolean.class.isAssignableFrom(fieldType)) {
            //利用length>2排除掉fieldName = is的情况，如果为is对应方法isIs setIs
            if (fieldName.length() > 2 && fieldName.startsWith("is")
                    && Character.isUpperCase(fieldName.charAt(3))) {
                methodName = fieldName;
            } else {
                methodName = "is" + StringUtils.capitalize(fieldName);
            }
        } else {
            methodName = "get" + StringUtils.capitalize(fieldName);
        }
        return methodName;
    }
    
    /**
     * 根据字段名推论得出字段对应的get方法<br/>
     *<功能详细描述>
     * @param getterName
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String getGetMethodNameByGetterNameAndType(String getterName,
            Class<?> getterType) {
        String methodName = null;
        if (boolean.class.isAssignableFrom(getterType)) {
            //利用length>2排除掉fieldName = is的情况，如果为is对应方法isIs setIs
            if (getterName.length() > 2 && getterName.startsWith("is")
                    && Character.isUpperCase(getterName.charAt(3))) {
                methodName = getterName;
            } else {
                methodName = "is" + StringUtils.capitalize(getterName);
            }
        } else {
            methodName = "get" + StringUtils.capitalize(getterName);
        }
        return methodName;
    }
    
    /**
      * 根据字段名获得对应的set方法<br/>
      *<功能详细描述>
      * @param field
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getSetMethodNameByField(Field field) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        String methodName = null;
        if (boolean.class.isAssignableFrom(fieldType)) {
            if (fieldName.length() > 2 && fieldName.startsWith("is")
                    && Character.isUpperCase(fieldName.charAt(3))) {
                methodName = "set"
                        + StringUtils.capitalize(fieldName.substring(2));
            } else {
                methodName = "set" + StringUtils.capitalize(fieldName);
            }
        } else {
            methodName = "set" + StringUtils.capitalize(fieldName);
        }
        return methodName;
    }

    /**
     * 根据字段名获得对应的set方法
     * @param fieldName
     * @param fieldType
     * @return
     */
    public static String getSetMethodName(String fieldName, Class<?> fieldType) {
        String methodName = null;
        if (boolean.class.isAssignableFrom(fieldType)) {
            if (fieldName.length() > 2 && fieldName.startsWith("is")
                    && Character.isUpperCase(fieldName.charAt(3))) {
                methodName = "set"
                        + StringUtils.capitalize(fieldName.substring(2));
            } else {
                methodName = "set" + StringUtils.capitalize(fieldName);
            }
        } else {
            methodName = "set" + StringUtils.capitalize(fieldName);
        }
        return methodName;
    }
    
    /**
      * 获取含有指定注解的getter集合<br/>
      *<功能详细描述>
      * @param type
      * @param annotationType
      * @return [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <A extends Annotation> List<String> getGetterNamesByAnnotationType(
            Class<?> type, Class<A> annotationType) {

        @SuppressWarnings("rawtypes")
        ClassReflector classReflector = ClassReflector.forClass(type);
        @SuppressWarnings("unchecked")
        Set<String> getterNames = classReflector.getGetterNames();
        
        List<String> resList = new ArrayList<String>();
        for (String getterNameTemp : getterNames) {
            if (!isHasAnnotationForGetter(type, getterNameTemp, annotationType)) {
                continue;
            }
            resList.add(getterNameTemp);
        }
        return resList;
    }
    
    /**
      * 获取指定类的属性（以及对应的getter方法上）上是否含有指定的注解
      *<功能详细描述>
      * @param type
      * @param getterName
      * @param annotationType
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <A extends Annotation> boolean isHasAnnotationForGetter(
            Class<?> type, String getterName, Class<A> annotationType) {
        A anno = getGetterAnnotation(type, getterName, annotationType);
        
        if (anno == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
      * 在对应类中是否存在对应属性的get方法
      *<功能详细描述>
      * @param type
      * @param getterName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isHasGetMethod(Class<?> type, String getterName) {
        @SuppressWarnings("rawtypes")
        ClassReflector reflector = ClassReflector.forClass(type);
        
        Method getterMethod = reflector.getGetterMethod(getterName);
        if (getterMethod != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
      * 在指定类中是否存在对应属性的set方法
      *<功能详细描述>
      * @param type
      * @param setterName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isHasSetMethod(Class<?> type, String setterName) {
        @SuppressWarnings("rawtypes")
        ClassReflector reflector = ClassReflector.forClass(type);
        
        Method setterMethod = reflector.getSetterMethod(setterName);
        if (setterMethod != null) {
            return true;
        } else {
            return false;
        }
        
    }
    
    /**
      * 获取某属性上的注解<br/>
      *<功能详细描述>
      * @param type
      * @param getterName
      * @param annotationType
      * @return [参数说明]
      * 
      * @return A [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <A extends Annotation> A getGetterAnnotation(Class<?> type,
            String getterName, Class<A> annotationType) {
        @SuppressWarnings("rawtypes")
        ClassReflector reflector = ClassReflector.forClass(type);
        
        A res = null;
        //如果注解是可被继承的则查找其父类及接口的方法
        if (annotationType.isAnnotationPresent(Inherited.class)) {
            @SuppressWarnings("unchecked")
            List<Method> getterMethodList = reflector.getGetterMethodList(getterName);
            if (getterMethodList != null) {
                for (Method getterMethodTemp : getterMethodList) {
                    res = getterMethodTemp.getAnnotation(annotationType);
                    
                    if (res != null) {
                        return res;
                    }
                }
            }
        } else {
            Method getterMethod = reflector.getGetterMethod(getterName);
            if (getterMethod != null) {
                res = getterMethod.getAnnotation(annotationType);
            }
            if (res != null) {
                return res;
            }
        }
        
        Field field = reflector.getFiled(getterName);
        if (field != null) {
            res = field.getAnnotation(annotationType);
        }
        return res;
    }
    
    /* 
     * 将父类所有的属性COPY到子类中。 
     * 类定义中child一定要extends father； 
     * 而且child和father一定为严格javabean写法，属性为deleteDate，方法为getDeleteDate 
     */  
    public void fatherToChild(Object father, Object child) throws Exception{
        if(!(child.getClass().getSuperclass() == father.getClass())){
            throw new Exception("child不是father的子类");
        }
        Class<?> fatherClass = father.getClass();
        Field ff[] = fatherClass.getDeclaredFields();
        for(int i = 0; i<ff.length; i++){
            Field f = ff[i];//取出每一个属性，如deleteDate
            //Class<?> type = f.getType();
            Method m = fatherClass.getMethod("get"+upperHeadChar(f.getName()));//方法getDeleteDate
            Object obj = m.invoke(father);//取出属性值
            f.set(child, obj);
        }
    }
    
    /** 
     * 首字母大写，in:deleteDate，out:DeleteDate 
     */  
    public String upperHeadChar(String in){  
        String head=in.substring(0,1);  
        String out=head.toUpperCase()+in.substring(1,in.length());  
        return out;  
    }
    
    /***
     * 获得一个类的方法的值
     * @param invoke 执行对象
     * @param methodName 方法名称
     * @return
     */
    public Object getValue(Object invoke, String methodName){
    	//执行后返回对象
    	Object obj = null;
    	//方法对象
    	Method m = null;
    	for(Class<?> clazz = invoke.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()){
    		try {
    			m = clazz.getDeclaredMethod("get"+methodName);
    		} catch (Exception e) {
    		}
    	}
    	
    	if(m != null){
    		try {
    			//调用方法
    			obj = m.invoke(invoke);
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		} catch (IllegalAccessException e) {
    			e.printStackTrace();
    		} catch (InvocationTargetException e) {
    			e.printStackTrace();
    		}
    	}
    	return obj;
    }
    
    /**
     * get the field value in object by fieldName
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName) {
    	Field field = getClassField(object.getClass(), fieldName);// get the field in this object  
    	if (field != null) {
    		field.setAccessible(true);
    		try {
    			return field.get(object);
    		} catch (Exception e) {
    			e.printStackTrace();
    			return null;
    		}
    	}
    	return null;
    }
    
    /**
     * 
     * @param aClazz
     * @param fieldName
     * @return
     */
    public static Field getClassField(Class<?> aClazz, String fieldName) {
    	Field[] declaredFields = aClazz.getDeclaredFields();
    	for (Field field : declaredFields) {
    		// 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String fieldName)，但是，失败了  
    		if (field.getName().equals(fieldName)) {
    			return field;// define in this class  
    		}
    	}
    	
    	Class<?> superclass = aClazz.getSuperclass();
    	if (superclass != null) {// 简单的递归一下
    		return getClassField(superclass, fieldName);
    	}
    	return null;
    }
    
}
