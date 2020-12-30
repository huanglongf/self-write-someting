package server.default

import com.cityos.sncmanager.rserver.meta.Meta

/**
 * Created by @author Andy on 2020/11/10 18:50
 * At Jd
 */
class AbstractInit extends Manager {
  override def restore(): Unit = {
    Meta.restoreFromRedis()
  }
}
