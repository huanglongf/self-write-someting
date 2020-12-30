import scala.collection.mutable

/**
 * Created by @author Andy on 2020/11/10 15:07
 * At Jd
 */

object demo {
  def main(args: Array[String]): Unit = {
    val map = new mutable.HashMap[String, mutable.HashMap[String, String]]()
    map.getOrElseUpdate("hh", new mutable.HashMap[String, String]())
    map("hh")+=("ip0"->"alive")
    map.getOrElseUpdate("gg", new mutable.HashMap[String, String]())
    map("gg")+=("ip2"->"dead")
    map("gg")+=("ip3"->"alive")
    println(map.toBuffer)

    val str = "{" + map.keySet.map(k => "\"" + k + "\":" + "{" + getjSONStr(map(k))).mkString(",") + "}"
    println(str)
  }

  def getjSONStr(hmap: mutable.HashMap[String, String]): String = {
    hmap.keySet.map(k => "\"" + k + "\":" + "\"" + hmap(k) + "\"").mkString(",") + "}"
  }


}
