/*
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：lb
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
package com.cityos.lb.run;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cityos.assign.Asn.Assign;
import com.cityos.lb.assin.TaskCollect;
import com.cityos.lb.bus.buscenter.JobEventBusCenter;
import com.cityos.lb.bus.impl.JobEventlbsDefaultListener;
import com.cityos.lb.meta.LbSchemal;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author Andy on 2020/12/9 18:54
 * At Jd
 */

@Slf4j
public class LbSystem {
    Assign assign;
    JobEventlbsDefaultListener jobEventlbsDefaultListener;
    TaskCollect collect;

    public LbSystem() throws Exception {
        this.assign = new Assign();
        this.jobEventlbsDefaultListener = new JobEventlbsDefaultListener();
        this.collect = new TaskCollect();
    }

    //TODO  提供消息注册方法eventBus de register
    //TODO  判断策略和period 的时间是否更新(对于更新周期的设置暂且搁置)
    //TODO  提供数据获取的接口
    public void start() throws Exception {
        /***
         * lb 总线
         */
        log.info("lb 任务总线工作开始工作");
        JobEventBusCenter.register(jobEventlbsDefaultListener);

    }

    /**
     * register lbs
     *
     * @param lbSchemal
     */
    public void registerLbServic(LbSchemal lbSchemal) {
        JobEventBusCenter.post(lbSchemal);
        collect.addLbTask(lbSchemal);
    }

    //  public String strategy(String plugin_type, String tid, String st, String... ndes) {

    // TODO 资源分配模块的集成(策略集成） ---？ 主要是拉取任务的判断，
    // TODO api 状态数据的获取（这里指的是时间状态的更新）---
    // TODO 组装成 对client 发送消息的接口；标准；
    public String acquireMession(String plugin_type, String node, String ndes_line) throws Exception {
//assignByStratge(JSONObject jsMession, String plugin_type, String tid_ref, String strategy, String... ndes) {
        if (ndes_line == null) {
            return "node";
        }
        String[] split = ndes_line.split(",");
        if(split.length==0){
            return "node";
        }

        JSONArray client_mession = new JSONArray();
        String strategy = jobEventlbsDefaultListener.strategy(plugin_type);
        if (strategy != null) {
            JSONArray total_update_mession = collect.acquireLbTask(plugin_type);
            String tid_ref = collect.tid_ref(plugin_type);

            if (!total_update_mession.isEmpty()) {
                for (Object o : total_update_mession) {
                    String assignByStratgenode = assign.assignByStratge((JSONObject) o, plugin_type, tid_ref, strategy, split);
                    if (assignByStratgenode == null) {
                        log.error("分配不到节点");
                        continue;
                    }
                    if (node.equalsIgnoreCase(assignByStratgenode)) {
                        client_mession.add(o);
                    }
                }
                return client_mession.toJSONString();
            }
        }
        return "none";
    }

}
