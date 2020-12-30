/*
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：assign
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by @author Andy on 2020/12/11 16:46
 * At Jd
 */
public class demo extends AbstractZkTest{
    @Test
    public  void test01() {

        HashMap<String, Integer> s = new HashMap<>();
        s.put("a", s.get("a") == null ? 1 :  s.get("a") + 1);
        s.put("a", s.get("a") == null ? 1 :  s.get("a") + 1);
        s.put("a", s.get("a") == null ? 1 :  s.get("a") + 1);

        s.put("b", s.get("b") == null ?  1  :  s.get("b") + 1);

        s.forEach((k, v) -> {
            System.out.println(k + "\t" + v);
        });

    }
}
