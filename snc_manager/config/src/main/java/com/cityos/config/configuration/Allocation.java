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
package com.cityos.config.configuration;

import com.cityos.config.configuration.apollo.Apollo;
import com.cityos.config.configuration.env.PlatForm;
import com.cityos.config.configuration.local.Constance;
import com.cityos.config.configuration.utils.AddressUtils;
import com.cityos.config.configuration.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.MapConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by @author Andy on 2020/11/5 16:43
 * At Jd
 */
@Slf4j
public class Allocation extends MapConfiguration implements Apollo, Constance {


    static private Properties p;
    AtomicInteger fist = new AtomicInteger(0);

    static {
        try {
            //System.setProperty("manager_conf_dir", "D:\\workspace2\\snc_manager\\config\\profiles\\dev\\");
            p = PropertiesUtils.getInstance(System.getProperty("manager_conf_dir"), "manager.properties");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static HashMap<String, String> big_cof = new HashMap<>();
    static HashMap<String, String> cannal_cof = new HashMap<>();

    public String LOCALHOST = p.getProperty("system.conf.ip", AddressUtils.getHostIp());
    public String PORT = p.getProperty("system.conf.port", "7087");

    public Allocation() throws Exception {

        super(new HashMap<String, String>());
        System.out.println("=============");
        System.setProperty("app.id", p.getProperty("app.id"));
        System.setProperty("apollo.meta", p.getProperty("apollo.meta"));
        System.setProperty("env", p.getProperty("env"));
        System.setProperty("MYSQLDBURL", p.getProperty("mydql.conf.dburl"));
        System.setProperty("MYSQLUSERNAME", p.getProperty("mydql.conf.username"));
        System.setProperty("MYSQLPWD", p.getProperty("mydql.conf.password"));
        System.setProperty("MYSQLINITIALSIZE", p.getProperty("mysql.conf.initialSize"));
        System.setProperty("MYSQLMAXACTIVE", p.getProperty("mysql.conf.maxActive"));
        try {
            getConfig(big_cof, COMMON_NAMESPACE, null);
            getConfig(cannal_cof, CANAL_NAMESPACE, big_cof);
        } catch (Exception e) {
            e.printStackTrace();
            if (0 == fist.get()) {
                throw new Exception("apollo 初始化，获取大数据集群参数错误");
            }
        }
        fist.set(1);
        showProperties();
    }


    private void showProperties() {
        log.info("=================加载大数据的组件配置文件==========================");
        big_cof.forEach((k, v) -> {
            log.info(String.format("%s\t = %s", k, v));
        });
        log.info("=====================加载cacanl配置文件============================");
        cannal_cof.forEach((k, v) -> {
            log.info(String.format("%s\t = %s", k, v));
        });
        log.info("==========================END=========================");
    }

    public void changeBpAndReturn() {
        getConfig(big_cof, COMMON_NAMESPACE,null);
    }

    public void changeCpAndReturn() {
        changeBpAndReturn();
        getConfig(cannal_cof, CANAL_NAMESPACE,big_cof);
    }


    public String getCanalContent() {

        return cannal_cof.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(System.lineSeparator()));
    }


    public String getBigdataContent() {
        big_cof.put("canal.manager.jdbc.driverName",cannal_cof.getOrDefault("canal.manager.jdbc.driverName","null"));
        big_cof.put("canal.manager.jdbc.password",cannal_cof.getOrDefault("canal.manager.jdbc.password","null"));
        big_cof.put("canal.manager.jdbc.username",cannal_cof.getOrDefault("canal.manager.jdbc.username","null"));
        big_cof.put("canal.manager.jdbc.url",cannal_cof.getOrDefault("canal.manager.jdbc.url","null"));
        return "{" + big_cof.entrySet().stream().map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"").collect(Collectors.joining(",")) + "}";
    }


    public static void main(String[] args) throws Exception {
        new Allocation();
        System.out.println("----------------");
        System.out.println(PlatForm.JEDISSERVER.getValue());
        System.out.println(PlatForm.REDISPWD.getValue());
    }


}
