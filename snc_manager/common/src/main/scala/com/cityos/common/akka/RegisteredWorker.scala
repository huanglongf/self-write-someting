package com.cityos.common.akka

/**
 * Created by @author Andy on 2020/11/6 16:14
 * At Jd
 */

/**
 * 注册
 *
 * @param plugin_type
 * @param node_ip
 */
case class RegisteredWorker(plugin_type: String, node_ip: String)

case class RegisteredWorker_v2(plugin_type: String, node_ip: String, worker_actor_system_name: String, worker_actor_name: String)

case class Heartbeat(ptype: String, ip: String)
case class mession(mession: String)

case object SendHeartbeat


case object False

case object True

/**
 * message
 */

case class ServersAlive()


case class ServersAliveInfo(info: String)