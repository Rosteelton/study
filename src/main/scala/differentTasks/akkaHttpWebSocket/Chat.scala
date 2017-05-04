package differentTasks.akkaHttpWebSocket

import akka.NotUsed
import akka.actor._
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl._

object Chat {

  implicit val actorSystem = ActorSystem("akka-system")
  implicit val flowMaterializer = ActorMaterializer()
  val chatActor: ActorRef = actorSystem.actorOf(Props(classOf[ChatActor]))


  def webSocketChatFlow(user: String): Flow[Message, Message, _] = {
    Flow[Message]
      .collect {
        case TextMessage.Strict(txt) => txt
      }
      .via(chatFlow(user))
      .map(msg => TextMessage.Strict(s"$user: $msg"))
  }


  def chatFlow(sender: String): Flow[String, ChatMessage, Any] = {

    val sink: Sink[String, NotUsed] = Flow[String].map(MessageReceived(sender, _)).to(Sink.actorRef[ChatEvent](chatActor, UserLeft(sender)))

    val source: Source[ChatMessage, Unit] = Source.actorRef[ChatMessage](1, OverflowStrategy.fail)
      .mapMaterializedValue(chatActor ! UserJoined(sender, _))

    Flow.fromSinkAndSource(sink, source)
  }


}
