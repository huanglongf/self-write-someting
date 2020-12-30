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
package com.cityos.config.configuration.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by @author Andy on 2020/11/5 17:08
 * At Jd
 */
public class PropertiesUtils {
    public static Properties getInstance(String path, String fileName) throws IOException {
        if (path == null) {
            path = Thread.currentThread().getClass().getResource("/").getPath();
        }
        FileInputStream in = new FileInputStream(path + fileName);
        Properties p = new Properties();
        p.load(in);
        return p;
    }
}
