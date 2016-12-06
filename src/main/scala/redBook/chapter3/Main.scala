package redBook.chapter3

object Main extends App {

  def hasSubsequence[A](l: List[A], sub: List[A]): Boolean = {
    if (sub.length <= l.length) {
      if (sub == l.take(sub.length)) true
      else hasSubsequence(l.tail, sub)
    } else false
  }


  println(hasSubsequence(List(1,2,3,4,5), List(5)))


}
