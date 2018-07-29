package com.simonsen.scalatest

object Basics {
  def add(x: Int, y: Int): Int = x + y // a method. See, no expression block!
  def multiplyThenMultiply(x: Int, y: Int)(multiplier: Int): Int = x * multiplier * y
  // multiplyThenMultiply(1, 2)(3) // you would get 6. Don't really know the purpose of this one. We should be able to do partial application anyway
  def name: String = System.getProperty("user.name") // sneaky! This is a method that takes no args and returns a string. Look, no parameter list!

  def main(args: Array[String]): Unit = {
    println(1) // 1
    println(1 + 1) // 2
    println("Hello!") // Hello!
    println("Hello," + " world!") // Hello, world!

    val x = 1 + 1
    // x = 3 - nope.
    val y: Double = 1 + 1

    var z = 1 + 1
    z = 3
    println(z * z)

    // combining expressions with blocks
    println({
      val x = 1 + 1
      x + 1
    })

    val xWithBlock = {
      val x = 5 // aha! the block scope DO return a value.
      val y = 6 // the scope, is then itself an expression,
      x * y // and can be placed wherever you can place an expression.
    }
    println(xWithBlock)

    val inc = (x: Int, y: Int) => x + y + 1 // note, a function is an expression
    val constantOrJustForSideEffects = () => 42
    println(inc(5, 6))

    //val incFail = (x) => x + 1 // must have parameter type

    println(name) // look, no parenthesis! remember, name is a method, not a variable

    val greeter = new Greeter("Mr. PhD. Dr. Prof. ", " jr. sr.")
    greeter.greet("<InsertIndianName>")

    var p1 = Point(1, 2) // p1 is allowed to be a variable. It's Point(1, 2) which is immutable
    val p2 = Point(1, 3)
    println("p1 equal p2: " + (p1 == p2))
    p1 = Point(1, 3)
    println("p1 equal p2: " + (p1 == p2))
    // p1.x = 2 // p1.x, y is val. (immutable!)

    IdFactory.createId()
    IdFactory.createId()
    IdFactory.createId()
    IdFactory.createId()
    println(IdFactory.createId())
  }
}

// new ClassName(args...) can't access prefix, suffix after creation.
class Greeter(prefix: String, suffix: String) {
  def greet(name: String): Unit = println(prefix + name + suffix) // Unit is sim. to void
}

// a case class is an immutable value type, equality defined by the fields
case class Point(x: Int, y: Int)

// single instances of their own definitions / singletons of their own classes
object IdFactory {
  private var counter = 0 // or else anyone with access to IdFactory can modify it
  def createId(): Int = {
    counter += 1
    counter
  }
}

trait Meeter {
  def meet(name: String): Unit
}

trait Meeter2 {
  def meet(name: String): Unit = println("Hello, " + name + "!") // default impl.
}

// class DefaultMeeter extends Meeter // nope, it has to be defined abstract
abstract class AbstrDefMeeter extends Meeter // yep
class DefaultMeeter2 extends Meeter2

class CustomizedMeeter2(prefix: String, postfix: String) extends Meeter {
  override def meet(name: String): Unit = {
    println(prefix + name + postfix)
  }
}