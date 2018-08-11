package com.simonsen.scaud

object StartRepl {
  def main(args: Array[String]): Unit = {
    Runtime.getRuntime().exec("cmd /c start \"\" c:/code/scala/scaud/src/resources/startscala.bat")
  }
}
