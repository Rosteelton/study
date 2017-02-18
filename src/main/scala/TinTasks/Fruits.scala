package TinTasks

import scala.collection.immutable.{::, Nil}
import scala.io.StdIn

object Fruits extends App {

  val coinsNumber: Int = StdIn.readLine().toInt
  val coins: List[Int] = StdIn.readLine().split(" ").toList.map(_.toInt)

  if (coinsNumber != coins.length) throw new IllegalArgumentException("Coins number and coinsCount are different")

    def countStartAmountOfCoins(coins: List[Int]): Int = {

      def loop(coins: List[Int], state: Int, desired: Int): Int = {
       coins match {
         case Nil => desired
         case head:: tail =>
           val diff = state + parseCoin(head)
           if (diff >= 0 ) { //success case
             loop(tail, diff, desired)
           } else {
             loop(tail, 0, desired + Math.abs(diff))
           }
       }
      }

      def parseCoin(coin: Int): Int = {
        coin match {
          case 5 => 1
          case 10 => -1
          case 50 => -9
          case 100 => -19
          case _ => throw new IllegalArgumentException("Wrong coin")
        }
      }
      loop(coins, 0, 0)
    }

  println(countStartAmountOfCoins(coins))

}
