package typeClasses

trait Addable[T] {
  def zero: T
  def append(a: T, b: T): T
}

object Addable {

  implicit object IntIsAddable extends Addable[Int] {
    def zero = 0
    def append(a: Int, b: Int) = a + b
  }

  implicit object StringIsAddable extends Addable[String] {
    def zero = ""
    def append(a: String, b: String) = a + b
  }

  def sum[T](xs: List[T])(implicit addable: Addable[T]) =
    xs.foldLeft(addable.zero)(addable.append)
}

