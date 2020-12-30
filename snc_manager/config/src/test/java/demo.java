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
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by @author Andy on 2020/11/6 18:20
 * At Jd
 */
public class demo {
    static Pattern pa = Pattern.compile("\\$\\{[^}]*\\}");

    @Test
    public  void test01() {

        HashMap<String, String> big_cof = new HashMap<>();
        big_cof.put("chengxu_mysql_address", "1.2.1.1");
        big_cof.put("kafkaNode", "12,13.14");
        Set<String> keys = big_cof.keySet();

        System.out.println("=========================");
        System.out.println(replace(keys, "jdbc:mysql://${chengxu_mysql_address}/shishi?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8&autoReconnect=true&allowM", big_cof));
        System.out.println("=========================");
        System.out.println(replace(keys, "${kafkaNode}", big_cof));
        System.out.println("=========================");
        System.out.println(replace(keys,  "${canal.file.data.dir:../conf}/${canal.instance.destination:}", big_cof));
        System.out.println("=========================");

    }

    static String replace(Set<String> keys, String property, HashMap<String, String> big_cof) {
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
