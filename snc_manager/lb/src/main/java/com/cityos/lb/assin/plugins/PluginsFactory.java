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
package com.cityos.lb.assin.plugins;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by @author Andy on 2020/12/9 20:51
 * At Jd
 */
public interface PluginsFactory extends LifeCycle{
    void flush();

    JSONArray out();

    JSONArray clientMsg();


    String acquireJk();
}
