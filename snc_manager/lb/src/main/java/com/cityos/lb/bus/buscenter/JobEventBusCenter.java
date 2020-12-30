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
package com.cityos.lb.bus.buscenter;

import com.cityos.lb.bus.JobEventListener;
import com.cityos.lb.meta.LbSchemal;
import com.google.common.eventbus.EventBus;

/**
 * Created by @author Andy on 2020/12/9 11:41
 * At Jd
 */
public class JobEventBusCenter {
    private static class JobEventBus {
        private static final EventBus EVENT_BUS = new EventBus();
    }

    public static void register(JobEventListener listener) {
        JobEventBus.EVENT_BUS.register(listener);
    }

    public static void post(LbSchemal event) {
        JobEventBus.EVENT_BUS.post(event);

    }
}
