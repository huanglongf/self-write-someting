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
package com.cityos.lb.assin;

import com.alibaba.fastjson.JSONArray;
import com.cityos.lb.assin.plugins.PluginsFactory;
import com.cityos.lb.assin.plugins.plugin_api;
import com.cityos.lb.meta.LbSchemal;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created by @author Andy on 2020/12/9 20:38
 * At Jd
 */

@Slf4j
public class TaskCollect {

    private HashMap<String, LbSchemal> lbss = new HashMap<>();
    private HashMap<String, PluginsFactory> runingTask = new HashMap<>();

    public void addLbTask(LbSchemal lbs) {
        String plugin_type = lbs.getPlugin_type();
        if (lbss.keySet().contains(plugin_type)) {
            log.error("Double commit task");
            return;
        }
        PluginsFactory collect = null;

        switch (plugin_type) {
            case "api":
                collect = new plugin_api(plugin_type, lbs.getStrategy(), lbs.getPeriod());
                break;
            default:
                log.error("Unsupported types");
                return;
        }

        if (collect != null) {
            lbss.put(plugin_type, lbs);
            runingTask.put(plugin_type, collect);
            collect.start();
        }

    }


    public JSONArray acquireLbTask(String plugin_type) {
        return runingTask.get(plugin_type).clientMsg();
    }

    public String tid_ref(String plugin_type) {
        return runingTask.get(plugin_type).acquireJk();
    }

}
