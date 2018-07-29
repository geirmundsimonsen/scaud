package com.simonsen.scaud

class Multiply(a: Any, b: Any) extends Iterator[Double] {
  val aIter = a match {
    case a: Iterator[Double] => a
    case a: Double => Iterator.continually(a)
  }

  val bIter = b match {
    case b: Iterator[Double] => b
    case b: Double => Iterator.continually(b)
  }

  def hasNext() : Boolean = {
    aIter.hasNext && bIter.hasNext
  }

  def next() : Double = {
    aIter.next() * bIter.next()
  }
}
