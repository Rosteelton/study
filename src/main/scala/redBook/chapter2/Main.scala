package redBook.chapter2

object Main extends App {

  def partial1[A,B,C](a: A, f: (A,B) => C): B => C =
    (x: B) => f(a, x)

  def curry[A,B,C](f: (A, B) => C): A => (B => C) =
    (a: A) => (b: B) => f(a, b)

  def compose[A,B,C](f: B => C, g: A => B): A => C =
    (a: A) => f(g(a))
}
