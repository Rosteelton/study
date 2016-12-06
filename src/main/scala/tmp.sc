def func(list: List[Int]): (Int, Int) = {
  (
    list.reduceLeft((acc, next) => if (next % 2 == 0 && next > acc) next else acc),
    list.reduceLeft((acc, next) => if (next % 2 != 0 && next > acc) next else acc)
  )
}

val a = List(1, 3, 5, 3, 10, 6, 4)
val b: List[Int] = List(1)
func(b)
