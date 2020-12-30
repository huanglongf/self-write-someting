package com.cityos.assign.Asn;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author Andy on 2020/12/11 17:04
 * At Jd
 */

@Slf4j
public class Assign extends MessionRecode implements Strategy {
    public Assign() throws Exception {
    }


    @Override
    public String strategy(String plugin_type, String tid, String st, String... ndes) {
        if (STRATEGY_DEFAULT.equalsIgnoreCase(st)) {
            if (ndes.length != 0 && ndes.length == 1) {
                handle(plugin_type, tid, ndes[0]);
                return ndes[0];
            } else {
                int min = Integer.MAX_VALUE;
                String node = "";
                for (String nde : ndes) {
                    Integer value = TypeNodeSuM(plugin_type, nde);
                    if (min > value) {
                        min = value;
                        node = nde;
                    }
                }
                handle(plugin_type, tid, node);
                return node;
            }
        } else {
            log.error("不支持的策略");
        }
        return null;
    }

    public String assignByStratge(JSONObject jsMession, String plugin_type, String tid_ref, String strategy, String... ndes) {
        String tid = jsMession.getString(tid_ref);
        if (finderNode(plugin_type, tid) == null) {
            strategy(plugin_type, tid, strategy, ndes);
        }
        return TASK_DISTRIBUTION.get(plugin_type).get(tid);
    }
}
