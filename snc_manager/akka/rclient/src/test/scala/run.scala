/**
 * Created by @author Andy on 2020/11/6 20:52
 * At Jd
 */

import com.cityos.sncmanager.rclient.Client

object run extends App {
  Client("localhost", "7088", "canal")
  while (true) {
    Thread.sleep(5000)
    Client.getNodeAliveInfo()
  }

}
