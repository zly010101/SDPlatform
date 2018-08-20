package cn.org.upthink.gen.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import cn.org.upthink.gen.exception.ResourceAccessException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * freeMarker工具类
 * <功能详细描述>
 */
public class FreeMarkerUtils {
    
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtils.class);
    
    /** 模版缓存 */
    private static Map<String, Template> templateCache = new HashMap<String, Template>();
    
    // 指定要在ftl页面使用的静态包名  
    public static TemplateHashModel useStaticPackage(String packageName) {
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel fileStatics = null;
        try {
            fileStatics = (TemplateHashModel) staticModels.get(packageName);
        } catch (TemplateModelException e) {
        }
        return fileStatics;
    }
    
    private static void rootFilter(Map<String, Object> params){
        //params.put("ObjectUtils", useStaticPackage(StringUtils.class.getName()));//ObjectUtils
        params.put("StringUtils", useStaticPackage(StringUtils.class.getName()));
    }
    
    /**
      * 获取模板
      * <功能详细描述>
      * @param filePath 模版所在类路径
      * @return [参数说明]
      * 
      * @return Template [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Template getTemplateByTemplateClassPath(Class<?> loadClass, String filePath) {
        if (templateCache.containsKey(filePath)) {
            return templateCache.get(filePath);
        }
        
        try {
            //通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            
            //设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(loadClass, "/");
            
            //在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(filePath, "UTF-8");
            
            templateCache.put(filePath, temp);
            return temp;
        } catch (IOException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("加载freeMarker模版.发生IOException", e);
        }
    }
    
    /**
      * 根据数据以及文件生成模版
      * <功能详细描述>
      *
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void fprint(Class<?> loadClass, String filePath, Map<String, Object> root, String outFilePath) {
        FileWriter out = null;
        try {
            //通过一个文件输出流，就可以写到相应的文件中
            File newFile = new File(outFilePath);
            if (!newFile.exists()) {
                FileUtils.forceMkdir(newFile.getParentFile());
                newFile.createNewFile();
            }
            out = new FileWriter(newFile);
            
            Template temp = getTemplateByTemplateClassPath(loadClass, filePath);
            
            rootFilter(root);
            
            temp.process(root, out);
        } catch (IOException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("根据freeMarker模版生成目标文件.发生IOException", e);
        } catch (TemplateException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("根据freeMarker模版生成目标文件.发生TemplateException", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    
    /**
     * 根据数据以及文件生成模版
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void fprint(Class<?> loadClass, String filePath, Map<String, Object> root, String outFilePath, String encode) {
        OutputStreamWriter out = null;
        try {
            //通过一个文件输出流，就可以写到相应的文件中
            File newFile = new File(outFilePath);
            if (!newFile.exists()) {
                FileUtils.forceMkdir(newFile.getParentFile());
                newFile.createNewFile();
            }
            out = new OutputStreamWriter(new FileOutputStream(newFile), encode);
            
            Template temp = getTemplateByTemplateClassPath(loadClass, filePath);
            
            rootFilter(root);
            
            temp.process(root, out);
        } catch (IOException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("根据freeMarker模版生成目标文件.发生IOException", e);
        } catch (TemplateException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("根据freeMarker模版生成目标文件.发生TemplateException", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    
    /**
     * 根据数据以及文件生成模版
     * <功能详细描述>
     * @param root
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String generateContent(Class<?> loadClass, String templateFilePath, Map<String, Object> root, String encode) {
        StringWriter out = new StringWriter();
        try {
            Template temp = getTemplateByTemplateClassPath(loadClass, templateFilePath);
            
            rootFilter(root);
            
            temp.process(root, out);
            
            String scriptContent = out.getBuffer().toString();
            return scriptContent;
        } catch (IOException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("根据freeMarker模版生成目标文件.发生IOException", e);
        } catch (TemplateException e) {
            logger.error(e.toString(), e);
            throw new ResourceAccessException("根据freeMarker模版生成目标文件.发生TemplateException", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    
}
