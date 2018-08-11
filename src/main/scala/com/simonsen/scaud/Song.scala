package com.simonsen.scaud

object Song {
  def main(args: Array[String]): Unit = {
    startSoundSystem()
    play(saw(120.0) * new Exp(1, 0.00000000000000000000000000000000000000001, 0.001))
    //new Exp(1, 0.000001, limit=0.5).foreach(println)
  }
}
