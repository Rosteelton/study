package hhTasks

object GreatNumbers extends App{


  final case class Index(value: Int) extends AnyVal

  def isGreat(i: Int): Boolean = {
    val original: Vector[Char] = i.toString.toCharArray.toVector

    val structure: Vector[(Index, Int, Boolean)] =
      original.zipWithIndex.map(some => (Index(some._2), some._1.toInt, false))

    var n = 0

    for (i <- original.indices){
      while(i < original.size) {
        //todo
      }


    }
  }

  //counter should start from 1
  def goThrow(mas: Vector[(Index, Int, Boolean)], counter: Int): Vector[(Index, Int, Boolean)] = {
    var masFromFunction: Vector[(Index, Int, Boolean)] = mas
    counter match {
      case c if c > mas.size => mas
      case c => //need optimize
        val newArr = mas.take(counter)
        if (newArr.map(_._2).sum == 10) {
          val updated = newArr.flatMap(el => masFromFunction.updated(el._1.value, (el._1, el._2, true)))
          goThrow(updated, counter + 1)
        } else goThrow(mas, counter + 1)
    }


  }


}
