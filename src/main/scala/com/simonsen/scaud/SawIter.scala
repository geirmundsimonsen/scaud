package com.simonsen.scaud

class SawIter(freq: Any) extends Iterator[Double] {
  var phase: Double = 0
  val freqIter = freq match {
    case freq: Iterator[Double] =>
      freq
    case freq: Double =>
      Iterator.continually(freq)
  }

  def hasNext() : Boolean = {
    freqIter.hasNext
  }

  def next() : Double = {
    val phaseInc = Main.invsr * freqIter.next() * 2
    val out = phase
    phase += phaseInc
    if (phase > 1)
      phase -= 2
    out
  }
}
