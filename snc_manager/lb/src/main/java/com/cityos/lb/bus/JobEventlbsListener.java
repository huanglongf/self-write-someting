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


import com.cityos.dao.sql.DbQuery;
import com.cityos.lb.meta.LbSchemal;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author Andy on 2020/12/9 13:34
 * At Jd
 */
@Slf4j
public abstract class JobEventlbsListener implements JobEventListener {
    public JobEventlbsListener() throws Exception {
        Processjb();
    }


    @Override
    public void Lbin(String ptype, String strategy, Integer period) {
        try {
            DbQuery.Lb_record_in(ptype, strategy, period);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("lb 入库错误" + e);
        }
    }

    @Override
    public Boolean eq(LbSchemal lbs, Object b) {
        return lbs.equals(b);
    }

    @Override
    public Boolean handle(LbSchemal lbs) {
       // String plugin_type = lbs.getPlugin_type();
        LbSchemal lbSchemal = LBSCHEMA.get(lbs.getPlugin_type());
        try {
            if (!eq(lbs, lbSchemal)) {
                LBSCHEMA.put(lbs.getPlugin_type(),lbs);
                Lbin(lbs.getPlugin_type(), lbs.getStrategy(), lbs.getPeriod());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LBSCHEMA.remove(lbs.getPlugin_type());
        }
        return false;
    }
}
