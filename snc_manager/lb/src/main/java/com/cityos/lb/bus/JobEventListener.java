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
package com.cityos.lb.bus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cityos.dao.sql.DbQuery;
import com.cityos.lb.meta.LbSchemal;

import java.util.HashMap;

/**
 * Created by @author Andy on 2020/12/9 13:30
 * At Jd
 */
public interface JobEventListener {
    HashMap<String, LbSchemal> LBSCHEMA = new HashMap<>();

    default void Processjb() throws Exception {
        JSONArray lb_record_read = DbQuery.Lb_record_read();
        for (int i = 0; i < lb_record_read.size(); i++) {
            LbSchemal lbSchemal = JSON.parseObject(JSON.toJSONString(lb_record_read.get(i)), LbSchemal.class);
            LBSCHEMA.put(lbSchemal.getPlugin_type(), lbSchemal);
        }
    }

    void Lbin(String ptype, String strategy, Integer period);

    Boolean eq(LbSchemal lbs,Object b);

    Boolean handle(LbSchemal lbs);


}
