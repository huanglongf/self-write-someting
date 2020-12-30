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
package com.cityos.dao.datapool

import java.sql.{Connection, ResultSet, Statement}
import java.util.Properties

import com.alibaba.druid.pool.DruidDataSourceFactory
import javax.sql.DataSource
import org.slf4j.LoggerFactory


/**
  * Created by Andy on 2019/4/12
  */
object MysqlPoolUtils {


  private val log = LoggerFactory.getLogger(MysqlPoolUtils.getClass)
  val dataSource: Option[DataSource] = {
    try {
      val druidProps = new Properties()
      // 从配置信息获取 mysql信息&rewriteBatchedStatements=true

      druidProps.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver")
      druidProps.setProperty("url", System.getProperty("MYSQLDBURL"))
      druidProps.setProperty("username", System.getProperty("MYSQLUSERNAME"))
      druidProps.setProperty("password", System.getProperty("MYSQLPWD"))
      druidProps.setProperty("filters", "stat")
      druidProps.setProperty("initialSize", System.getProperty("MYSQLINITIALSIZE"))
      druidProps.setProperty("maxActive", System.getProperty("MYSQLMAXACTIVE"))
      //#获取连接时最大等待时间，单位毫秒，超过连接就会失效。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
      druidProps.setProperty("maxWait", "60000")
      //# 连接回收器的运行周期时间，时间到了清理池中空闲的连接，testWhileIdle根据这个判断
      druidProps.setProperty("timeBetweenEvictionRunsMillis", "60000")
      druidProps.setProperty("minEvictableIdleTimeMillis", "30000")
      //#用来检测连接是否有效的sql，要求是一个查询语句。
      druidProps.setProperty("validationQuery", "SELECT 1")
      //#建议配置为true，不影响性能，并且保证安全性。 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis， 执行validationQuery检测连接是否有效。
      druidProps.setProperty("testWhileIdle", "true")
      //#申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。设置为false
      druidProps.setProperty("testOnBorrow", "true")
      //#归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能,设置为flase
      druidProps.setProperty("testOnReturn", "true")
      //#是否缓存preparedStatement，也就是PSCache。
      druidProps.setProperty("poolPreparedStatements", "false")
      // 批量插入开启
      druidProps.setProperty("rewriteBatchedStatements", "true")
      //#池中能够缓冲的preparedStatements语句数量
      druidProps.setProperty("maxPoolPreparedStatementPerConnectionSize", "200")
      Some(DruidDataSourceFactory.createDataSource(druidProps))
    } catch {
      case error: Exception =>
        log.error("Error Create Mysql Connection", error)
        None
    }
  }

  // 连接方式
  @throws[Exception]
  def getConnection: Option[Connection] = {
    dataSource match {
      case Some(ds) => Some(ds.getConnection())
      case None => throw new Exception("Error get Mysql Connection")
    }
  }

  //javaConnect
  def javaConn(): Connection = {
    val connection = getConnection.getOrElse(null)
    connection
  }


  /**
    * close
    *
    * @param stat
    * @param conn
    */
  def close(rs:ResultSet,stat: Statement, conn: Connection): Unit = {
    closeRs(rs)
    closeStat(stat)
    closeConn(conn)

  }

  def closeRs(rs: ResultSet): Unit = {

    if(rs !=null){
      try {
        rs.close()
      } catch {
        case e:Exception =>log.info("rs 关闭失败")
      }
    }
  }


  def closeStat(stat: Statement): Unit = {
    if (stat != null)
      try {
        stat.close()
      } catch {
        case e: Exception => log.info("stat 关闭实效")
      }
  }

  def closeConn(conn: Connection): Unit = {
    if (conn != null)
      try {
        conn.close()
      } catch {
        case e: Exception => log.info("conn 关闭实效")
      }
  }

}

