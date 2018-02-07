package differentTasks.quill

import java.time.Instant

import com.typesafe.config.{Config, ConfigFactory}
import io.getquill.{CassandraAsyncContext, SnakeCase}
import monix.execution.Scheduler

import scala.util.{Failure, Success}

object Quill extends App {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  val config: Config = ConfigFactory.load()
  val cassandraContext = new CassandraAsyncContext[SnakeCase](SnakeCase, "ctx")

  val keyspace = "test"
  val queries = CassandraSimRepo.Queries(keyspace)
  val repo = CassandraSimRepo(cassandraContext)

  val now: Instant = Instant.now()
  val simStateView = SimStateView("qwerty", "qwerty", Some("qwerty"), now, 1)

  val task = for {
   _ <- CassandraSimRepo.init(queries,cassandraContext)
   _ <- repo.saveSimState(simStateView)
   r <- repo.getSimState("qwerty")
  } yield r

  task.runAsync(Scheduler(ec))
    .onComplete {
      case Success(a) =>
        println(a)
      case Failure(a) =>
        println(a)
    }
}
