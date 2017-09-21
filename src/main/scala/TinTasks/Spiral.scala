package TinTasks

object Spiral extends App {

  def calculate(xNx: Int): Long = {
    xNx match {
      case 1 => 1
      case 0 => 0
      case n => n*n + (n*n-(n-1)) + (n*n - 2*(n-1)) + (n*n - 3*(n-1)) + calculate(n-2)
    }
  }

  assert(calculate(3) == 25)
  assert(calculate(5) == 101)

  println(calculate(1091))
}
