package redBook.chapter5

object Lazy extends App {

  val stream = Stream.apply(1,2,3,4,5)

  println(stream.take(3).toList)
  println(stream.toList)
  println(stream.takeWhile((x: Int) => x <=4).toList)
  println(stream.forAll((x: Int) => x<=4))
  println(stream.takeWhileWithFoldRight((x: Int) => x <=4).toList)
  println(stream.map((some: Int) => some * 2).toList)
  println(stream.filter((x: Int) => x % 2== 0).toList)
  println(Stream.ones.takeWhile(_ == 1))
  println(Stream.constant(6).take(6).toList)
  println(Stream.from(5).take(5).toList)

}
