package cn.org.upthink.gen;

import cn.org.upthink.gen.model.*;
import cn.org.upthink.gen.reflection.JpaMetaClass;
import cn.org.upthink.gen.util.FreeMarkerUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rover on 2017-01-12.
 */
public class JpaEntityFreeMarkerGenerator {

    private Class<?> loadTemplateClass = JpaEntityFreeMarkerGenerator.class;

    private String daoTemplateFilePath = "templates/modules/gen/mapper.ftl";
    private String serviceTemplateFilePath = "templates/modules/gen/service.ftl";
    private String controllerTemplateFilePath = "templates/modules/gen/controller.ftl";
    private String controllerTemplateFilePath2 = "templates/modules/gen/controller2.ftl";
    private String sqlMapDaoTemplateFilePath = "templates/modules/gen/SqlMapDao.ftl";
    private String dtoFormTemplateFilePath = "templates/modules/gen/DtoForm.ftl";
    private String dtoQueryTemplateFilePath = "templates/modules/gen/DtoQuery.ftl";

    private DaoGeneratorModel model4Dao;
    private ServiceGeneratorModel model4Service;
    private ControllerGeneratorModel model4Controller;

    private EntityGeneratorModel model4Entity;

    public void generate(Class<?> type, String resultFolderPath, boolean cleanFolder) {
        if(cleanFolder){
            File folder = new File(resultFolderPath);
            try {
                FileUtils.forceMkdir(folder.getCanonicalFile());
                FileUtils.cleanDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //生成Dao以及DaoImpl
        generateDao(type, resultFolderPath);
        //生成Service
        generateService(type, resultFolderPath);
        //FormDTO
        generateDtoForm(type, resultFolderPath);
        //QueryDTO
        generateDtoQuery(type, resultFolderPath);

        //生成控制层
        generateController(type, resultFolderPath);
        //生产mapper.xml
        generateSqlMapDao(type, resultFolderPath);
    }

    /**
     * 生成持久层逻辑
     * <功能详细描述>
     * @param type
     * @param resultFolderPath [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <TYPE> void generateDao(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        model4Dao = new DaoGeneratorModel();

        String daoPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../../mapper";
        daoPath = org.springframework.util.StringUtils.cleanPath(daoPath);

        model4Dao.setBasePackage(ClassUtils.convertResourcePathToClassName(daoPath));
        model4Dao.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model4Dao.setEntitySimpleTypeName(jpaMetaClass.getEntitySimpleName());
        model4Dao.setLowerCaseEntityTypeName(org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mapper", model4Dao);

        FreeMarkerUtils.fprint(loadTemplateClass,
                this.daoTemplateFilePath,
                data,
                resultFolderPath + "/" + daoPath + "/" + jpaMetaClass.getEntitySimpleName() + "Mapper.java");
    }


    /**
     * 生成业务层代码
     * <功能详细描述>
     * @param type
     * @param resultFolderPath [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <TYPE> void generateService(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        model4Service = new ServiceGeneratorModel();

        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../../service";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);

        model4Service.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        model4Service.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model4Service.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        model4Service.setLowerCaseEntitySimpleName(org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("service", model4Service);
        if(model4Dao!=null){
            data.put("mapper", model4Dao);
        }

        FreeMarkerUtils.fprint(loadTemplateClass,
                this.serviceTemplateFilePath,
                data,
                resultFolderPath + "/" + basePath + "/" + jpaMetaClass.getEntitySimpleName() + "Service.java");
    }

    /**
     * 生成控制层代码
     * <功能详细描述>
     * @param type
     * @param resultFolderPath [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <TYPE> void generateController(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        model4Controller = new ControllerGeneratorModel();

        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../../controller";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);

        SelectMapper fieldsMapper = new SelectMapper(jpaMetaClass);

        model4Controller.setBizName("redpacket");
        model4Controller.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        model4Controller.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model4Controller.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        model4Controller.setLowerCaseEntitySimpleName(org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("fieldList", fieldsMapper);
        data.put("controller", model4Controller);
        if(model4Service!=null){
            data.put("service", model4Service);
        }

        if(model4Entity!=null){
            data.put("dto", model4Entity);
        }

        FreeMarkerUtils.fprint(loadTemplateClass,
                this.controllerTemplateFilePath2,
                data,
                resultFolderPath + "/" + basePath + "/" + jpaMetaClass.getEntitySimpleName() + "Controller.java");
    }

    /**
     * 生成sqlMapDao
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <TYPE> void generateSqlMapDao(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        SqlMapMapper mapper = new SqlMapMapper(jpaMetaClass);
        InsertMapper insert = new InsertMapper(jpaMetaClass);
        SelectMapper select = new SelectMapper(jpaMetaClass);
        UpdateMapper update = new UpdateMapper(jpaMetaClass);

        select.setSimpleTableName("a");

        SelectMapper fieldsMapper = new SelectMapper(jpaMetaClass);

        String lowerEntityName = org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName());
        mapper.setTableName("tb_"+lowerEntityName);
        mapper.setId("id");
        mapper.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        mapper.setEntitySimpleTypeName(jpaMetaClass.getEntitySimpleName());
        mapper.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        mapper.setLowerCaseEntityTypeName(lowerEntityName);
        mapper.setLowerCaseEntitySimpleName(lowerEntityName);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mapper", mapper);
        data.put("insert", insert);
        data.put("select", select);
        data.put("update", update);
        data.put("fieldList", fieldsMapper);
        if(model4Dao!=null){
            String entityPackage = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/..";
            entityPackage = org.springframework.util.StringUtils.cleanPath(entityPackage);
            entityPackage = entityPackage.replaceAll("/",".");
            data.put("entityPackage", entityPackage);
            data.put("dao", model4Dao);
        }

        //org.springframework.util.StringUtils
        String sqlMapPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../../mybatis";
        sqlMapPath = org.springframework.util.StringUtils.cleanPath(sqlMapPath);

        FreeMarkerUtils.fprint(loadTemplateClass,
                sqlMapDaoTemplateFilePath,
                data,
                resultFolderPath + "/" + sqlMapPath + "/" + jpaMetaClass.getEntitySimpleName() + "Mapper.xml");
    }

    /**FormDTO*/
    public <TYPE> void generateDtoForm(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        model4Entity = new EntityGeneratorModel();

        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../../dto";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);

        EntityDtoForm fieldsMapper = new EntityDtoForm(jpaMetaClass);

        model4Entity.setBizName("gateway");
        model4Entity.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        model4Entity.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model4Entity.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        model4Entity.setLowerCaseEntitySimpleName(org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("fieldList", fieldsMapper);
        data.put("dto", model4Entity);
        if(model4Service!=null){
            data.put("service", model4Service);
        }

        FreeMarkerUtils.fprint(loadTemplateClass,
                this.dtoFormTemplateFilePath,
                data,
                resultFolderPath + "/" + basePath + "/" + jpaMetaClass.getEntitySimpleName() + "FormDTO.java");
    }

    /**QueryDTO*/
    public <TYPE> void generateDtoQuery(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        model4Entity = new EntityGeneratorModel();

        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName()) + "/../../dto";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);

        EntityDtoForm fieldsMapper = new EntityDtoForm(jpaMetaClass);

        model4Entity.setBizName("gateway");
        model4Entity.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        model4Entity.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model4Entity.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        model4Entity.setLowerCaseEntitySimpleName(org.apache.commons.lang3.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("fieldList", fieldsMapper);
        data.put("dto", model4Entity);
        if(model4Service!=null){
            data.put("service", model4Service);
        }

        FreeMarkerUtils.fprint(loadTemplateClass,
                this.dtoQueryTemplateFilePath,
                data,
                resultFolderPath + "/" + basePath + "/" + jpaMetaClass.getEntitySimpleName() + "QueryDTO.java");
    }


}
