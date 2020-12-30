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
package com.cityos.lb.meta;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by @author Andy on 2020/12/9 12:01
 * At Jd
 */
@Data
@AllArgsConstructor
public class LbSchemal {
    private String plugin_type;
    private String strategy;
    private Integer period;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return true;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof LbSchemal)) {
            return false;
        }
        LbSchemal lbSchemal = (LbSchemal) o;
        return sequal(getPlugin_type(), lbSchemal.getPlugin_type()) &&
                sequal(getStrategy(), lbSchemal.getStrategy()) &&
                sequal(getPeriod() + "", lbSchemal.getPeriod() + "");
    }

    private boolean sequal(String plugin_type, String plugin_type1) {
        return plugin_type.equalsIgnoreCase(plugin_type1);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPlugin_type(), getStrategy(), getPeriod());
    }
}
