package differentTasks.akkaExamples.akkaActor

import akka.actor._

object App{

  case class Greet(name: String)

  case class Praise(name: String)

  case class Celebrate(name: String, age: Int)


class Master extends Actor {

  val talker: ActorRef = context.actorOf(Props[Talker], "talker")

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    context.watch(talker)
    talker ! Greet("Anton")
    talker ! Praise("Anton")
    talker ! Celebrate("Anton", 24)
    talker ! PoisonPill
  }

  override def receive: Receive = {
    case Terminated(tal) if tal == talker => context.system.terminate()
  }
}

  class Talker extends Actor {
    override def receive: PartialFunction[Any, Unit] = {
      case Greet(name) => println(s"Hello $name!")
      case Praise(name) => println(s"you cool $name")
      case Celebrate(name, age) => println(s"congratulate $name with $age birthday!")
    }
  }

  val system = ActorSystem("Tutorial")
  val Master: ActorRef = system.actorOf(Props[Master])


}
