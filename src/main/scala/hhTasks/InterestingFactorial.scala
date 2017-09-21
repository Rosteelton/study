package hhTasks


object InterestingFactorial extends App {

  def factorial(n: Int): Int = n match {
    case 0 => 1
    case _ => n * factorial(n - 1)
  }

  def calculate(from: Long, to: Long): Long = {
    from.to(to).foldLeft(0L) { (acc, number) =>
      acc + Stream.from(1, 1)
        .find { n =>
          factorial(n) % number == 0
        }.getOrElse(0)
    }
  }

  assert(factorial(5) == 120)

  println(calculate(6,10))
  println(calculate(5,6))


}
