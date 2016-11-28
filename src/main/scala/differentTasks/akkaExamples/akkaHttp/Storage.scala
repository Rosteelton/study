package differentTasks.akkaExamples.akkaHttp
import App.Message
/**
  * Created by ansolovev on 30.05.16.
  */
case class Storage() {

  var list = List.empty[Message]

  def add(message: Message): List[Message] = {
    list = message :: list
    list
  }
  def get: List[Message] = list
}


object Storage {
  val myStorage = new Storage()
}

object AuthBase {
  val usersList = Map("Anton" -> "12345", "Ivan" -> "1234")
}
