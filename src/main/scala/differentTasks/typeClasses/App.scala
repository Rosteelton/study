package differentTasks.typeClasses


object App extends App{

  println(Addable.sum(List(1,2,3,4,5)))
  println(Addable.sum(List("1","2")))

  import Addable.IntIsAddable2
  val test  = println(Addable.zeroFunc(5))
  val test2 = println(Addable.appendFunc(5,5))

}



