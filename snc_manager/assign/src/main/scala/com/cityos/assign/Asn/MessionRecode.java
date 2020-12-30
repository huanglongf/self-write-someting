package com.cityos.assign.Asn;

import com.cityos.dao.sql.DbQuery;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created by @author Andy on 2020/12/11 15:58
 * At Jd
 */

@Slf4j
public abstract class MessionRecode implements Distribution {
    public MessionRecode() throws Exception {
        loadMession();
    }


    @Override
    public void Asin(String plugin_type, String tid, String nodes) {
        try {
            DbQuery.As_flush(tid, nodes, plugin_type);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Assin 入库错误" + e);
        }
    }



    @Override
    public Boolean handle(String plugin_type, String tid, String nodes) {
        try {
            if (!TASK_DISTRIBUTION.containsKey(plugin_type) || !TASK_DISTRIBUTION.get(plugin_type).containsKey(tid)) {
                if (!TASK_DISTRIBUTION.containsKey(plugin_type)) {
                    TASK_DISTRIBUTION.put(plugin_type, new HashMap<String, String>());
                }
                TASK_DISTRIBUTION.get(plugin_type).put(tid, nodes);
                String s = plugin_type + nodes;
                Asin(tid, nodes, plugin_type);
                TYPENODESUM.put(s, TYPENODESUM.get(s) == null ? 1 : TYPENODESUM.get(s) + 1);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TASK_DISTRIBUTION.remove(tid);
        }
        return false;
    }

    @Override
    public Integer TypeNodeSuM(String plugin_type, String nodes) {
        return TYPENODESUM.get(plugin_type + nodes);
    }

    @Override
    public String finderNode(String plugin_type, String tid) {
        if (TASK_DISTRIBUTION.get(plugin_type) != null) {
            return TASK_DISTRIBUTION.get(plugin_type).get(tid);
        }
        return null;
    }
}
