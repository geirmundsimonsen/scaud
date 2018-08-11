package com.simonsen.scaud

object SinOsc {
  def get(numsamples: Int, freq: Any): Array[Double] = {
    val samples = Array.fill[Double](numsamples)(0)
    var phase = 0.0
    freq match {
      case f: Double =>
        val phaseinc = invsr * tau * f
        for (x <- samples.indices) {
          samples(x) = Math.sin(phase)
          phase += phaseinc
        }
      case f: Array[Double] =>
        for (x <- samples.indices) {
          samples(x) = Math.sin(phase)
          phase += invsr * tau * f(x)
        }
    }

    samples
  }
}
