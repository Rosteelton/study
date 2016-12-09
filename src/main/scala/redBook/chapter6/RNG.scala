package redBook.chapter6

trait RNG {
  def nextInt: (Int, RNG) // Should generate a random `Int`. We'll later define other functions in terms of `nextInt`.
}

object RNG {
  // NB - this was called SimpleRNG in the book text

  case class Simple(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
      val nextRNG = Simple(newSeed) // The next state, which is an `RNG` instance created from the new seed.
      val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
      (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
    }
  }

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A,B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (num, state) = rng.nextInt
    (math.abs(num), state)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (nonNegative, state) = RNG.nonNegativeInt(rng)
    (nonNegative.toDouble / Int.MaxValue,state)
  }

  def intDouble(rng: RNG): ((Int,Double), RNG) = {
    val (doubleGen, newState) = double(rng)
    val (int, _) = rng.nextInt
    ((int, doubleGen), newState)
  }

  def doubleInt(rng: RNG): ((Double,Int), RNG) = {
    val gen = intDouble(rng)
    ((gen._1._2, gen._1._1), gen._2)
  }

  def double3(rng: RNG): ((Double,Double,Double), RNG) = {
    val (doubleGen1, state) = double(rng)
    val (doubleGen2, state2) = double(state)
    val (doubleGen3, state3) = double(state2)
    ((doubleGen1, doubleGen2, doubleGen3), state3)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    if (count > 0) (rng.nextInt._1 :: ints(count - 1)(rng.nextInt._2)._1, rng.nextInt._2)
    else (Nil, rng)
  }

  def positiveMax(n: Int): Rand[Int] = {
      map(nonNegativeInt)(a => if (a>n) ??? else a)
  }

  def doubleWithMap(rng: RNG): (Double, RNG) = {
    val rand: Rand[Int] = rng => nonNegativeInt(rng)
    map(rand)(int => int.toDouble/Int.MaxValue)(rng)
  }

  val _double: Rand[Double] =
    map(nonNegativeInt)(_ / (Int.MaxValue.toDouble + 1))

  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = {
//    rng => {
//      val (a, rng2) = s(rng)
//      (f(a), rng2)
    rng => {
      val (a, rng2) = ra(rng)
      val (b, rng3) = rb(rng2)
      (f(a,b), rng3)
    }
  }

  val intDoubleWithMap2: Rand[(Int,Double)] = {
    map2(nonNegativeInt, _double)((int, double) => (int,double))
  }

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = {
    fs.foldRight(unit(List[A]()))((f, acc) => map2(f, acc)(_ :: _))
  }

  def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] = {
    rng =>  {
      val (a, rng2) = f(rng)
      g(a)(rng2)
    }
  }
}

case class State[S,+A](run: S => (A, S)) {
  def map[B](f: A => B): State[S, B] = {
    this.flatMap(a => State.unit(f(a)))
  }
  def map2[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] = {
      flatMap(a => sb.map(b => f(a, b)))
  }

  def flatMap[B](f: A => State[S, B]): State[S, B] = {
    State { s =>
      val (a, state) = run(s)
      f(a).run(state)
    }
  }
}

sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)

object State {
  type Rand[A] = State[RNG, A]

  def unit[S, A](a: A): State[S, A] =
    State(s => (a, s))

  def modify[S](f: S => S): State[S, Unit] = for {
    s <- get
    _ <- set(f(s))
  } yield ()

  def get[S]: State[S, S] = State(s => (s, s))

  def set[S](s: S): State[S, Unit] = State(_ => ((), s))

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = ???
}