package com.simonsen.scaud

class Bars(bpm: Double, beatsInBar: Int = 4) {
  val rate = 60 / bpm * sr

  def toSamples(bar: Int, beat: Double) : Double = {
    (bar * beatsInBar + beat) * rate
  }
}
