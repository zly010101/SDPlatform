package ${service.basePackage};

import com.kangmei.persistence.mybatis.dto.Page;
import com.kangmei.persistence.mybatis.service.BaseCrudService;
import com.kangmei.persistence.mybatis.util.StringUtils;
//import com.kangmei.frame.modules.sys.utils.UserUtils;
import ${mapper.basePackage}.${service.entitySimpleName}Mapper;
import ${service.entityTypeName};
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class ${service.entitySimpleName}Service extends BaseCrudService<${service.entitySimpleName}Mapper, ${service.entitySimpleName}> {

    @Transactional(readOnly = false)
    public Page<${service.entitySimpleName}> findPage(Page<${service.entitySimpleName}> page, ${service.entitySimpleName} ${service.lowerCaseEntitySimpleName}) {
    return super.findPage(page, ${service.lowerCaseEntitySimpleName});
    }

    @Transactional(readOnly = false)
    public void save(${service.entitySimpleName} ${service.lowerCaseEntitySimpleName}) {
        // 如果没有审核权限，则将当前内容改为待审核状态
        /*if (!UserUtils.getSubject().isPermitted("school:${service.lowerCaseEntitySimpleName}:audit")){
            ${service.lowerCaseEntitySimpleName}.setDelFlag(${service.entitySimpleName}.DEL_FLAG_AUDIT);
        }*/
        //${service.lowerCaseEntitySimpleName}.setUpdateBy(UserUtils.getUser());
        ${service.lowerCaseEntitySimpleName}.setUpdateDate(new Date());
        if (StringUtils.isBlank(${service.lowerCaseEntitySimpleName}.getId())){
            ${service.lowerCaseEntitySimpleName}.preInsert();
            dao.insert(${service.lowerCaseEntitySimpleName});
        }else{
            ${service.lowerCaseEntitySimpleName}.preUpdate();
            dao.update(${service.lowerCaseEntitySimpleName});
        }
    }

    @Transactional(readOnly = false)
    public void delete(${service.entitySimpleName} ${service.lowerCaseEntitySimpleName}) {
        super.delete(${service.lowerCaseEntitySimpleName});
    }

}
