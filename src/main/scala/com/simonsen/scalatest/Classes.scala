package com.simonsen.scalatest

object Classes {
  def main(args: Array[String]): Unit = {
    val user1 = new User
    val user2 = new User
    // yep, it's false. Not a case class - this compares memory locations.
    // in this way, we could actually distinguish between instances of classes even
    // though the class have no fields.
    println(user1 == user2)

    val point1 = new MutPoint(2, 3)
    val point2 = new MutPoint(y=5) // naming y!
    point1.move(2, 2)
    println(point1)

    val mutprivnum = new MutPrivNum
    mutprivnum.x
  }
}

class User

// mutable point class
// we already defined Point and it's within scope, so new name.
// x has default value 2. This would seem pointless, but you can NAME y! Yey!
// var, val is public. no val, var is private (is it then mutable or immutable?)
class MutPoint(var x: Int = 2, var y: Int) {
  def move(dx: Int, dy: Int): Unit = {
    x = x + dx
    y = y + dy
  }

  override def toString: String = s"($x, $y)" // string interpolation
}

class MutPrivNum {
  private var _x = 0 // notice putting field here (rather than at class line)
  private val bound = 100

  def x = _x
  def x_= (newValue: Int): Unit = { // yep , x_= is set. Not
    if (newValue < bound) _x = newValue else printWarning // yep, else is an abort mechanism
  }

  private def printWarning = println("WARNING: Out of bounds, fool!")
}