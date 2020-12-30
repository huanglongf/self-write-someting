package com.cityos.sncmanager.meta

import com.google.common.eventbus.Subscribe

/**
 * Created by @author Andy on 2020/11/10 11:46
 * At Jd
 */
class bigDataParameters {


  @Subscribe
  def initEnv(env: String) {
    bigDataParameters.env = env
  }

}

object bigDataParameters {
  var env: String = _

  def apply(): bigDataParameters = new bigDataParameters()

  def getEv() = env
}


