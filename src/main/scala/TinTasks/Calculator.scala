package TinTasks

import scala.collection.immutable.::

object Calculator extends App {

  val regExpAll = """[0-9]+|(\+|\*|\-)""".r
  val regExpNumbers = """[0-9]+""".r
  val regExpSymbols = """(\+|\*|\-)""".r

  val test = "9+4*2*2-1*3-5"

  val numbers = regExpNumbers.findAllIn(test).toList
  val symbols = regExpSymbols.findAllIn(test).toList

  assert(numbers.length == symbols.length + 1, "Numbers length should be equal symbols length + 1")
  assert(numbers.length < 9, "Length should be < 9")

  val source: List[String] = regExpAll.findAllIn(test).toList

  def evaluateMultiplies(line: List[String]): List[String] = {
    line match {
      case n1 :: "*" :: n2 :: rest => evaluateMultiplies((n1.toLong * n2.toLong).toString :: rest)
      case List(n1,"*",n2) => List((n1.toLong * n2.toLong).toString)
      case Nil => List.empty;
      case head :: tail => head :: evaluateMultiplies(tail)
    }
  }

  def evaluateRemains(line: List[String]): Long = {
    line match {
      case n1 :: "+" :: n2 :: rest => evaluateRemains((n1.toLong + n2.toLong).toString :: rest)
      case n1 :: "-" :: n2 :: rest => evaluateRemains((n1.toLong - n2.toLong).toString :: rest)
      case value :: Nil => value.toLong
    }
  }

  def calculate: List[String] => Long =
    line => evaluateRemains(evaluateMultiplies(line))


  println(calculate(source))
}
