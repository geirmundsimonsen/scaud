package com.simonsen.scaud

import scala.collection.mutable.ArrayBuffer

object Song {
  def main(args: Array[String]): Unit = {
    startSoundSystem()
    val buf = new ArrayBuffer[Double]
    val bars = new Bars(100)
    val pitches = Array(60, 63, 65, 67, 70, 72)
    val a = System.currentTimeMillis()
    pitches.cycle.take(6 * 8).zipWithIndex.foreach({ case(pitch, i) => buf.place(patch2(pitch), bars.toSamples(0, i * 0.5))})
    buf.toArray
    val b = System.currentTimeMillis()
    println(b - a)
    //play(buf.toArray)

    val c = System.currentTimeMillis()
    val seq: Sequencer = new Sequencer
    pitches.cycle.take(6 * 8).zipWithIndex.foreach(tuple => seq.place(patch2(tuple._1), bars.toSamples(0, tuple._2 * 0.5)))
    //seq.toArray
    val d = System.currentTimeMillis()
    println(d - c)
    play(seq)
  }

  def patch1(): Iterator[Double] = {
    val falling = saw(exp(1, -40, 0) * 200 + 200).map(x => if (x > 0.9) random.nextInt(3) - 1 else x) * exp(1, -24, 0.001)
    val rising = saw(exp(1, -40, 0) * -200 + 200).map(x => if (x > 0.9) random.nextInt(3) - 1 else x) * exp(1, -24, 0.001)

    falling + rising
  }

  def patch2(pitch: Double): Iterator[Double] = {
    val sig = saw(p2f(pitch + random.nextDouble() * 0.04 - 0.02)) * exp(1, -20, 0.01)
    sig
  }
}