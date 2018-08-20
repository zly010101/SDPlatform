package cn.org.upthink.controller;

import cn.org.upthink.persistence.mybatis.dto.Page;
import cn.org.upthink.web.BaseController;
import cn.org.upthink.common.dto.BaseResult;
import cn.org.upthink.test.WxActivityTime;
import cn.org.upthink.dto.WxActivityTimeFormDTO;
import cn.org.upthink.dto.WxActivityTimeQueryDTO;
import cn.org.upthink.service.WxActivityTimeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//import cn.org.upthink.frame.modules.sys.utils.UserUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
* Created by rover on 2018-06-08.
*/
@Api(value="wxActivityTimeApi", description = "wxActivityTime的接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping(value = "/redpacket")
public class WxActivityTimeController extends BaseController {

    @Autowired
    private WxActivityTimeService wxActivityTimeService;

    @ApiOperation(value ="获取wxActivityTime详细信息", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/wxActivityTime/{id}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> findWxActivityTime(@PathVariable("id") String id) {
        WxActivityTime wxActivityTime = null;
        try {
            wxActivityTime = wxActivityTimeService.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(wxActivityTime!=null){
            return getBaseResultSuccess(wxActivityTime, "有效对象");
        }else{
            return getBaseResultFail(null, "无效的id，没有获取到对象");
        }
    }

    @ApiOperation(value = "删除WxActivityTime信息", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id编号", required = true, dataType = "String")
    })
    @DeleteMapping(value = "/wxActivityTime/{id}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> deleteWxActivityTime(@PathVariable("id") String id) {
        WxActivityTime wxActivityTime = null;
        try {
            wxActivityTime = wxActivityTimeService.get(id);
            if(wxActivityTime!=null){
                wxActivityTimeService.delete(wxActivityTime);
                return getBaseResultSuccess(true, "已成功删除WxActivityTime对象");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "无效的id，没有删除WxActivityTime对象");
    }

    @ApiOperation(value="新增WxActivityTime", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/wxActivityTime", produces = "application/json;charset=UTF-8")
    public BaseResult<?> addWxActivityTime(@ApiParam @RequestBody WxActivityTimeFormDTO wxActivityTimeFormDTO) {
        try {
            WxActivityTime wxActivityTime = new WxActivityTime();
            wxActivityTime.setActId(wxActivityTimeFormDTO.getActId());
            wxActivityTime.setActName(wxActivityTimeFormDTO.getActName());
            wxActivityTimeService.save(wxActivityTime);
            return getBaseResultSuccess(true, "保存WxActivityTime成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "保存失败");
    }

    @ApiOperation(value = "更新WxActivityTime", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/wxActivityTime", produces = "application/json;charset=UTF-8")
    public BaseResult<?> updateWxActivityTime(
                     @RequestParam(value = "id", required = true, defaultValue = "") String id,
                     @ApiParam @RequestBody WxActivityTimeFormDTO wxActivityTimeFormDTO) {
        try {
            if(wxActivityTimeService.get(id)==null){
                return getBaseResultFail(false, "操作失败，不存在的WxActivityTime。请传入有效的WxActivityTime的ID。");
            }
            WxActivityTime wxActivityTime = new WxActivityTime();
            wxActivityTime.setId(id);
            wxActivityTime.setActId(wxActivityTimeFormDTO.getActId());
            wxActivityTime.setActName(wxActivityTimeFormDTO.getActName());
            wxActivityTimeService.save(wxActivityTime);
            return getBaseResultSuccess(true, "保存WxActivityTime成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "保存失败");
    }

    @ApiOperation(value = "WxActivityTime列表查询", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/wxActivityTime", produces = "application/json;charset=UTF-8")
    public BaseResult<?> listWxActivityTime(HttpServletRequest request, HttpServletResponse response, @ApiParam WxActivityTimeQueryDTO wxActivityTimeQueryDTO) {
        try {
            WxActivityTime wxActivityTime = new WxActivityTime();
            Page<WxActivityTime> page = wxActivityTimeService.findPage(new Page<WxActivityTime>(request, response), wxActivityTime);
            if(page.getList().isEmpty()){
                return getBaseResultSuccess(new ArrayList<WxActivityTime>(), "没有查询到有效的数据。");
            }
            return getBaseResultSuccess(page, "查询数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(null, "查询数据失败");
    }

}
