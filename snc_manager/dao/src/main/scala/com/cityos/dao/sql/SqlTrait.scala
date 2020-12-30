/**
 * **********************************************************************
 * Copyright (c) ${project.inceptionYear} .
 * All rights reserved.
 * project name：sncdata
 * project description：${project.description}
 *
 * The software is owned by JD Co., LTD. Without formal authorization from JD Co., LTD.,
 * no enterprise or individual can acquire, read, install or disseminate any content
 * related to the software that is protected by intellectual property rights.
 * ***********************************************************************
 */
package com.cityos.dao.sql

import java.time.LocalDateTime

trait SqlTrait extends Lb{

  /**
   * 注册cannal node 节点
   *
   * @param ip
   * @return
   */
  def canalregist(ip: String): String = {
    val now = LocalDateTime.now().toString().replaceAll("T", " ")
    s"""
       |REPLACE INTO canal_ip_info ( `canal_ip`, `created_time` )
       |VALUES
       |	( '$ip', '$now' )
       |""".stripMargin
  }


  /**
   * cannal 初始化配置文件
   * @param content
   * @param ip
   * @return
   */
  def canalInstanceProfile(content: String, ip: String): String = {
    val now = LocalDateTime.now().toString().replaceAll("T", " ")
    s"""
       |REPLACE INTO canal_config ( `name`, `canal_ip`,`created_time`,`content` )
       |VALUES
       |	( 'canal.properties','$ip','$now' ,'$content')
       |""".stripMargin
  }



}


