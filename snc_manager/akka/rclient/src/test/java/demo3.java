/*
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：rclient
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
import com.cityos.sncmanager.rclient.Client;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by @author Andy on 2020/11/11 11:55
 * At Jd
 */
public class demo3 {
    @Test
    public static void test01() throws InterruptedException {
/**
 *  workerHost: String, 服务所在ip
 *  workerPort: String, 服务所在 一个端口，不冲突即可
 *  ptype: String,  插件类型，
 *  WORKER_ACTOR_SYSTEM_NAME:String, PRC 客户端的 RPC 系统命名，做个UUID 区分
 *  WORKER_ACTOR_NAME:String PRC 客户端的命名，做个UUID 区分
 * **/
        String uuid = UUID.randomUUID().toString();
        Client.apply("localhost", "7089", "flume");


        /**
         * 获取存活状态 ---json
         */
        try {
            Thread.sleep(5000L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String nodeAliveInfo = Client.getNodeAliveInfo();
        System.out.println(nodeAliveInfo);


        /**
         *  bigdata.configuration 参数 ---json （flat)
         */
        String envParameters = Client.getEnvParameters();
        System.out.println(envParameters);

        while(true){
            Thread.sleep(5000L);
            System.out.println("执行业务");
        }

    }
}
