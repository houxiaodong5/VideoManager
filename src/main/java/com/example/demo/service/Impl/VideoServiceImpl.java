package com.example.demo.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.Scheduld.RunableContro;
import com.example.demo.entity.*;
import com.example.demo.dao.VideoMapper;
import com.example.demo.pojo.FY_pojo;
import com.example.demo.pojo.Fhcs_pojo;
import com.example.demo.pojo.Event;
import com.example.demo.service.VideoService;
import com.example.demo.util.CronUtil;
import com.example.demo.util.DealTime;
import com.example.demo.util.IscUtils;
import com.example.demo.vo.StartVideoVo;
import com.example.demo.vo.StopVideoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.service.Impl
 * @ClassName: VideoServiceImpl
 * @Author: sxtc
 * @Description: VideoServiceImpl
 * @Date: 2020/4/24 12:04
 * @LatestUpdate:
 * @Version: 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private RunableContro runableContro;

    @Value("${startVideoUrl}")
    private String startVideoUrl;
    @Value("${stopVideoUrl}")
    private String stopVideoUrl;
    @Value("${ip}")
    private String ip;
    @Value("${appkey}")
    private String appkey;
    @Value("${appsecret}")
    private String appsecret;
    private Map<String, Cfsj> wjxMap = new ConcurrentHashMap<>();//key：视频设备编号 value:开始结束时间对象(未开始的)
    private Map<String, Cfsj> yjxMap = new ConcurrentHashMap<>();//已进行的摄像机
    private Map<String, String> taskMap = new ConcurrentHashMap<>();//存放任务id,key:视频设备编号 value：任务编号

    private String cron = "1/10 * * * * ?";//默认每隔十秒执行一次扫描任务


    @Override
    public void getScheduledCs() {
        String jgsj = videoMapper.selectScheduledCs("videorefreshtime");
        cron = CronUtil.getCron(Integer.parseInt(jgsj));
    }

    @Override
    public void starCan() {
        runableContro.startCron1(cron);//开启定时扫描未进行摄像的摄像机线程
        runableContro.startCron2(cron);//开启定时扫描已进行摄像的摄像机线程
    }

    @Override
    public ResultEntity start(ResultEntity resultEntity, StartVideoVo vo) {
        System.err.println("====vo======"+vo.toString());
        resultEntity.setTimeFlag(new Date().getTime());
        resultEntity.setUserAccount(vo.getUserAccount());
        resultEntity.setMachineName(vo.getMachineName());
        resultEntity.setSystemNum(vo.getSystemNum());
        if (vo.getCameraIndexCodes() == null || vo.getCameraIndexCodes().length == 0) {
            resultEntity.setMessage("摄像机编号不能为空");
            resultEntity.setCode(400);
            return resultEntity;
        }
        String[] cameraIndexCodes = vo.getCameraIndexCodes();
        List<Fhcs_pojo> cameraIndexs = new ArrayList<>();
        String sjbh = null;
        if (!StringUtils.isEmpty(vo.getEventId())) {//事件编号不为空说明是内部调用
            String camera = "";
            for (int i = 0; i < cameraIndexCodes.length; i++) {
                camera += cameraIndexCodes[i] + ",";
            }
            camera = camera.substring(0, camera.length() - 1);
            Date sjkssj = DealTime.InteToDateTime(vo.getBeginTime());
            Date sjjssj = DealTime.InteToDateTime(vo.getEndTime());
            sjbh = vo.getEventId();
            //添加事件标签
            addSj(vo.getEventId(), vo.getSystemNum(), "splx001", sjkssj, sjjssj, "01", camera, vo.getUserAccount(), vo.getUserAccount(), vo.getMachineName());
        }
        Long beginTime = vo.getBeginTime();//获取开启时间（可变）
        Long endTime = vo.getEndTime();//获取结束时间（可变）
        Long finalBeginTime = beginTime;//不可变的开启时间
        Long finalEndTime = endTime;//不可变的结束时间
        Date beginDate = DealTime.InteToDateTime(finalBeginTime);//获取可用于存储事件的开启时间 （不可变）
        Date endDate = DealTime.InteToDateTime(finalEndTime); //获取可用于存储事件的结束时间 （不可变）
        long currentTime = new Date().getTime();//获取当前时间戳
        System.err.println("======currentTime==="+currentTime);
        if (endTime != null && (endTime < beginTime || endTime < currentTime)) {
            return null;
        }
        for (int i = 0; i < cameraIndexCodes.length; i++) {
            Fhcs_pojo fhcs_pojo = new Fhcs_pojo();
            try {
                //声明任务编号
                String taskId = null;
                int lxzt = 0;//声明录像状态
                //添加事件标签,返回事件编号
                if (StringUtils.isEmpty(vo.getEventId())) {
                    //添加事件标签信息
                    sjbh = UUID.randomUUID().toString();
                    addSj(sjbh, vo.getSystemNum(), "splx001", beginDate, endDate, "01", cameraIndexCodes[i], vo.getUserAccount(), vo.getUserAccount(), vo.getMachineName());
                }
                fhcs_pojo.setEventId(sjbh);//存放事件编号
                fhcs_pojo.setCameraIndexCode(cameraIndexCodes[i]);//存放摄像机编号
                if (finalBeginTime > currentTime) {//说明还不用开启
                    Cfsj cf = wjxMap.get(cameraIndexCodes[i]);//未进行
                    Cfsj cf2 = yjxMap.get(cameraIndexCodes[i]); //已进行
                    if (cf != null) { //若此摄像机已经在待开启状态中
                        if (cf.getStartTime() < finalBeginTime) {
                            beginTime = cf.getStartTime();//获取比较小的开启时间
                        }
                        if (cf.getEndTime() == null || (finalEndTime != null && finalEndTime < cf.getEndTime())) {
                            endTime = cf.getEndTime();//若结束时间为空则需要手动关闭，不受时间限制
                        }
                        cf.setStartTime(beginTime);//存放关于此摄像机的最小开启时间
                        cf.setEndTime(endTime);//存放关于此摄像机的最大关闭时间
                        wjxMap.put(cameraIndexCodes[i], cf);
                        lxzt = 0;//录像状态设为0未开启
                        updateLXsjxx(cameraIndexCodes[i],DealTime.InteToDateTime(beginTime),DealTime.InteToDateTime(endTime),lxzt,taskId);//修改录像时间信息表
                    } else if (cf2 != null) {//说明已经开启
                        if (cf2.getEndTime() == null || cf2.getEndTime() > endTime) {
                            endTime = cf2.getEndTime();//取最大结束时间
                        }
                        cf2.setEndTime(endTime);
                        yjxMap.put(cameraIndexCodes[i], cf2);//更新结束时间
                        lxzt = 1;//已开启
                        taskId = taskMap.get(cameraIndexCodes[i]);
                        updateLXsjxx(cameraIndexCodes[i],DealTime.InteToDateTime(beginTime),DealTime.InteToDateTime(endTime),lxzt,taskId);//修改录像时间信息
                    } else {//说明还未开启并且没有用户使用过
                        Cfsj cfsj = new Cfsj();
                        cfsj.setStartTime(beginTime);
                        cfsj.setEndTime(endTime);
                        wjxMap.put(cameraIndexCodes[i], cfsj);
                        lxzt = 0;
                        //添加录像时间信息
                        addLXsjxx(cameraIndexCodes[i],beginDate,endDate,lxzt,taskId);
                    }
                    fhcs_pojo.setMessage("OK");
                    fhcs_pojo.setCode(200);
                } else {//开始录像
                    //判断此摄像机是否已经开启
                    Cfsj cf = yjxMap.get(cameraIndexCodes[i]);
                    Cfsj cf2 = wjxMap.get(cameraIndexCodes[i]);
                    if (cf != null) {//说明已经开启
                        if (cf.getEndTime() == null || (endTime != null && endTime < cf.getEndTime())) {
                            endTime = cf.getEndTime();//若结束时间为空则需要手动关闭，不受时间限制
                        }
                        cf.setEndTime(endTime);
                        lxzt = 1;
                        yjxMap.put(cameraIndexCodes[i], cf);
                        taskId = taskMap.get(cameraIndexCodes[i]);
                        updateLXsjxx(cameraIndexCodes[i],DealTime.InteToDateTime(beginTime),DealTime.InteToDateTime(endTime),lxzt,taskId);//修改录像时间信息表数据
                        fhcs_pojo.setMessage("OK");
                        fhcs_pojo.setCode(200);
                    } else {//说明还未开启
                        taskId = startVideo(cameraIndexCodes[i]);//开启录像
                        Cfsj cfsj = new Cfsj();
                        cfsj.setStartTime(beginTime);
                        cfsj.setEndTime(endTime);
                        if (taskId != null) {//启动成功
                            if (cf2 != null) {//说明已经有关于此摄像机的信息
                                if (cf2.getEndTime() == null || (endTime != null && endTime < cf2.getEndTime())) {
                                    endTime = cf2.getEndTime();//若结束时间为空则需要手动关闭，不受时间限制
                                }
                                //移除此摄像机的未进行状态信息
                                wjxMap.remove(cameraIndexCodes[i]);
                                updateLXsjxx(cameraIndexCodes[i],beginDate,DealTime.InteToDateTime(endTime),1,taskId);//修改录像时间信息
                            } else {//说明这是第一个人操作此摄像机
                                addLXsjxx(cameraIndexCodes[i],beginDate,endDate,1,taskId);//添加录像时间信息
                            }
                            lxzt = 1;
                            yjxMap.put(cameraIndexCodes[i], cfsj);
                            taskMap.put(cameraIndexCodes[i], taskId);
                            fhcs_pojo.setMessage("OK");
                            fhcs_pojo.setCode(200);
                        } else {//启动失败
                            lxzt = 3;//状态为 录像异常
                            if (cf2 != null) {//说明已经有关于此摄像机的信息
                                deleteLXsjxx(cameraIndexCodes[i]);//则删除关于此摄像机的录像时间信息
                                wjxMap.remove(cameraIndexCodes[i]);
                            }
                            fhcs_pojo.setMessage("摄像机开启失败");
                            fhcs_pojo.setCode(400);
                            System.err.println("=====开启摄像机失败===并且不在运行状态中==");
                        }

                    }
                }
                addLXglxx(sjbh, cameraIndexCodes[i], beginDate, endDate, lxzt, taskId);//添加摄像关联信息表(事件编号，视频设备编号，开始结束时间,录像状态，任务编号)
                fhcs_pojo.setBeginTime(beginTime);
                fhcs_pojo.setTaskID(taskId == null ? "0" : taskId);

            } catch (Exception e) {
                e.printStackTrace();
                fhcs_pojo.setCode(400);
                fhcs_pojo.setMessage("Fail");

            }
            cameraIndexs.add(fhcs_pojo);

        }
        Map<String, List<Fhcs_pojo>> map = new HashMap<>();
        map.put("cameraIndexs",cameraIndexs);
        resultEntity.setData(map);
        return resultEntity;
    }

    /**
     * 删除录像时间信息表
     *
     * @param cameraIndexCode
     */
    private void deleteLXsjxx(String cameraIndexCode) {
        videoMapper.deleteLxsjxx(cameraIndexCode);
    }


    @Override
    public void dealWjxVideo() {

        System.err.println("======扫描未进行工作的摄像机==="+new Date().toLocaleString());
        try {
            //监控未进行的摄像机
            if (wjxMap.size() > 0) {
                List<String> keyList = new ArrayList<>(); //存放未进行状态转已进行状态的key(摄像机编号)
                Set<Map.Entry<String, Cfsj>> entries = wjxMap.entrySet();
                Iterator<Map.Entry<String, Cfsj>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Cfsj> next = iterator.next();
                    Cfsj cfsj = next.getValue();
                    long time = new Date().getTime();
                    if (time >= cfsj.getStartTime()) {
                        //开始录像
                        String taskId = startVideo(next.getKey());
                        if (taskId != null) {//启动成功
                            taskMap.put(next.getKey(), taskId);//存放任务编号
                            yjxMap.put(next.getKey(), cfsj);//存放到已进行的map中
                            //根据视频设备编号修改录像管理信息表状态为1，任务编号
                            Lxglxx lxglxx = new Lxglxx();
                            lxglxx.setLxzt(1);
                            lxglxx.setLxrwbh(taskId);
                            lxglxx.setSpsbbh(next.getKey());
                            updateLXglxxBySBBH(lxglxx);
                            //修改录像时间信息表
                            updateLXsjxx(next.getKey(),null,null,1,taskId);

                        } else {//开启失败
                            Lxglxx lxglxx = new Lxglxx();
                            lxglxx.setLxzt(3);
                            lxglxx.setLxrwbh(null);
                            lxglxx.setSpsbbh(next.getKey());
                            updateLXglxxBySBBH(lxglxx);
                        }
                        keyList.add(next.getKey());
                    }
                }
                    //清除未进行转已进行的摄像机数据
                    for (int i = 0; i < keyList.size(); i++) {
                        wjxMap.remove(keyList.get(i));
                    }
                    keyList.clear();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dealYjxVideo() {
        System.err.println("======扫描已进行工作的摄像机==="+new Date().toLocaleString());
        try {
            //监控已进行的摄像机
            if (yjxMap.size() > 0) {
                List<String> keyList = new ArrayList<>(); //存放未进行状态转已进行状态的key(摄像机编号)
                Set<Map.Entry<String, Cfsj>> entries = yjxMap.entrySet();
                Iterator<Map.Entry<String, Cfsj>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Cfsj> next = iterator.next();
                    Cfsj cfsj = next.getValue();
                    long time = new Date().getTime();
                    if (cfsj.getEndTime() != null && time >= cfsj.getEndTime()) {
                        //结束录像
                        String taskId = taskMap.get(next.getKey());//获取此摄像机的任务编号
                        Integer flag = stopVideo(next.getKey(), taskId);//停止录像
                        if (flag == 1) {//停止成功
                            //根据视频设备编号修改录像管理信息表状态为2
                            Lxglxx lxglxx = new Lxglxx();
                            lxglxx.setLxzt(2);
                            lxglxx.setLxrwbh(taskId);
                            lxglxx.setSpsbbh(next.getKey());
                            updateLXglxxBySBBH(lxglxx);//修改录像管理信息表状态
                            taskMap.remove(next.getKey());//移除任务编号
                            keyList.add(next.getKey());

                            //删除录像时间信息表
                            deleteLxsjxx(next.getKey());
                        } else {//停止失败
                            System.err.println("停止失败");
                        }

                    }
                }
                    //清除已经结束的摄像机数据
                    for (int i = 0; i < keyList.size(); i++) {
                        yjxMap.remove(keyList.get(i));
                    }
                    keyList.clear();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectLXsjxx() {
        //查询未进行的摄像机
        List<Lxsjxx> wjxlist = videoMapper.selectLxsjxx(0);
        for (Lxsjxx lxsjxx : wjxlist) {
            Cfsj cfsj = new Cfsj();
            cfsj.setStartTime(lxsjxx.getZxlxsj().getTime());
            if (lxsjxx.getZdlxsj() != null) {
                cfsj.setEndTime(lxsjxx.getZdlxsj().getTime());
            } else {
                cfsj.setEndTime(null);
            }
            System.err.println(lxsjxx.getSpsbbh() + "=====" + lxsjxx.getZxlxsj() + "====" + lxsjxx.getZdlxsj() + "====" + lxsjxx.getZt());
            wjxMap.put(lxsjxx.getSpsbbh(), cfsj);
        }
        //查询已进行的摄像机
        List<Lxsjxx> yjxlist = videoMapper.selectLxsjxx(1);
        for (Lxsjxx lxsjxx : yjxlist) {
            Cfsj cfsj = new Cfsj();
            cfsj.setStartTime(lxsjxx.getZxlxsj().getTime());
            if (lxsjxx.getZdlxsj() != null) {
                cfsj.setEndTime(lxsjxx.getZdlxsj().getTime());
            } else {
                cfsj.setEndTime(null);
            }
            System.err.println(lxsjxx.getSpsbbh() + "=====" + lxsjxx.getZxlxsj().toLocaleString() + "====" + lxsjxx.getZdlxsj().toString() + "====" + lxsjxx.getZt());
            yjxMap.put(lxsjxx.getSpsbbh(), cfsj);
            taskMap.put(lxsjxx.getSpsbbh(), lxsjxx.getTaskId());
        }
    }

    @Override
    public ResultEntity stop(ResultEntity resultEntity, StopVideoVo vo) {

        resultEntity.setTimeFlag(new Date().getTime());//存放时间戳
        if (vo == null || StringUtils.isEmpty(vo.getSystemNum()) || StringUtils.isEmpty(vo.getMachineName()) || StringUtils.isEmpty(vo.getUserAccount())) {
            resultEntity.setMessage("用户账号或系统编号或主机名不能为空");
            resultEntity.setCode(400);
            return resultEntity;
        }
        resultEntity.setMachineName(vo.getMachineName());//存放主机名
        resultEntity.setSystemNum(vo.getSystemNum());//存放系统编号
        resultEntity.setUserAccount(vo.getUserAccount());//存放用户账号
        List<Event> pojos = vo.getCameraIndexCodes();
        if (pojos == null || pojos.size() == 0) {
            resultEntity.setCode(400);
            resultEntity.setMessage("视频设备编号，事件编号，任务编号不能为空");
            return resultEntity;
        }
        List<Fhcs_pojo> cameraIndexCodes = new ArrayList<>();
        for (int i = 0; i < pojos.size(); i++) {
            Fhcs_pojo fhcs_pojo = new Fhcs_pojo();
            try {
                Event pojo = pojos.get(i);
                if (StringUtils.isEmpty(pojo.getCameraIndexCode()) || StringUtils.isEmpty(pojo.getEventId()) || StringUtils.isEmpty(pojo.getTaskID())) {
                    fhcs_pojo.setCode(400);
                    fhcs_pojo.setMessage("摄像机编号或事件编号或任务编号不能为空");
                    cameraIndexCodes.add(fhcs_pojo);
                } else {//符合条件
                    fhcs_pojo = new Fhcs_pojo();
                    fhcs_pojo.setCameraIndexCode(pojo.getCameraIndexCode());
                    fhcs_pojo.setEventId(pojo.getEventId());
                    fhcs_pojo.setTaskID(pojo.getTaskID());
                    Cfsj cfsj = wjxMap.get(pojo.getCameraIndexCode());
                    Cfsj cfsj1 = yjxMap.get(pojo.getCameraIndexCode());
                    if (cfsj != null) {//此摄像机还未开启录像
                        fhcs_pojo.setMessage("该摄像机还未开启录像");
                        fhcs_pojo.setCode(400);
                        fhcs_pojo.setEndTime(cfsj.getEndTime());

                    } else if (cfsj1 != null) {//此摄像机正在录像
                        Long endTime = cfsj1.getEndTime();
                        long time = new Date().getTime();
                        if (endTime == null || endTime <= time) {//可以手动停止
                            Integer integer = stopVideo(pojo.getCameraIndexCode(), pojo.getTaskID());
                            if (integer == 1) {
                                yjxMap.remove(pojo.getCameraIndexCode());//移除此摄像机时间信息
                                taskMap.remove(pojo.getCameraIndexCode());//移除此摄像机任务信息
                                Lxglxx lxglxx = new Lxglxx();
                                lxglxx.setLxzt(2);
                                lxglxx.setLxrwbh(pojo.getTaskID());
                                lxglxx.setSpsbbh(pojo.getCameraIndexCode());
                                updateLXglxxBySBBH(lxglxx);//修改此录像机的状态为停止2
                                deleteLxsjxx(pojo.getCameraIndexCode());//删除此录像机的录像时间信息
                                fhcs_pojo.setMessage("OK");
                                fhcs_pojo.setCode(200);
                                fhcs_pojo.setEndTime(time);
                                //修改此事件标签
                                updateSj(pojo.getEventId(), null, null, null, new Date(), null, null, null, null, null);
                            } else {
                                fhcs_pojo.setMessage("停止录像操作失败");
                                fhcs_pojo.setCode(400);
                            }
                        } else {//不可以手动停止，因为当前时间不是最大预定结束时间
                            fhcs_pojo.setMessage("此摄像机已有其他用户使用，不可停止录像");
                            fhcs_pojo.setCode(400);
                        }
                    } else {
                        fhcs_pojo.setMessage("此录像机已停止");
                        fhcs_pojo.setCode(200);
                    }
                    cameraIndexCodes.add(fhcs_pojo);
                }
            } catch (Exception e) {
                fhcs_pojo.setMessage("Fail");
                fhcs_pojo.setCode(400);
                cameraIndexCodes.add(fhcs_pojo);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("cameraIndexCodes", cameraIndexCodes);
        resultEntity.setData(map);
        return resultEntity;
    }

    /**
     * 修改事件标签信息
     *
     * @param sjid
     * @param sjly
     * @param sjlxdm
     * @param sjkssj
     * @param sjjssj
     * @param czlx
     * @param jksxjbh
     * @param sjyh
     * @param czyh
     * @param jqmc
     */
    private void updateSj(String sjid, String sjly, String sjlxdm, Date sjkssj, Date sjjssj, String czlx, String jksxjbh, String sjyh, String czyh, String jqmc) {
        try {
            Sjbqxx sjbqxx = new Sjbqxx();
            sjbqxx.setSjid(sjid);
            sjbqxx.setSjjssj(sjjssj);
            sjbqxx.setSjly(sjly);
            sjbqxx.setSjlxdm(sjlxdm);
            sjbqxx.setSjkssj(sjkssj);
            sjbqxx.setCzlx(czlx);
            sjbqxx.setJksxjbh(jksxjbh);
            sjbqxx.setSjyh(sjyh);
            sjbqxx.setCzyh(czyh);
            sjbqxx.setJqmc(jqmc);
            videoMapper.updateSj(sjbqxx);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ResultEntity getSJBQInfo(Sjbqxx sjbqxx) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            //根据条件查询数据列表
            Integer pageNum = sjbqxx.getPageNum();
            Integer pageSize = sjbqxx.getPageSize();
            sjbqxx.setPageNum((pageNum - 1) * pageSize);
            List<Sjbqxx> actionList = videoMapper.selectSJbqInfo(sjbqxx);
            //查询符合条件的数据条数
            Integer count = videoMapper.selectSJbqCount(sjbqxx);
            resultEntity.setTimeFlag(new Date().getTime());
            FY_pojo fy_pojo = new FY_pojo();
            fy_pojo.setCount(count);
            fy_pojo.setData(actionList);
            fy_pojo.setPageNum(pageNum);
            fy_pojo.setPageSize(pageSize);
            resultEntity.setData(fy_pojo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultEntity;
    }

    /**
     * 删除录像时间信息表
     *
     * @param key
     */
    private void deleteLxsjxx(String key) throws Exception {
        videoMapper.deleteLxsjxx(key);
    }


    /**
     * 添加录像时间信息表
     *
     * @param
     */
    private void addLXsjxx(String spsbbh,Date zxlxsj,Date zdlxsj,Integer zt,String taskId) throws Exception {
        Lxsjxx lxsjxx = new Lxsjxx();
        lxsjxx.setTaskId(taskId);
        lxsjxx.setZt(zt);
        lxsjxx.setZxlxsj(zxlxsj);
        lxsjxx.setZdlxsj(zdlxsj);
        lxsjxx.setSpsbbh(spsbbh);
        videoMapper.insertLxsjxx(lxsjxx);
    }

    /**
     * 修改录像时间信息表
     *
     * @param
     */

    private void updateLXsjxx(String spsbbh,Date zxlxsj,Date zdlxsj,Integer zt,String taskId) throws Exception {
        Lxsjxx lxsjxx = new Lxsjxx();
        lxsjxx.setTaskId(taskId);
        lxsjxx.setZt(zt);
        lxsjxx.setZxlxsj(zxlxsj);
        lxsjxx.setZdlxsj(zdlxsj);
        lxsjxx.setSpsbbh(spsbbh);
        videoMapper.updateLXsjxx(lxsjxx);
    }


    /**
     * 根据视频设备编号修改录像管理信息表的状态和任务编号
     *
     * @param lxglxx
     */
    private void updateLXglxxBySBBH(Lxglxx lxglxx) throws Exception {
        videoMapper.updateLXglxxBySBBH(lxglxx);
    }

    /**
     * 添加录像管理信息表
     *
     * @param
     */

    public void addLXglxx(String sjid, String spsbbh, Date kssj, Date jssj, Integer lxzt, String lxrwbh) throws Exception {
        Lxglxx lxglxx = new Lxglxx();
        lxglxx.setSjid(sjid);
        lxglxx.setSpsbbh(spsbbh);
        lxglxx.setLxrwbh(lxrwbh);
        lxglxx.setJssj(jssj);
        lxglxx.setLxzt(lxzt);
        lxglxx.setKssj(kssj);
        videoMapper.insertLxglxx(lxglxx);
    }

    /**
     * 添加事件标签
     *
     * @param
     * @return
     */
    private void addSj(String sjid, String sjly, String sjlxdm, Date sjkssj, Date sjjssj, String czlx, String jksxjbh, String sjyh, String czyh, String jqmc) {

        try {
            Sjbqxx sjbqxx = new Sjbqxx();
            sjbqxx.setSjid(sjid);
            sjbqxx.setSjjssj(sjjssj);
            sjbqxx.setSjly(sjly);
            sjbqxx.setSjlxdm(sjlxdm);
            sjbqxx.setSjkssj(sjkssj);
            sjbqxx.setCzlx(czlx);
            sjbqxx.setJksxjbh(jksxjbh);
            sjbqxx.setSjyh(sjyh);
            sjbqxx.setCzyh(czyh);
            sjbqxx.setJqmc(jqmc);
            videoMapper.insertSj(sjbqxx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加外部操作日志信息
     *
     * @param wbczrzxx
     */
    @Override
    public void addWbczrz(Wbczrzxx wbczrzxx) {
        try {
            videoMapper.insertWbczrzxx(wbczrzxx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录像
     *
     * @param cameraIndexCode
     */
    @Override
    public synchronized String startVideo(String cameraIndexCode) {

        String taskId = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cameraIndexCode", cameraIndexCode);
            jsonObject.put("recordType", 6);
            jsonObject.put("type", 0);
            String response = IscUtils.getResult(ip, appkey, appsecret, startVideoUrl, jsonObject);
            System.err.println("=======response=====" + response);
            JSONObject result1Object = JSONObject.parseObject(response);
            if (result1Object != null && result1Object.get("code").equals("0")) {
                taskId = JSONObject.parseObject(result1Object.get("data").toString(), String.class);
                System.err.println("======================处理成功");
            } else {
                System.err.println("=======================处理失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("==========================处理失败");
        }
        return taskId;
    }

    @Override
    public Integer stopVideo(String cameraIndexCode, String taskId) {
        Integer flag = 0;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cameraIndexCode", cameraIndexCode);
            jsonObject.put("type", 0);
            jsonObject.put("taskID", taskId);
            String response = IscUtils.getResult(ip, appkey, appsecret, stopVideoUrl, jsonObject);
            JSONObject result1Object = JSONObject.parseObject(response);
            if (result1Object != null && result1Object.get("code").equals("0")) {
                flag = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("======================操作失败");
        }
        return flag;
    }

    public static void main(String[] args) {
        System.err.println(new Date().getTime());
    }
}
