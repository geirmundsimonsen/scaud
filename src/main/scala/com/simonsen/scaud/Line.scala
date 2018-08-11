package com.simonsen.scaud

class Line(start: Double, end: Double, t: Double) extends Iterator[Double] {
  var value: Double = start
  val step: Double = (end - start) * invsr * (1 / t)
  val n: Int = ((end - start) / step).toInt
  val slope = if (end > start) Rising else if (end < start) Falling else Flat

  def hasNext() : Boolean = {
    slope match {
      case Rising => value < end
      case Falling => value > end
      case Flat => false
    }
  }

  def next() : Double = {
    val out = value
    value += step
    out
  }

  sealed trait Slope { def name: String }
  case object Rising extends Slope { val name = "Rising" }
  case object Falling extends Slope { val name = "Falling" }
  case object Flat extends Slope { val name = "Flat" }
}

