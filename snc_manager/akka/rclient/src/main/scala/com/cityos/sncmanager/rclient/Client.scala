package com.cityos.sncmanager.rclient

import java.util.UUID

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import com.cityos.common.akka._
import com.cityos.common.log.Logging
import com.cityos.sncmanager.meta.bigDataParameters
import com.google.common.eventbus.EventBus
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by @author Andy on 2020/11/6 19:07
 * At Jd
 */
class Client(val host: String, val ptype: String) extends Actor with Logging {

  val bus: EventBus = new EventBus()

  override def preStart(): Unit = {
    super.preStart()
    masterRef = context.actorSelection(s"akka.tcp://${MASTER_ACTOR_SYSTEM_NAME}@$masterHost:$masterPort/user/${MASTER_ACTOR_NAME}")
    //worker向Master发送注册消息（Worker的id，worker的内存，cpu总核数）
    masterRef ! RegisteredWorker_v2(ptype, host, WORKER_ACTOR_SYSTEM_NAME, WORKER_ACTOR_NAME)
    bus.register(bigDataParameters())
  }

  override def receive: Receive = {
    case True => {

    }
    case SendHeartbeat => {
      //在该case中可以进行一些逻辑判断
      println("发送心跳")
      masterRef ! Heartbeat(ptype, host)
    }
    case False => {
      //在该case中可以进行一些逻辑判断
      logger.error("XXXXXXXXXXXXXXXXXXXX 注册失败 XXXXXXXXXXXXXXXXXXX")
      System.exit(1)

    }

    case platFormConfig(bigDataInfo) => {
      //在该case中可以进行一些逻辑判断
      import context.dispatcher
      logger.info("XXXXXXXXXXXXXXXXXXXX 注册成功 XXXXXXXXXXXXXXXXXXX")
      bus.post(bigDataInfo)
      context.system.scheduler.schedule(0 millis, HEARTBEAT_INTERVAL millis, self, SendHeartbeat)
    }

    case mession(mess) => {
      println("接收新的任务")
    }

  }
}


object Client {

  /**
   * 同一节点起多个服务
   *
   * @param workerHost
   * @param workerPort
   * @param ptype
   * @return
   */
  def apply(workerHost: String, workerPort: String, ptype: String): Unit = {
    println("akka client start")
    val uuid = UUID.randomUUID.toString
    WORKER_ACTOR_SYSTEM_NAME = ptype + uuid
    WORKER_ACTOR_NAME = ptype + "_" + uuid
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$workerHost"
         |akka.remote.netty.tcp.port = "$workerPort"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem(WORKER_ACTOR_SYSTEM_NAME, config)
    val workeractorname: ActorRef = actorSystem.actorOf(Props(new Client(workerHost + "_" + workerPort, ptype)), WORKER_ACTOR_NAME)
  }

  /**
   * 获取节点 存活
   *
   */
  def getNodeAliveInfo(): String = {
    import akka.pattern.ask
    implicit val timeout = Timeout(15 seconds)
    //var count = 3
    val future = masterRef ? ServersAlive()
    val info: ServersAliveInfo = Await.result(future.mapTo[ServersAliveInfo], 15 seconds)
    //    println(LocalDateTime.now().toString)
    //    println("所有node存活情况", info.info)
    //    println(bigDataParameters.getEv())
    info.info
  }

  def getEnvParameters = bigDataParameters.getEv()

}