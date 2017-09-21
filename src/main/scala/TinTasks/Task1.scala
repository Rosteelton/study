package TinTasks

object Task1 extends App {

val result =
  Stream.from(1, 1).find(n => (2 * n).toString.sorted == (5 * n).toString.sorted)

  println(result)
  println(2*result.get)
  println(5*result.get)
}
