package cn.org.upthink.controller;

import cn.org.upthink.model.dto.LoopImgFormDTO;
import cn.org.upthink.model.dto.LoopImgQueryDTO;
import cn.org.upthink.persistence.mybatis.dto.Page;
import cn.org.upthink.web.BaseController;
import cn.org.upthink.common.dto.BaseResult;
import cn.org.upthink.entity.LoopImg;
import cn.org.upthink.service.LoopImgService;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by rover on 2018-06-08.
*/
@Api(value="loopImgApi", description = "loopImg的接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping(value = "/v1")
public class LoopImgController extends BaseController {

    @Autowired
    private LoopImgService loopImgService;

    @ApiOperation(value ="获取loopImg详细信息", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loopImgId", value = "loopImgId编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/loopImg/{loopImgId}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> findLoopImg(@PathVariable("loopImgId") String loopImgId) {
        LoopImg loopImg = null;
        try {
            loopImg = loopImgService.get(loopImgId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loopImg!=null){
            return getBaseResultSuccess(loopImg, "有效对象");
        }else{
            return getBaseResultFail(null, "无效的loopImgId，没有获取到对象");
        }
    }

    @ApiOperation(value = "删除LoopImg信息", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loopImgId", value = "loopImgId编号", required = true, dataType = "String")
    })
    @DeleteMapping(value = "/loopImg/{loopImgId}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> deleteLoopImg(@PathVariable("loopImgId") String loopImgId) {
        LoopImg loopImg = null;
        try {
            loopImg = loopImgService.get(loopImgId);
            if(loopImg!=null){
                loopImgService.delete(loopImg);
                return getBaseResultSuccess(true, "已成功删除LoopImg对象");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "无效的loopImgId，没有删除LoopImg对象");
    }

    @ApiOperation(value="新增LoopImg", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/loopImg", produces = "application/json;charset=UTF-8")
    public BaseResult<?> addLoopImg(@ApiParam @RequestBody LoopImgFormDTO loopImgFormDTO) {
        try {
            LoopImg loopImg = new LoopImg();
            loopImg.setLoopUrl(loopImgFormDTO.getLoopUrl());
            loopImg.setLoopType(loopImgFormDTO.getLoopType());
            loopImg.setLoopName(loopImgFormDTO.getLoopName());
            loopImgService.save(loopImg);
            return getBaseResultSuccess(true, "保存LoopImg成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "保存失败");
    }

    @ApiOperation(value = "更新LoopImg", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loopImgId", value = "loopImgId编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/loopImg", produces = "application/json;charset=UTF-8")
    public BaseResult<?> updateLoopImg(
                     @RequestParam(value = "loopImgId", required = true, defaultValue = "") String loopImgId,
                     @ApiParam @RequestBody LoopImgFormDTO loopImgFormDTO) {
        try {
            if(loopImgService.get(loopImgId)==null){
                return getBaseResultFail(false, "操作失败，不存在的LoopImg。请传入有效的LoopImg的loopImgId。");
            }
            LoopImg loopImg = new LoopImg();
            loopImg.setId(loopImgId);
            loopImg.setLoopUrl(loopImgFormDTO.getLoopUrl());
            loopImg.setLoopType(loopImgFormDTO.getLoopType());
            loopImg.setLoopName(loopImgFormDTO.getLoopName());
            loopImgService.save(loopImg);
            return getBaseResultSuccess(true, "保存LoopImg成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "保存失败");
    }

    @ApiOperation(value = "LoopImg列表查询", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/loopImg", produces = "application/json;charset=UTF-8")
    public BaseResult<?> listLoopImg(HttpServletRequest request, HttpServletResponse response, @ApiParam LoopImgQueryDTO loopImgQueryDTO) {
        try {
            LoopImg loopImg = new LoopImg();
            loopImg.setLoopUrl(loopImgQueryDTO.getLoopUrl());
            loopImg.setLoopType(loopImgQueryDTO.getLoopType());
            loopImg.setLoopName(loopImgQueryDTO.getLoopName());
            Page<LoopImg> page = loopImgService.findPage(new Page<LoopImg>(request, response), loopImg);
            if(page.getList().isEmpty()){
                return getBaseResultSuccess(new ArrayList<LoopImg>(), "没有查询到有效的数据。");
            }
            return getBaseResultSuccess(page, "查询数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(null, "查询数据失败");
    }

}
