package com.cityos.dao.sql

/**
 * Created by @author Andy on 2020/12/8 20:43
 * At Jd
 */
trait Assign {
  val assign_r =
    s"""
       |SELECT
       |	tid,
       |	nodes,
       | plugin_type
       |FROM
       |	assign
       |""".stripMargin

  def assign_w(tid: String, nodes: String, plugin_type: String): String =
    s"""
       |REPLACE INTO `shishi`.`assign` ( `tid`, `nodes`,`plugin_type` )
       |VALUES
       |	( '$tid', '$nodes' ,'$plugin_type')
       |""".stripMargin

}
