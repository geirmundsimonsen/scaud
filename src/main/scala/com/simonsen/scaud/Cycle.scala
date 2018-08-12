package com.simonsen.scaud

class Cycle[T](array: Array[T]) extends Iterator[T] {
  var idx = -1

  def hasNext() : Boolean = {
    true
  }

  def next() : T = {
    idx += 1
    if (idx == array.length) {
      idx = 0
    }
    array(idx)
  }
}
