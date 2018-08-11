package com.simonsen.scaud

class Saw {
  var phase: Double = 0

  def process(numsamples: Int, freq: Any): Array[Double] = {
    val samples = new Array[Double](numsamples)

    freq match {
      case freq: Double =>
        val phaseInc = invsr * freq * 2
        for (i <- Range(0, numsamples)) {
          samples(i) = phase
          phase += phaseInc
          if (phase > 1)
            phase -= 2
        }
      case freq: Array[Double] =>
        for (i <- Range(0, numsamples)) {
          val phaseInc = invsr * freq(i) * 2
          samples(i) = phase
          phase += phaseInc
          if (phase > 1)
            phase -= 2
        }
    }

    samples
  }
}
