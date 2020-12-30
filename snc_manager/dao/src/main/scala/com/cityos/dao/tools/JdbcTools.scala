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
package com.cityos.dao.tools

import java.sql.ResultSet

import com.alibaba.fastjson.{JSONArray, JSONObject}
import com.cityos.dao.datapool.MysqlPoolUtils
import org.slf4j.LoggerFactory


object JdbcTools {

  private val log = LoggerFactory.getLogger(MysqlPoolUtils.getClass)

  /**
    * 查询JsonArray 的结果
    */
  @throws[Exception]
  def getJsonArray(sql: String): JSONArray = {
    // log.info("sql====" + sql)
    val conn = MysqlPoolUtils.getConnection.getOrElse(null)
    if (conn == null) {
      return null
    }
    val arr = new JSONArray()
    val stat = conn.createStatement()
    var rs: ResultSet = null
    try {
      rs = stat.executeQuery(sql)
      val meta = rs.getMetaData
      while (rs.next()) {
        val row = new JSONObject(true)
        for (i <- 1 to meta.getColumnCount) {
          row.put(meta.getColumnLabel(i).toLowerCase, rs.getString(i))
        }
        arr.add(row)
      }
    } finally {
      MysqlPoolUtils.close(rs, stat, conn)
    }
    // 关闭
    arr
  }

  /**
    * 查询JsonObject 的结果
    */
  @throws[Exception]
  def getJsonObject(sql: String): JSONObject = {
    // log.info("sql====" + sql)
    val conn = MysqlPoolUtils.getConnection.getOrElse(null)
    if (conn == null) {
      return null
    }
    val row = new JSONObject(true)
    val stat = conn.createStatement()
    var rs: ResultSet = null
    try {
      rs = stat.executeQuery(sql)
      val meta = rs.getMetaData
      while (rs.next()) {
        for (i <- 1 to meta.getColumnCount) {
          row.put(meta.getColumnLabel(i).toLowerCase, rs.getString(i))
        }
      }
    } finally {
      MysqlPoolUtils.close(rs, stat, conn)
    }
    // 关闭
    row
  }

  /**
    * 数据的跟新和单个数据的查询
    *
    * @param sql
    * @param stype
    */
  @throws[Exception]
  def query(sql: String, stype: String): Int = {
    //log.info("sql====" + sql)
    val conn = MysqlPoolUtils.getConnection.getOrElse(null)
    if (conn == null) {
      return -1
    }

    val stat = conn.createStatement()
    var rs: ResultSet = null
    try {
      if ("update".equalsIgnoreCase(stype)) {
        val i = stat.executeUpdate(sql)
        return i
      }

      if ("ddl".equalsIgnoreCase(stype)) {
        val bool = stat.execute(sql)
        return 1
      }

      if ("queryone".equalsIgnoreCase(stype)) {
        val rs = stat.executeQuery(sql)
        rs.next()
        val result = rs.getInt(1)
        return result
      }
    } finally {
      MysqlPoolUtils.close(rs, stat, conn)
    }
    return -1
  }

}
