package differentTasks.akkaExamples.akkaHttp

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.model.{HttpEntity, _}
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.io.StdIn
import scala.util.{Failure, Random, Success}

object App extends App {


  case class Message(name: String, content: String, date: Int)

  implicit val MessageJsonFormat: RootJsonFormat[Message] = jsonFormat3(Message)

  case class AddElement(message: Message)

  case object ShowElements


  implicit lazy val system = ActorSystem("http-system")
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit lazy val executionContext = system.dispatcher

  private val logging: ActorRef = system.actorOf(Props[LogActor])


  //u can import all routes drom different files
  //val route = {
  //some.helloRoute ~ some.hiRoute
  //}

  val source: Source[Int, NotUsed] = Source.fromIterator(() => Iterator.continually(Random.nextInt))

  val route =
    path("messages") {
      extractCredentials {
        someCred =>
          get {
            someCred match {
              case Some(BasicHttpCredentials(user, password)) =>
                AuthBase.usersList.get(user) match {
                  case Some(pass) if pass == password => {
                    implicit val timeout: akka.util.Timeout = 5.seconds
                    val list: Future[List[Message]] = (logging ? ShowElements).mapTo[List[Message]]
                    onComplete(list) {
                      case Success(someList) => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, someList.mkString("<p></p>")))
                      case Failure(_) => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "Crash!"))
                    }
                  }
                  case None => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "Unregistered user!"))
                }
              case None => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "Please log in!"))
            }
          }
      }
    } ~
      post {
        entity(as[Message]) { message =>
          logging ! AddElement(message)
          complete(s"${message.toString} added!")
        }
      } ~
      path("hello" / Segment) { someName =>
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1> Hello $someName! </h1>"))
        }
      } ~
      path("greetings") {
        get {
          parameter("name1", "name2") { (name1, name2) =>
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1> Hello ${name1 + name2} </h1>"))
          }
        }
      } ~
      path("helloid" / IntNumber) { someNumber =>
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1> Hello $someNumber! </h1>"))
        }
      } ~
      path("randomnumbers") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, source.map(n => ByteString(s"$n\n"))))
        }
      }


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())


}






