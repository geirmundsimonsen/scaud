package com.simonsen.scaud

object Main {
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

  def main(args: Array[String]): Unit = {
    startSoundSystem()
    play(saw(100.0) * line(1, 0, 0.1))
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
}

