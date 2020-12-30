package com.cityos.common.akka

/**
 * Created by @author Andy on 2020/12/8 22:12
 * At Jd
 */
/**
 * guan yu Lb 的负载
 */
case class Lb()
case class requestLbService(plugin_type: String, node_ip: String,strategy:String,period:Int)

