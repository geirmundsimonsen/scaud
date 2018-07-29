package com.simonsen.scaud

import java.nio.{ByteBuffer, ByteOrder}

import javax.sound.sampled.SourceDataLine

import scala.util.Random

object Main {
  var sourcedataline: SourceDataLine = _
  val sr = 44100
  val invsr : Double = 1.0 / sr
  val tau : Double = Math.PI * 2
  val random = new Random()

  implicit class Sample(samples: Array[Double]) {
    def +(x: Double) : Array[Double] = {
      for (i <- samples.indices) {
        samples(i) += x
      }
      samples
    }

    def +(arr: Array[Double]) : Array[Double] = {
      for (i <- samples.indices) {
        samples(i) += arr(i)
      }
      samples
    }

    def *(x: Double) : Array[Double] = {
      for (i <- samples.indices) {
        samples(i) *= x
      }
      samples
    }

    def *(arr: Array[Double]) : Array[Double] = {
      for (i <- samples.indices) {
        samples(i) *= arr(i)
      }
      samples
    }

    def f(f: Double => Double) : Array[Double] = {
      for (i <- samples.indices) {
        samples(i) = f(samples(i))
      }
      samples
    }
  }

  implicit class Sample2(samples: Iterator[Double]) {
    def *(other: Iterator[Double]) : Iterator[Double] = {
      new Multiply(samples, other)
    }

    def *(other: Double) : Iterator[Double] = {
      new Multiply(samples, Iterator.continually(other))
    }
  }

  def main(args: Array[String]): Unit = {
    startSoundSystem()

    play(saw(100.0) * saw(332.0) * line(1, 0, 1))
  }

  def startSoundSystem(): Unit = {
    println("Starting sound system")
    javax.sound.sampled.AudioSystem.getMixerInfo map(x => x.getName) foreach println
    //val mixerinfo = javax.sound.sampled.AudioSystem.getMixerInfo().filter(mixerinfo => "Speakers (RME Fireface UC)".equals(mixerinfo.getName())).apply(0)
    val mixerinfo = javax.sound.sampled.AudioSystem.getMixerInfo().filter(mixerinfo => "default [default]".equals(mixerinfo.getName)).apply(0)
    println(mixerinfo.getName())
    val audioformat = new javax.sound.sampled.AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
    println(audioformat)
    sourcedataline = javax.sound.sampled.AudioSystem.getSourceDataLine(audioformat, mixerinfo)
    sourcedataline.open()
    sourcedataline.start()
  }

  def impulse(): Array[Double] = {
    Array(1)
  }

  def impulsetrain(): Array[Double] = {
    val samples = Array.fill[Double](44100)(0)
    for (i <- 0 until 44100 by 100) {
      samples.update(i, 1)
    }
    samples
  }

  def manySines(): Array[Double] = {
    val a = System.currentTimeMillis()
    val samples = Array.fill[Double](88200)(0)
    val nrOfSines = 1000
    val invNrOfSines = 1.0 / nrOfSines
    for (sine <- 0 until nrOfSines) {
      val freq = random.nextDouble() * 300 + 200
      val length = random.nextInt(88200)
      for (index <- 0 until length) {
        samples(index) += Math.sin(invsr * tau * index * freq)
      }
    }
    for (index <- 0 until 88200) {
      samples(index) *= invNrOfSines
    }
    println("manySines: " + (System.currentTimeMillis() - a) + " ms")
    samples
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
}

