package com.simonsen.scaud

class Exp(start: Double, ratePrSecond: Double, limit: Double) extends Iterator[Double] {
  var value = start
  //val db2amp =  // convert to db, else the values are difficult to work with
  val factor = Math.exp(Math.log(ratePrSecond) * invsr)
  val rising = factor > 1

  require(ratePrSecond != 1)

  def hasNext() : Boolean = {
    if (rising) {
      value < limit
    } else {
      value > limit
    }
  }

  def next() : Double = {
    val out = value
    value *= factor
    out
  }
}
