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
import com.alibaba.fastjson.JSONObject;
import com.cityos.dao.sql.DbQuery;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by @author Andy on 2020/12/9 20:49
 * At Jd
 */

@Data
@Slf4j
public class plugin_api implements PluginsFactory {

    private String ptype;
    private String strategy;
    private Integer period;
    private AtomicInteger readble = new AtomicInteger(0);
    JSONArray tasks = new JSONArray();
    HashMap<String, LocalDateTime> trigger_time = new HashMap<>();


    private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);

    @Override
    public void flush() {
        if (readble.compareAndSet(0, -1)) {
            try {
                tasks.clear();
                tasks.addAll(DbQuery.Lb_ApiTaskInfomation());
                log.info("更新api 拉取信息 " + LocalDateTime.now());
            } catch (Exception e) {
                readble.set(0);
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONArray out() {

        int count = 1;
        while (!readble.compareAndSet(0, 1) && count > 0) {
            try {
                Thread.sleep(period / 2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }
        try {
            JSONArray clone = (JSONArray) tasks.clone();
            readble.set(0);
            return clone;
        } catch (Exception e) {
            readble.set(0);
            e.printStackTrace();
        }
        return null;
    }

    public plugin_api(String ptype, String strategy, Integer period) {
        this.ptype = ptype;
        this.strategy = strategy;
        this.period = period;
    }

    @Override
    public void start() {

        delayExector.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                flush();
            }
        }, period, period, TimeUnit.SECONDS);

    }

    /**
     * client 发送数据；
     * @return
     */
    @Override
    public JSONArray clientMsg() {
        JSONArray out = out();
        if (out.isEmpty()) {
            ListIterator<Object> lt = out.listIterator();
            while (lt.hasNext()) {
                JSONObject objTask = (JSONObject) lt.next();
                String group_id = objTask.getString("group_id");
                LocalDateTime updatetime = LocalDateTime.parse((objTask.getString("update_time").replaceAll(" ", "T")));

                if (trigger_time.get(group_id) == null) {
                    trigger_time.put(group_id, updatetime);
                } else {
                    if (trigger_time.get(group_id).equals(updatetime)) {
                        lt.remove();
                    }
                }
            }
        }
        if (out != null && !out.isEmpty()) {
            return out;
        }
        return null;
    }


    @Override
    public String acquireJk() {return "group_id";}

}
