package com.simonsen.scalatest

object Traits {
  def main(args: Array[String]): Unit = {

  }
}

// Traits: Like interface in java
// Subtyping: as normal. A collection of a supertype yadda yadda.

// this works as an identifier
trait HairColor

trait JustAnIterator[A] {
  def hasNext: Boolean // no params, but a method in a class does has access to fields
  def next: A // no params
}

// extend it in a class like you would normally do.
// Type is specified here, and could easily be a anything, not just an Int
class IntIterator(to: Int) extends JustAnIterator[String] {
  private var current = 0
  override def hasNext: Boolean = current < to
  override def next: String = {
    if (hasNext) {
      val t = current
      current += 1
      "foo"
    } else "fail"
  }
}