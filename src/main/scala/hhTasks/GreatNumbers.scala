package hhTasks

object GreatNumbers extends App{

  final case class Index(value: Int) extends AnyVal

  def isGreat(i: Int): Boolean = {
    val original: Vector[Char] = i.toString.toCharArray.toVector

    var structure: Vector[(Index, Int, Boolean)] =
      original.zipWithIndex.map(some => (Index(some._2), some._1.asDigit, false))

    for (n <- original.indices) {
      val go = goThrow(structure.drop(n), 1)
      go.foreach { some =>
        structure = structure.updated(some._1.value, some)
      }
    }
    structure.map(_._3).forall(some => some)
  }

  //counter should start from 1
  def goThrow(mas: Vector[(Index, Int, Boolean)], counter: Int): Vector[(Index, Int, Boolean)] = {
    var masFromFunction: Vector[(Index, Int, Boolean)] = mas

    counter match {
      case c if c > mas.size => mas
      case _ => //need optimize

        val newArr = mas.take(counter)

        if (newArr.map(_._2).sum == 10) {
          var i = 0
          while(i < newArr.size) {
            val el = newArr(i)
            masFromFunction = masFromFunction.updated(masFromFunction.indexOf(el), (el._1, el._2, true))
            i = i + 1
          }

          goThrow(masFromFunction, counter + 1)
        } else goThrow(masFromFunction, counter + 1)
    }
  }

  def calculate(from: Int, to: Int): Int = {
    from.to(to).foldLeft(0) {(acc, index) =>
      if (isGreat(index)) acc + 1 else acc
    }
  }

  println(isGreat(2530))
  println("\n")
  println(isGreat(25300))
  println("\n")
  println(isGreat(2553002))
  println("\n")
  println(isGreat(253002534))
  println(isGreat(3523014))
  println(isGreat(28546))
  println(isGreat(1))
  println(isGreat(10000000))
  println(isGreat(69000001))
  println(calculate(1,60))

  val result = calculate(1, 6900000)

  println(s"FINAL ROUND:${result}")
}
