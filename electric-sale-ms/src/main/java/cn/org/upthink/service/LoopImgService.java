package cn.org.upthink.service;

import cn.org.upthink.persistence.mybatis.dto.Page;
import cn.org.upthink.persistence.mybatis.service.BaseCrudService;
import cn.org.upthink.persistence.mybatis.util.StringUtils;
//import cn.org.upthink.frame.modules.sys.utils.UserUtils;
import cn.org.upthink.mapper.LoopImgMapper;
import cn.org.upthink.entity.LoopImg;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class LoopImgService extends BaseCrudService<LoopImgMapper, LoopImg> {

    @Transactional(readOnly = false)
    public Page<LoopImg> findPage(Page<LoopImg> page, LoopImg loopImg) {
    return super.findPage(page, loopImg);
    }

    @Transactional(readOnly = false)
    public void save(LoopImg loopImg) {
        // 如果没有审核权限，则将当前内容改为待审核状态
        /*if (!UserUtils.getSubject().isPermitted("school:loopImg:audit")){
            loopImg.setDelFlag(LoopImg.DEL_FLAG_AUDIT);
        }*/
        //loopImg.setUpdateBy(UserUtils.getUser());
        loopImg.setUpdateDate(new Date());
        if (StringUtils.isBlank(loopImg.getId())){
            loopImg.preInsert();
            dao.insert(loopImg);
        }else{
            loopImg.preUpdate();
            dao.update(loopImg);
        }
    }

    @Transactional(readOnly = false)
    public void delete(LoopImg loopImg) {
        super.delete(loopImg);
    }

}
