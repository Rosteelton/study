package redBook.chapter7

import java.util.concurrent._



object Par {


  type Par[A] = ExecutorService => Future[A]


  private case class UnitFuture[A](get: A) extends Future[A] {
    override def isCancelled: Boolean = false
    override def get(timeout: Long, unit: TimeUnit): A = get
    override def cancel(mayInterruptIfRunning: Boolean): Boolean = false
    override def isDone: Boolean = true
  }


  scala.concurrent.Future

  def unit[A](a: A): Par[A] = {
    (es: ExecutorService) => UnitFuture(a)
  }

  def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C] = {
    (es: ExecutorService) => {
      val aa: Future[A] = a(es)
      val bb: Future[B] = b(es)
      UnitFuture(f(aa.get(), bb.get()))
    }
  }

  def fork[A](a: => Par[A]): Par[A] = {
    es => es.submit(new Callable[A] {
      def call = a(es).get
    })
  }

  def map[A,B](fa: Par[A])(f: A => B): Par[B] =
    map2(fa, unit(()))((a,_) => f(a))

  def parMap[A,B](l: List[A])(f: A => B): Par[List[B]] = {
    ???
  }


  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)
}



