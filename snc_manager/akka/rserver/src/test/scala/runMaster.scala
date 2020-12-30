import com.cityos.config.configuration.Allocation
import com.cityos.sncmanager.rserver.master.Master

/**
 * Created by @author Andy on 2020/11/6 20:55
 * At Jd
 */
object runMaster extends App {
  Master(new Allocation())
}
