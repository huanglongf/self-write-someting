package com.cityos.sncmanager.rserver.meta

import java.util

import com.cityos.common.akka.ServersAliveInfo
import com.cityos.common.utils.JedisUtil
import com.cityos.dao.sql.DbQuery
import com.cityos.sncmanager.rserver.pojo.PluginInfo

import scala.collection.mutable

/**
 * Created by @author Andy on 2020/11/6 16:34
 * At Jd
 */
object Meta {
  val id2Worker = new mutable.HashMap[String, PluginInfo]()
  val workers = new mutable.HashSet[PluginInfo]()

  @throws[Exception]
  def register(ptype: String, ip: String): Unit = {
    val plugin = new PluginInfo(ip, ptype)
    workers += plugin
    id2Worker(ptype + ip) = plugin
    DbQuery.cregist(ip)
    JedisUtil.registerInfo(ptype, ip)
    //  DbQuery.Lb_record_in(ptype,ip)
  }

  def updateHeartBeat(ptype: String, ip: String): Unit = {
    var plugininfo: PluginInfo = null
    try {
      plugininfo = id2Worker(ptype + ip)
    } catch {
      case e: Exception => register(ptype, ip); plugininfo = id2Worker(ptype + ip)
    }
    val current = System.currentTimeMillis()
    plugininfo.lastHearbeatTime = current
    JedisUtil.registerInfo(ptype, ip)
  }

  def getserversAliverInfo(): ServersAliveInfo = {
    acquireNodeAlive()
  }

  /**
   * 提供活图信息监控;
   *
   * @return
   */
  def acquireNodeAlive(): ServersAliveInfo = {
    val now = System.currentTimeMillis()
    val map = new mutable.HashMap[String, mutable.HashMap[String, String]]()
    for ((key, value) <- id2Worker) {
      map.getOrElseUpdate(value.ptype, new mutable.HashMap[String, String]())
      if ((now - value.lastHearbeatTime) <= (HEARTBEAT_INTERVAL * 3)) {
        map(value.ptype) += (value.ip -> "alive")
      } else {
        map(value.ptype) += (value.ip -> "dead")
        JedisUtil.unRegisterInfo(value.ptype, value.ip)
      }
    }
    val str = "{" + map.keySet.map(k => "\"" + k + "\":" + "{" + getjSONStr(map(k))).mkString(",") + "}"
    ServersAliveInfo(str)
  }

  /**
   * 获取活着的ip
   *
   * @param ptype
   */
  def getAliveNodeIp(ptype: String, ip: String): String = {
    val now = System.currentTimeMillis()
    id2Worker.filter(k => k._1.equalsIgnoreCase(ptype + ip)).filter(k => (k._2.lastHearbeatTime) > (now - HEARTBEAT_INTERVAL * 3)).map(_._2.ptype).mkString(",")
  }

  def getjSONStr(hmap: mutable.HashMap[String, String]): String = {
    hmap.keySet.map(k => "\"" + k + "\":" + "\"" + hmap(k) + "\"").mkString(",") + "}"
  }


  def restoreFromRedis(): Unit = {
    val map: util.Map[String, String] = JedisUtil.remoteRegisterInfo()

    if (map == null || map.isEmpty) {
      return
    }
    import scala.collection.JavaConversions._
    println("restore from meta backup")
    for (entry <- map.entrySet) {
      val strings = entry.getKey.split("_")
      println(strings(0) + "\t" + strings(1))
      val plugin = new PluginInfo(strings(1), strings(0))
      workers += plugin
      id2Worker(strings(0) + strings(1)) = plugin
    }
    println("================================")
  }

}
