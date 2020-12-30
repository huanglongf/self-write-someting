package com.cityos.sncmanager.rserver.master

import akka.actor.{Actor, ActorSystem, Props}
import com.cityos.common.akka._
import com.cityos.common.log.Logging
import com.cityos.config.configuration.Allocation
import com.cityos.lb.bus.buscenter.JobEventBusCenter
import com.cityos.lb.meta.LbSchemal
import com.cityos.sncmanager.rserver.PluginCustom.canal
import com.cityos.sncmanager.rserver.meta.Meta
import com.cityos.sncmanager.rserver.workercache.workClientRefer
import com.typesafe.config.ConfigFactory

/**
 * Created by @author Andy on 2020/11/6 16:11
 * At Jd
 */
class Master(val allo: Allocation) extends Actor with Logging {


  override def receive: Receive = {
    case RegisteredWorker_v2(ptype, ip, worker_actor_system_name, worker_actor_name) => {
      try {
        workClientRefer.add(worker_actor_system_name, ptype, ip, worker_actor_name)
        allo.changeCpAndReturn()
        Meta.register(ptype, ip)
        canal.instanceInit(allo.getCanalContent, ip)
      } catch {
        case e: Exception => {
          logger.error(s"cannal server from $ip  register failled details  $e")
          println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
          sender() ! False
        }
      }

      sender() ! platFormConfig(allo.getBigdataContent)
    }

    case Heartbeat(ptype, ip) => {
      Meta.updateHeartBeat(ptype, ip)
      logger.info(s"$ptype 发来心跳信息 FROM $ip")
      sender() ! mession(lbs.acquireMession(ptype,ip,Meta.getAliveNodeIp(ptype, ip)))
    }

    case ServersAlive() => {
      sender() ! Meta.getserversAliverInfo()
    }

    // Lb 策略
    case requestLbService(plugin_type, node_ip, strategy, period) => {
      JobEventBusCenter.post(new LbSchemal(plugin_type, strategy, period))

    }
  }

}

object Master {

  val MASTER_ACTOR_SYSTEM_NAME = "MasterSystem"
  val MASTER_ACTOR_NAME = "Master"

  def apply(allo: Allocation): Unit = {
    println("akka server start")
    val host = allo.LOCALHOST
    val port = allo.PORT

    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin

    val config = ConfigFactory.parseString(configStr)
    //创建ActorSystme(单例)
    val actorSystem = ActorSystem(MASTER_ACTOR_SYSTEM_NAME, config)
    //通过AcotorSystor创建Acotr
    val masterActor = actorSystem.actorOf(Props(new Master(allo)), MASTER_ACTOR_NAME)
  }
}
