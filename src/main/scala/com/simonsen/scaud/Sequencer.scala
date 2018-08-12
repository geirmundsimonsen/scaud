package com.simonsen.scaud

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Sequencer extends Iterator[Double] {
  val iterArray = new ArrayBuffer[Iterator[Double]]
  val sortedIter = new mutable.TreeSet[(Iterator[Double], Int)]()(IterOrdering)
  var counter = 0

  def place(iter: Iterator[Double], toSample: Double) : Unit = {
    iterArray.append(iter)
    sortedIter.+=((iter, toSample.toInt))
  }

  def hasNext() : Boolean = {
    sortedIter.foreach(tuple => if (!tuple._1.hasNext) sortedIter.-=(tuple))
    sortedIter.nonEmpty
  }

  def next() : Double = {
    var out = 0.0
    sortedIter.takeWhile(tuple => tuple._2 <= counter).foreach(tuple => out += tuple._1.next)
    counter += 1
    out
  }
}

object IterOrdering extends Ordering[(Iterator[Double], Int)] {
  def compare(a:(Iterator[Double], Int), b:(Iterator[Double], Int)) = a._2 compare b._2
}