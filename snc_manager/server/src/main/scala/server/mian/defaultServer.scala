package server.mian

import com.cityos.config.configuration.Allocation
import com.cityos.sncmanager.rserver.master.Master
import server.default.AbstractInit

/**
 * Created by @author Andy on 2020/11/10 18:48
 * At Jd
 */
class defaultServer extends AbstractInit {
  def main(): Unit = {

    val bannal =
      """
         |___________________________________________
         | ___________________________________________
         |  ___________________/\\\____________________
         |   __/\\\\\\\\\\\__/\\\\\\\\\\\_____/\\\\\\\\_
         |    _\///////\\\/__\////\\\////____/\\\//////__
         |     ______/\\\/_______\/\\\_______/\\\_________
         |      ____/\\\/_________\/\\\_/\\__\//\\\________
         |       __/\\\\\\\\\\\____\//\\\\\____\///\\\\\\\\_
         |        _\///////////______\/////_______\////////__
         |
         |              JD SHUKE SNC Platform V2.4.8
         |""".stripMargin

    println(bannal)
    Master(new Allocation())
    restore()
  }

}

object defaultServer {
  def apply(): defaultServer = new defaultServer()
}
