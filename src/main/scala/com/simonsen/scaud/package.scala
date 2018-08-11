package com.simonsen

import javax.sound.sampled.SourceDataLine

import java.nio.{ByteBuffer, ByteOrder}

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
  }

  def startSoundSystem(): Unit = {
    println("Starting sound system")
    val mixerinfo = javax.sound.sampled.AudioSystem.getMixerInfo().filter(mixerinfo => "Speakers (RME Fireface UC)".equals(mixerinfo.getName())).apply(0)
    //val mixerinfo = javax.sound.sampled.AudioSystem.getMixerInfo().filter(mixerinfo => "default [default]".equals(mixerinfo.getName)).apply(0)
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
}
