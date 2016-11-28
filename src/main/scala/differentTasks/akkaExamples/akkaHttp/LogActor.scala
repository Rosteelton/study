package differentTasks.akkaExamples.akkaHttp

import akka.actor.{Actor, ActorLogging}
import differentTasks.akkaExamples.akkaHttp.App._

/**
  * Created by ansolovev on 30.05.16.
  */
class LogActor extends Actor with ActorLogging{
  override def receive: Receive = {

    case AddElement(mess) =>
      log.info("Add message")
      Storage.myStorage.add(mess)

    case ShowElements =>
      log.info("messages have been sent")
      sender ! Storage.myStorage.get
  }
}

