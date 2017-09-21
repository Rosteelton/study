package TinTasks

import scala.io.StdIn

object Monitoring {

  case class Human(start: Int, end: Int)

  type Amount = Int


  def main(args: Array[String]): Unit = {
    val line1 = StdIn.readLine().toInt

    var answers: Array[String] = Array.fill[String](line1)("")

    var i = 0
    var input: Array[String] = Array.fill[String](line1)("")

    while (i < line1) {
      input.update(i, StdIn.readLine())
      i = i + 1
    }

    i = 0
    while (i < input.length) {
      val humans: Array[Human] = parse(input(i))
      var time: Array[Boolean] = Array.fill[Boolean](10000)(false)

      var k = 0

      while (k < humans.length) {
        var j = humans(k).start
        while (j < humans(k).end) {
          time.update(j, true)
          j = j + 1
        }
        k = k + 1
      }
      //-----------
      if (time.forall(some => some == true)) {
        time = Array.fill[Boolean](10000)(true)
        //answers.update(i, "Acepted")
        var z = 0
        while (z < humans.length) {
          var s = humans(z).start
          while(s < humans(z).end) {
            time.update(s, false)
            if (time.forall(some => some == true)) {
              answers.update(i, "Wrong Answer")
            } else {

            }
            s = s + 1
          }

          z=z+1
        }
        if (answers(i) != "Wrong Answer") {
          answers.update(i, "Accepted")
        }


      } else {
        answers.update(i, "Wrong Answer")
      }

      i = i + 1
    }


    answers.foreach(println)
  }


  def parse(str: String):Array[Human] = {
    val splitted = str.split(" ").toList.map(_.toInt)
    val amount = splitted.head

    val line: Array[Int] = splitted.tail.toArray
    var starts: Array[Int] = Array.fill[Int](amount)(0)
    var ends: Array[Int] = Array.fill[Int](amount)(0)

    var k = 0
    var l = 0
    var j = 0
    while (k < line.length) {
      if (k % 2 == 0) {
        starts.update(l, line(k))
        l = l + 1
      } else {
        ends.update(j, line(k))
        j = j + 1
      }
      k = k + 1
    }
    (starts zip ends).map(pair => Human(pair._1, pair._2))
  }

}
