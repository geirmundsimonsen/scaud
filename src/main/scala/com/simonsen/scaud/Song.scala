package com.simonsen.scaud

object Song {
  def main(args: Array[String]): Unit = {
    startSoundSystem()
    val falling = saw(exp(1, -40, 0) * 200 + 200).map(x => if (x > 0.9) random.nextInt(3) - 1 else x) * exp(1, -24, 0.001)
    val rising = saw(exp(1, -40, 0) * -200 + 200).map(x => if (x > 0.9) random.nextInt(3) - 1 else x) * exp(1, -24, 0.001)
    play(patch1())
  }

  def patch1(): Iterator[Double] = {
    val falling = saw(exp(1, -40, 0) * 200 + 200).map(x => if (x > 0.9) random.nextInt(3) - 1 else x) * exp(1, -24, 0.001)
    val rising = saw(exp(1, -40, 0) * -200 + 200).map(x => if (x > 0.9) random.nextInt(3) - 1 else x) * exp(1, -24, 0.001)
    falling + rising
  }
}