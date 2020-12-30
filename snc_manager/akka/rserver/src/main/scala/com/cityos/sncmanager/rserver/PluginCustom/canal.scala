package com.cityos.sncmanager.rserver.PluginCustom

import com.cityos.dao.sql.DbQuery

/**
 * Created by @author Andy on 2020/11/6 18:08
 * At Jd
 */
object canal {
  def instanceInit(content: String, ip: String): Unit = {
    DbQuery.canalInstanceInit(content, ip)
  }

}
