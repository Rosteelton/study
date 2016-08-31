package freeMonads.ex1

import cats.free.Free
import cats.implicits._
import cats.~>

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


trait External[A]
case class Tickets(count: Int) extends AnyVal
case class InvokeTicketService(count: Int) extends External[Tickets]

object Example extends App {

  def purchaseTickets(input: Int): Free[External, Option[Tickets]] = {
    if (input > 0) {
      Free.liftF(InvokeTicketService(input)).map(Some(_))
    } else {
      Free.pure(None)
    }
  }

  def getBonusTickets(input: Int): Free[External, Option[Tickets]] = {
    if (input > 10) {
      Free.liftF(InvokeTicketService(1)).map(Some(_))
    } else {
      Free.pure(None)
    }
  }

val input: Int = 9

  def formatResponse(purchased: Option[Tickets], bonus: Option[Tickets]): String =
    s"Purchased tickets: ${purchased.map(_.count).getOrElse(0)}; bonus: ${bonus.map(_.count).getOrElse(0)}"

  var freeToNeededType: Free[External, String] = for {
    purchase <- purchaseTickets(input)
    bonus <- getBonusTickets(purchase.map(_.count).getOrElse(0))
  } yield formatResponse(purchase, bonus)


  val externalServiceInvoker: ~>[External, Future] = new (External ~> Future) {
    override def apply[A](e: External[A]): Future[A] = e match {
      case InvokeTicketService(count) => Future(Tickets(count))
    }
  }

  val result: Future[String] = freeToNeededType.foldMap(externalServiceInvoker)

  Await.result(result, 10.seconds)
  println(result)
}


