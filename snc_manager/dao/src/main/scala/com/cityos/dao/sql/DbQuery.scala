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

import com.alibaba.fastjson.JSONArray
import com.cityos.dao.tools.JdbcTools

object DbQuery extends SqlTrait {
  /**
   * canal 服务插入注册节点ip 信息
   */
  @throws[Exception]
  def cregist(ip: String): Int = {
    JdbcTools.query(canalregist(ip), "update")
  }

  @throws[Exception]
  def canalInstanceInit(content: String, ip: String): Int = {
    JdbcTools.query(canalInstanceProfile(content, ip), "update")
  }

  @throws[Exception]
  def Lb_record_in(ptype: String, strategy: String, period: Int): Int = {
    JdbcTools.query(lb_w(ptype, strategy, period), "update")
  }

  @throws[Exception]
  def Lb_record_read(): JSONArray = {
    JdbcTools.getJsonArray(lb_r)
  }


  /** 获取同步任务信息 -Lb -plugin-api **/
  @throws[Exception]
  def Lb_ApiTaskInfomation(): JSONArray = {
    JdbcTools.getJsonArray(taskUpdate)
  }

  /** 分配任务信息 Assign-api **/
  @throws[Exception]
  def As_record_in(): JSONArray = {
    JdbcTools.getJsonArray(assign_r)
  }

  @throws[Exception]
  def As_flush(tid: String, nodes: String, plugin_type: String): Int = {
    JdbcTools.query(assign_w(tid, nodes,plugin_type), "update")
  }

}
