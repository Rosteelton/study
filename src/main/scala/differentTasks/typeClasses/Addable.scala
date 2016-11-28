package differentTasks.typeClasses

trait Addable[T] {
  def zero: T
  def append(a: T, b: T): T
}

object Addable {

  implicit object IntIsAddable extends Addable[Int] {
    def zero = 0
    def append(a: Int, b: Int) = a + b
  }

  implicit val IntIsAddable2 = new Addable[Int] {
    override def zero: Int = 100
    override def append(a: Int, b: Int): Int = a + b + zero
  }

  implicit object StringIsAddable extends Addable[String] {
    def zero = ""
    def append(a: String, b: String) = a + b
  }

  def sum[T](xs: List[T])(implicit addable: Addable[T]) =
    xs.foldLeft(addable.zero)(addable.append)

  def zeroFunc[T](i: T)(implicit addable: Addable[T]) = {
    addable.zero
  }

  def appendFunc[T](a: T, b: T)(implicit addable: Addable[T]) = {
    addable.append(a,b)
  }
}

