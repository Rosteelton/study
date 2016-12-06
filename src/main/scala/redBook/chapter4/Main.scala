package redBook.chapter4

object Main  extends App {



  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    for {
    a <- a
    b <- b
    } yield f(a,b)
  }

  println(map2(Some(2), Some(3))((x: Int, y: Int) => x + y))

  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
    if (a.forall(_.isDefined)) Some(a.map(_.get))
    else None
  }

  println(sequence(List(Some(2), Some(3))))


  def sequence2[A](a: List[Option[A]]): Option[List[A]] =
    a match {
      case Nil => Some(Nil)
      case h :: t => h flatMap (hh => sequence(t) map (hh :: _))
    }
}
