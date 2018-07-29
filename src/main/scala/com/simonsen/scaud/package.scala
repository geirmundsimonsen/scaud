package com.simonsen

package object scaud {
  def saw(numsamples: Int, freq: Any) : Array[Double] = {
    val samples = new Array[Double](numsamples)
    var phase: Double = 0

    freq match {
      case freq: Double =>
        val phaseInc = Main.invsr * freq * 2
        for (i <- Range(0, numsamples)) {
          samples(i) = phase
          phase += phaseInc
          if (phase > 1)
            phase -= 2
        }
      case freq: Array[Double] =>
        for (i <- Range(0, numsamples)) {
          val phaseInc = Main.invsr * freq(i) * 2
          samples(i) = phase
          phase += phaseInc
          if (phase > 1)
            phase -= 2
        }
    }

    samples
  }

  def line(start: Double, end: Double, time: Double) : Stream[Double] =
    start #:: (if (start > 0) line(start - 1, 0, 0) else Stream.empty)

  def line2(start: Double, end: Double, time: Double) : Unit = 3

  lazy val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map { n => n._1 + n._2 }

  lazy val line: Stream[Double] =

  def fibs2(n: Int) : BigInt = {
    var a: BigInt = 0
    var b: BigInt = 1

    if (n == 0)
      0
    else if (n == 1)
      1
    else {
      for (_ <- Range(2, n + 1)) {
        val res = a + b
        a = b
        b = res
      }
      b
    }
  }
}
