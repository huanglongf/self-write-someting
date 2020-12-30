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
package com.cityos.config.configuration.apollo;

import com.cityos.config.configuration.env.PlatForm;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by @author Andy on 2020/8/15 10:23
 * At Jd
 */
public interface Apollo {


    String COMMON_NAMESPACE = "bigdata.configuration";
    String CANAL_NAMESPACE = "canal_instance";
    Pattern pa = Pattern.compile("\\$\\{[^}]*\\}");

    /**
     * @return
     */
    default void getConfig(HashMap<String, String> allo, String namespace, HashMap<String, String> big_cof) {

        Config source = ConfigService.getConfig(namespace);
        if (COMMON_NAMESPACE.equals(namespace)) {
            placeholderConfigBigDate(source, allo);
        } else {
            placeholderConfig(source, allo, big_cof);
        }
    }

    default void placeholderConfig(Config source, HashMap<String, String> allo, HashMap<String, String> big_cof) {
        Set<String> keys = big_cof.keySet();
        source.getPropertyNames().stream().forEach(p -> allo.put(p, replace(keys, source.getProperty(p, null), big_cof)));
    }

    default void placeholderConfigBigDate(Config source, HashMap<String, String> allo) {
        source.getPropertyNames().stream().forEach(p -> allo.put(p, source.getProperty(p, null)));
        PlatForm.JEDISSERVER.setValue(allo.get("redis.cluster.nodes"));
        PlatForm.REDISPWD.setValue(allo.get("redis.password"));

    }


    default String replace(Set<String> keys, String property, HashMap<String, String> big_cof) {
        if (property == null) {
            return "";
        }

        if (property.contains("${")) {
            try {
                Matcher m = pa.matcher(property);
                while (m.find()) {
                    final String group = m.group();
                    final String mrs = group.replaceAll("\\$\\{", "").replaceAll("\\}", "");
                    if (keys.contains(mrs)) {
                        property = property.replaceAll("\\$\\{" + mrs + "\\}", big_cof.get(mrs));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return property;
    }


}