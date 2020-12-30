package com.cityos.sncmanager.rserver

import com.cityos.lb.run.LbSystem

/**
 * Created by @author Andy on 2020/11/9 18:53
 * At Jd
 */
package object master {
  val HEARTBEAT_INTERVAL = 5000
   val lbs = new LbSystem()
  lbs.start()
}
