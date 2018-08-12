package com.simonsen

import javax.sound.sampled.SourceDataLine
import java.nio.{ByteBuffer, ByteOrder}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

package object scaud {
  var sourcedataline: SourceDataLine = _
  val sr = 44100
  val invsr : Double = 1.0 / sr
  val tau : Double = Math.PI * 2
  val random = new Random()

  implicit class Sample2(samples: Iterator[Double]) {
    def *(other: Iterator[Double]) : Iterator[Double] = {
      new Multiply(samples, other)
    }

    def *(other: Double) : Iterator[Double] = {
      new Multiply(samples, Iterator.continually(other))
    }

    def +(other: Iterator[Double]) : Iterator[Double] = {
      new Add(samples, other)
    }

    def +(other: Double) : Iterator[Double] = {
      new Add(samples, other)
    }
  }

  implicit class Sample3(samples: ArrayBuffer[Double]) {
    def place(iter: Iterator[Double], at: Double): ArrayBuffer[Double] = {
      val atInt = at.toInt
      for (x <- Range(samples.length, atInt)) {
        samples.append(0);
      }
      iter.zipWithIndex.foreach { case(x, i) => if (samples.length <= i + atInt) {
        samples.append(x)
      } else {
        samples(i + atInt) = samples(i + atInt) + x
      } }
      println(samples.length)
      samples
    }
  }

  implicit class ArrayExtension[T](array: Array[T]) {
    def cycle(): Iterator[T] = {
      new Cycle(array)
    }
  }

  def startSoundSystem(): Unit = {
    println("Starting sound system")

    var device = if ("gesi".equals(System.getProperty("user.name"))) {
      "default [default]"
    } else {
      "Speakers (RME Fireface UC)"
    }

    val mixerinfo = javax.sound.sampled.AudioSystem.getMixerInfo().filter(mixerinfo => device.equals(mixerinfo.getName())).apply(0)
    val audioformat = new javax.sound.sampled.AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
    sourcedataline = javax.sound.sampled.AudioSystem.getSourceDataLine(audioformat, mixerinfo)
    sourcedataline.open()
    sourcedataline.start()
  }

  def play(samples: Array[Double]): Unit = {
    val buffer = ByteBuffer.allocate(samples.length * 2 * 2)
    buffer.order(ByteOrder.LITTLE_ENDIAN)
    samples.foreach(sample => {
      val pcm16sample : Short = if (sample > 1) 32767 else if (sample < -1) -32767 else (sample * 32767).asInstanceOf[Short]
      buffer.putShort(pcm16sample)
      buffer.putShort(pcm16sample)
    })
    sourcedataline.write(buffer.array, 0, buffer.array.length)
  }

  def play(samples : Iterator[Double]) : Unit = {
    play(samples.toArray)
  }

  def db2amp(db: Double): Double = {
    Math.exp(db/8.65618)
  }

  def p2f(pitch: Double): Double = {
    440 * Math.pow(2, (pitch-69)/12)
  }

  def saw(numsamples: Int, freq: Any) : Array[Double] = {
    val samples = new Array[Double](numsamples)
    var phase: Double = 0

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

  def line(start: Double, end: Double, t: Double): Iterator[Double] = {
    new Line(start, end, t)
  }

  def saw(freq: Any) : Iterator[Double] = {
    new SawIter(freq)
  }

  def exp(start: Double, dbPrSec: Double, limit: Double) : Iterator[Double] = {
    new Exp(start, dbPrSec, limit)
  }
}
