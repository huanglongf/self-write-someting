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
package com.cityos.lb.bus.impl;

import com.cityos.lb.bus.JobEventlbsListener;
import com.cityos.lb.meta.LbSchemal;
import com.google.common.eventbus.Subscribe;

/**
 * Created by @author Andy on 2020/12/9 18:31
 * At Jd
 */
public class JobEventlbsDefaultListener extends JobEventlbsListener {

    public JobEventlbsDefaultListener() throws Exception {
    }


    @Subscribe
    public void askForLoadBalance(LbSchemal lbs) {
        handle(lbs);
    }

    public String strategy(String plugin_type) {
        try {
            return LBSCHEMA.get(plugin_type).getStrategy();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
