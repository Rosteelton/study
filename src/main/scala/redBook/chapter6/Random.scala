package redBook.chapter6

import redBook.chapter6.RNG.Simple

object Random extends App {

  val simpleGNR = Simple(1111)
  println(simpleGNR.nextInt._1)
  println(simpleGNR.nextInt._2.nextInt._1)
  println(RNG.double(simpleGNR)._1)

  println(RNG.intDouble(simpleGNR)._1)

  println(RNG.doubleInt(simpleGNR)._1)
  println(RNG.double3(simpleGNR)._1)

  println(RNG.ints(5)(simpleGNR)._1)

  println(RNG.doubleWithMap(simpleGNR)._1)

  println(RNG.intDoubleWithMap2(simpleGNR)._1)
}
