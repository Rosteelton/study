package redBook.chapter5

trait Stream[+A] {

  def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
    this match {
      case Cons(h,t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
      case _ => z
    }

  def toList: List[A] = {
    def step[A](a: Stream[A]): List[A] = {
      a match {
        case Empty => Nil
        case Cons(h, t) => h() :: step(t())
      }
    }
    step(this)
  }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.

  @annotation.tailrec
  final def find(f: A => Boolean): Option[A] = this match {
    case Empty => None
    case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
  }

  def take(n: Int): Stream[A] = {
    def step[A](a: Stream[A], n: Int): Stream[A] = {
      a match {
        case Empty => Empty
        case Cons(h, t) =>
          if (n > 0) Stream.cons(h(), step(t(), n - 1))
          else Empty
      }
    }
    step(this, n)
  }

  def drop(n: Int): Stream[A] = sys.error("todo")

  def takeWhile(p: A => Boolean): Stream[A] = {

    def step(a: Stream[A]): Stream[A] = {
      a match {
        case Empty => Empty
        case Cons(h, t) =>
          if (p(h())) Stream.cons(h(), step(t()))
          else Empty
      }
    }
    step(this)
  }

  def takeWhileWithFoldRight(p: A => Boolean): Stream[A] = {
    foldRight(Empty: Stream[A])((h: A, t) => if (p(h)) Stream.cons(h, t) else Empty)
  }

  def forAll(p: A => Boolean): Boolean = {
    this match {
      case Empty => true
      case Cons(h, t) =>
        if (p(h())) t().forAll(p)
        else false
    }
  }

  def headOption: Option[A] = {
    this match {
      case Empty => None
      case Cons(h, _) => Some(h())
    }
  }

  def map[B](f: A => B): Stream[B] = {
//    this match {
//      case Empty => Empty
//      case Cons(h, t) => Stream.cons(f(h()), t().map(f))
//    }
    foldRight(Empty: Stream[B])((h: A, t) => Stream.cons(f(h), t))
  }

  def filter(f: A => Boolean): Stream[A] = {
    foldRight(Empty: Stream[A])((element: A, acc) => if (f(element)) Stream.cons(element, acc) else acc)
  }

  def append[B >: A](a: => Stream[B]): Stream[B] =
    this.foldRight(a)((element, stream) => Stream.cons(element, stream))


  def flatMap[B](f: A => Stream[B]): Stream[B]= {
    foldRight(Empty: Stream[B])((h,t) => f(h) append t)
  }




  def startsWith[B](s: Stream[B]): Boolean = sys.error("todo")
}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = Stream.cons(1, ones)

  def constant[A](a: A): Stream[A] = {
    Stream.cons(a, constant(a))
  }

  def from(n: Int): Stream[Int] = {
    Stream.cons(n, from(n+1))
  }

}
