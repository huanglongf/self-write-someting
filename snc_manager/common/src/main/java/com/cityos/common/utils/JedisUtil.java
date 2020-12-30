/*
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：common
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
package com.cityos.common.utils;

import com.cityos.config.configuration.Allocation;
import com.cityos.config.configuration.env.PlatForm;
import com.cityos.config.configuration.local.Constance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JedisUtil {
    private static JedisCluster jedisCluster = null;

    private static GenericObjectPoolConfig getConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setBlockWhenExhausted(true);
        config.setMaxTotal(Allocation.MAXTOTAL);
        config.setMaxIdle(Allocation.MAXIDEL);
        config.setMinIdle(Allocation.MINIDEL);
        config.setMaxWaitMillis(Allocation.MAXWAITMILL);
        config.setTestOnBorrow(Allocation.TESTONBORROW);
        config.setTestWhileIdle(Allocation.TESTWHILEIDLE);


        return config;
    }


    /***
     * getredisCluster
     * @return
     */
    public static JedisCluster getCluster() {

        if (jedisCluster == null) {
            Set<HostAndPort> nodeSet = new HashSet();
            String[] jedisserver = PlatForm.JEDISSERVER.getValue().split(",");
            for (String str : jedisserver) {
                String[] s = str.split(":");
                nodeSet.add(new HostAndPort(s[0], Integer.valueOf(s[1])));
            }
            jedisCluster = new JedisCluster(nodeSet, Allocation.REDISTIMEOUT, Allocation.SOTIMEOUT, Allocation.REDISATTEMPTS, PlatForm.REDISPWD.getValue(), getConfig());
        }
        return jedisCluster;
    }

    public static void refreshRedisCluster() {
        try {
            jedisCluster.close();
        } catch (Exception e) {
            e.printStackTrace();
            jedisCluster = null;
        }
    }

    public static void close(JedisCluster jds) {
        if (jds != null) {
            try {
                jds.close();
            } catch (Exception e) {
                e.printStackTrace();
                jds = null;
            }
        }
    }


    /**
     * 同步注册信息
     */
    public static void registerInfo(String ptype, String ip) {
        try {
            getCluster().hset(Constance.PLUGININFO, ptype + "_" + ip, "alive");
        } catch (Exception e) {
            log.error("同步注册信息到redis 出错" + e);
        }
    }

    /**
     * 同步注册信息
     */
    public static void unRegisterInfo(String ptype, String ip) {
        try {
            getCluster().hset(Constance.PLUGININFO, ptype + "_" + ip, "dead");
        } catch (Exception e) {
            log.error("同步注册信息到redis 出错" + e);
        }
    }

    /**
     * 同步注册信息
     */
    public static Map<String, String> remoteRegisterInfo() {
        Map<String, String> stringStringMap = null;
        try {
            stringStringMap = getCluster().hgetAll(Constance.PLUGININFO);

        } catch (Exception e) {
            log.error("同步信息 with redis 出错" + e);
            return null;
        }
        return stringStringMap;
    }


}
