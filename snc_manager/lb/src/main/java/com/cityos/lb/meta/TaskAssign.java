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
package com.cityos.lb.meta;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by @author Andy on 2020/12/9 13:10
 * At Jd
 */
@Data
@AllArgsConstructor
public class TaskAssign {
    private String plugin_type;
    private String node_ip;
    private String ID;
}
