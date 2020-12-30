/*
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：rserver
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
package com.cityos.sncmanager.reserver.cache;

import akka.actor.ActorSelection;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author Andy on 2020/10/10 21:05
 * At Jd
 */
public class CaffeineWorker {
    final static Cache<String, ActorSelection> cache = Caffeine.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS)
            .maximumSize(100)
            .build();

    public static Cache<String, ActorSelection> getCache() {
        return cache;
    }

    /**
     * 获取Producer
     * @param task_id
     * @return
     * @throws IOException
     */

    public static ActorSelection getProducer(String task_id) throws IOException {
        ActorSelection ifPresent = getCache().getIfPresent(task_id);
        if(ifPresent==null){
            //getCache().put(task_id,new KafkaProducerAcquire().buildProducer(task_id,bootstrap));
            return null;
        }else {
            return ifPresent;
        }
    }

    /**
     * remove p
     * @param task_id
     * @return
     * @throws IOException
     */
    public static void remove(String task_id)  {
        cache.invalidate(task_id);
    }
}
