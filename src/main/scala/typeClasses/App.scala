package typeClasses

object App extends App{

  println(Addable.sum(List(1,2,3,4,5)))
  println(Addable.sum(List("1","2")))

}
