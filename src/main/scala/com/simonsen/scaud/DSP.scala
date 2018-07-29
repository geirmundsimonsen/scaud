package com.simonsen.scaud

class DSP {
  def saw(numsamples: Int, freq: Any): Array[Double] = {
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
}
