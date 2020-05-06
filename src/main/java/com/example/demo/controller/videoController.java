package com.example.demo.controller;

import com.example.demo.entity.ResultEntity;
import com.example.demo.entity.Sjbqxx;
import com.example.demo.entity.Wbczrzxx;
import com.example.demo.service.VideoService;
import com.example.demo.vo.StartVideoVo;
import com.example.demo.vo.StopVideoVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.Date;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.controller
 * @ClassName: videoController
 * @Author: sxtc
 * @Description: videoController
 * @Date: 2020/4/24 12:06
 * @LatestUpdate:
 * @Version: 1.0
 */
@RestController
@Api(value = "对外部系统提供的接口",description = "对外部系统提供的接口")
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
public class videoController {

    @Autowired
    private VideoService videoService;

    /**
     * 开启录像机
     * @param vo
     * @return
     */
    @PostMapping("/startVideo")
    @ApiOperation("开始录像")
    public ResultEntity startVideo(@RequestBody @ApiParam(name="vo",value="传入json格式",required=true) StartVideoVo vo){
        ResultEntity resultEntity = new ResultEntity();
        if(vo==null || StringUtils.isEmpty(vo.getUserAccount()) || StringUtils.isEmpty(vo.getMachineName()) || StringUtils.isEmpty(vo.getSystemNum())){
            resultEntity.setCode(400);
            resultEntity.setMessage("用户账号，主机名称，系统编号不能为空");
            return resultEntity;
        }
        Wbczrzxx wbczrzxx = new Wbczrzxx();
        wbczrzxx.setCzsj(new Date());//操作时间
        wbczrzxx.setJqmc(vo.getMachineName());//机器名称
        wbczrzxx.setCzlx("开始录像");
        wbczrzxx.setCzjk("startVideo");//操作接口
        wbczrzxx.setXtbh(vo.getSystemNum());//系统编号
        wbczrzxx.setYhzh(vo.getUserAccount());//用户账号
        wbczrzxx.setRzlx("操作日志");
        resultEntity=videoService.start(resultEntity,vo);
        System.err.println("==resultEntity===="+resultEntity.toString());
        wbczrzxx.setXysj(new Date());//响应时间
        videoService.addWbczrz(wbczrzxx);//添加日志
        return resultEntity;
    }

    /**
     * 停止录像
     * @param vo
     * @return
     */
    @PostMapping("/stopVideo")
    @ApiOperation("停止录像")
    public ResultEntity stopVideo(@RequestBody @ApiParam(name="vo",value="传入json格式",required=true) StopVideoVo vo){
        ResultEntity resultEntity = new ResultEntity();
        Wbczrzxx wbczrzxx = new Wbczrzxx();
        wbczrzxx.setCzsj(new Date());//操作时间
        wbczrzxx.setJqmc(vo.getMachineName());//机器名称
        wbczrzxx.setCzlx("停止录像");
        wbczrzxx.setCzjk("stopVideo");//操作接口
        wbczrzxx.setXtbh(vo.getSystemNum());//系统编号
        wbczrzxx.setYhzh(vo.getUserAccount());//用户账号
        wbczrzxx.setRzlx("操作日志");
        resultEntity=videoService.stop(resultEntity,vo);
        wbczrzxx.setXysj(new Date());//响应时间
        videoService.addWbczrz(wbczrzxx);//添加日志
        return resultEntity;
    }


    @GetMapping("/getSJBQInfo")
    @ApiOperation("根据条件查询事件集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sjid", value = "事件编号", required = false,paramType="query"),
            @ApiImplicitParam(name = "sjly", value = "事件来源", required = false, paramType="query"),
            @ApiImplicitParam(name = "sjlxdm", value = "事件类型代码", required = false, paramType="query"),
            @ApiImplicitParam(name = "jksxjbh", value = "监控摄像机编号", required = false, paramType="query"),
            @ApiImplicitParam(name = "czlx", value = "操作类型", required = false, paramType="query"),
            @ApiImplicitParam(name = "czyh", value = "操作用户", required = false, paramType="query"),
            @ApiImplicitParam(name = "jqmc", value = "机器名称", required = false, paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "分页单位", required = true, paramType="query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType="query")
    })
    public ResultEntity getSJBQInfo(String sjid,String sjly,String sjlxdm,String jksxjbh,String czlx,String czyh,String jqmc,Integer pageSize,Integer pageNum){
        Sjbqxx sjbqxx = new Sjbqxx();
        System.err.println(pageNum+"+======pageNum==");
        System.err.println(pageSize+"+======pageSize==");
        sjbqxx.setSjid(sjid);
        sjbqxx.setSjly(sjly);
        sjbqxx.setSjlxdm(sjlxdm);
        sjbqxx.setJksxjbh(jksxjbh);
        sjbqxx.setCzyh(czyh);
        sjbqxx.setJqmc(jqmc);
        sjbqxx.setCzlx(czlx);
        sjbqxx.setPageNum(pageNum);
        sjbqxx.setPageSize(pageSize);
        return videoService.getSJBQInfo(sjbqxx);
    }

}
