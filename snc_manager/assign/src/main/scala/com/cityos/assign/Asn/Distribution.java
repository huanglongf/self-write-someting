package com.cityos.assign.Asn;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cityos.dao.sql.DbQuery;

import java.util.HashMap;

/**
 * Created by @author Andy on 2020/12/11 15:31
 * At Jd
 */
public interface Distribution {
    HashMap<String, HashMap<String, String>> TASK_DISTRIBUTION = new HashMap<>();
    HashMap<String, Integer> TYPENODESUM = new HashMap<>();

    default void loadMession() throws Exception {
        JSONArray as_record_read = DbQuery.As_record_in();
        for (int i = 0; i < as_record_read.size(); i++) {
            JSONObject o = (JSONObject) as_record_read.get(i);
            String s = o.getString("plugin_type") + o.getString("nodes");
            if (o.getString("plugin_type") != null) {
                TASK_DISTRIBUTION.put(o.getString("plugin_type"), new HashMap<String, String>());
            }
            TASK_DISTRIBUTION.get(o.getString("plugin_type")).put(o.getString("tid"), o.getString("nodes"));
            TYPENODESUM.put(s, TYPENODESUM.get(s) == null ? 1 : TYPENODESUM.get(s) + 1);

        }
    }

    void Asin(String plugin_type, String tid, String ndes);

    String strategy(String plugin_type, String tid,String st,String ... ndes);

    Boolean handle(String plugin_type, String tid, String ndes);

    Integer TypeNodeSuM(String plugin_type,String nodes);

    String finderNode(String plugin_type, String tid);

}
