package com.cityos.sncmanager

import akka.actor.ActorSelection

/**
 * Created by @author Andy on 2020/11/9 15:09
 * At Jd
 */
package object rclient {
  val HEARTBEAT_INTERVAL = 5000
  val MASTER_ACTOR_SYSTEM_NAME = "MasterSystem"
  val MASTER_ACTOR_NAME = "Master"


  import java.net.InetAddress

  var masterHost: String = InetAddress.getByName("snc_manager").getHostAddress
  //  var masterHost: String = "10.241.242.58"
  var masterPort: String = "7087"

  var masterRef: ActorSelection = _


  var WORKER_ACTOR_SYSTEM_NAME:String  = _
  var WORKER_ACTOR_NAME:String = _

}
