/*
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：config
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
package com.cityos.config.configuration.local;



/**
 * Created by @author Andy on 2020/11/6 12:49
 * At Jd
 */
public interface Constance {

    /**
     * redis 配置
     */
    Integer MAXTOTAL = 100;
    Integer MAXIDEL = 50;
    Integer MINIDEL = 30;
    Long MAXWAITMILL = 6000L;
    Boolean TESTONBORROW = true;
    Boolean TESTWHILEIDLE = true;
    Integer REDISTIMEOUT = 16000;
    Integer REDISATTEMPTS = 6;
    Integer SOTIMEOUT = 10000;


    /**
     * redis meta back_keys_prefix
     */

    String PLUGININFO ="snc:manager:register:info";



}
