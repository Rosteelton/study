package akkaHttpWebSocket

import akka.NotUsed
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow
import com.typesafe.config.ConfigFactory
import Chat._

object Server extends App {

//  val echoService: Flow[Message, Message, NotUsed] = Flow[Message].map {
//    case TextMessage.Strict(txt) => TextMessage(txt)
//    case _ => TextMessage("WTF!")
//  }

  val route: Route = get {
    path("chat") {
      pathEndOrSingleSlash {
        parameter('name) { userName =>
          handleWebSocketMessages(Chat.webSocketChatFlow(userName))
        }
      }
    }
  }
  val config = ConfigFactory.load()
  Http().bindAndHandle(route, config.getString("http-server.interface"), config.getInt("http-server.port"))
}
