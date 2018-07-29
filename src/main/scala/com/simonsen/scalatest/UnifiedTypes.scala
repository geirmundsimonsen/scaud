package com.simonsen.scalatest

object UnifiedTypes {
  def main(args: Array[String]): Unit = {
    // Any holds some methods for every type
    val any1: Any = "hey"
    val any2: Any = 2
    println(any2 + "wow") // + is defined on Any - prob. causes toString to run
    println(any2.toString) // 2
    println(any2) // 2

    val list: List[Any] = List(
      "a string", // ok, a string
      732, // yey, an int, a char and a bool
      'c',
      true,
      733,
      () => "foo" // and an anom. function!
    )

    list.filter(x => x.isInstanceOf[Int]).foreach(x => println(x)) // simple and easy

    // Nothing is a subtype of all types. Any. String. Int. No VALUE can have type Nothing.
    // Null is a subtype of all AnyRef types. String. Not Int. Has the value defined by the literal 'null'.
    // Should almost never be used in scala code.
  }
}