package com.cityos.sncmanager.rserver.workercache

import akka.actor.Actor
import com.cityos.sncmanager.reserver.cache.CaffeineWorker

/**
 * Created by @author Andy on 2020/12/11 23:33
 * At Jd
 */
object workClientRefer extends Actor {
  override def receive: Receive = ???

  def add(work_actor_system_name: String, ptype: String, ip: String, work_actor_name: String): Unit = {
    val pp = ip.split("-")
    if (pp.length != 2) {
      return
    }
    val workHost: String = pp(0)
    val workPort: String = pp(1)
    CaffeineWorker.getCache().put(ptype+ip, context.actorSelection(s"akka.tcp://${work_actor_system_name}@$workHost:$workPort/user/${work_actor_name}"))
  }

}
