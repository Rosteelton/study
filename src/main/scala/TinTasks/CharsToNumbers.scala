package TinTasks

object CharsToNumbers extends App {

  case class First(r: Int, t: Int, r2: Int, q: Int) {
    override def toString: String =
      r.toString + t.toString + r2.toString + q.toString
  }
  case class Second(r: Int, q: Int, r2: Int, s: Int) {
    override def toString: String =
      r.toString + q.toString + r2.toString + s.toString
  }
  case class Result(s: Int, t: Int, s1: Int, s2: Int) {
    override def toString: String =
      s.toString + t.toString + s1.toString + s2.toString
  }

  val all = for {
    r <- 1 to 9
    t <- 0 to 9
    q <- 0 to 9
    s <- 1 to 9
  } yield (First(r, t, r, q), Second(r, q, r, s), Result(s, t, s, s))

  val result = all.foldLeft(0) { (acc, mass) =>
    if (mass._1.toString.toInt + mass._2.toString.toInt == mass._3.toString.toInt) {
      acc + 1
    } else acc
  }

  println(result)

}
