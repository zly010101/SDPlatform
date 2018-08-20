package cn.org.upthink.service;

import cn.org.upthink.persistence.mybatis.dto.Page;
import cn.org.upthink.persistence.mybatis.service.BaseCrudService;
import cn.org.upthink.persistence.mybatis.util.StringUtils;
//import cn.org.upthink.frame.modules.sys.utils.UserUtils;
import cn.org.upthink.mapper.WxActivityTimeMapper;
import cn.org.upthink.test.WxActivityTime;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class WxActivityTimeService extends BaseCrudService<WxActivityTimeMapper, WxActivityTime> {

    @Transactional(readOnly = false)
    public Page<WxActivityTime> findPage(Page<WxActivityTime> page, WxActivityTime wxActivityTime) {
    return super.findPage(page, wxActivityTime);
    }

    @Transactional(readOnly = false)
    public void save(WxActivityTime wxActivityTime) {
        // 如果没有审核权限，则将当前内容改为待审核状态
        /*if (!UserUtils.getSubject().isPermitted("school:wxActivityTime:audit")){
            wxActivityTime.setDelFlag(WxActivityTime.DEL_FLAG_AUDIT);
        }*/
        //wxActivityTime.setUpdateBy(UserUtils.getUser());
        wxActivityTime.setUpdateDate(new Date());
        if (StringUtils.isBlank(wxActivityTime.getId())){
            wxActivityTime.preInsert();
            dao.insert(wxActivityTime);
        }else{
            wxActivityTime.preUpdate();
            dao.update(wxActivityTime);
        }
    }

    @Transactional(readOnly = false)
    public void delete(WxActivityTime wxActivityTime) {
        super.delete(wxActivityTime);
    }

}
