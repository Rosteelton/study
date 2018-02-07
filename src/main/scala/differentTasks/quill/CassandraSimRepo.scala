package differentTasks.quill

import io.getquill.{CassandraAsyncContext, SnakeCase}
import monix.eval.Task

import scala.concurrent.ExecutionContext

class CassandraSimRepo(context: CassandraAsyncContext[SnakeCase])(implicit ec: ExecutionContext) {

  import context._

  private val table = quote(querySchema[SimStateView]("sim_states"))
  private val matView = quote(querySchema[SimStateView]("sim_states_by_device_id"))

  def getSimState(simId: String): Task[Option[SimStateView]] =
    Task.deferFuture{
      performIO(context.runIO(table.withFilter(_.simId == lift(simId))).map(_.headOption))
    }

  def getSimStatesByDeviceId(deviceId: String): Task[List[SimStateView]] =
    Task.deferFuture {
      performIO(context.runIO(matView.withFilter(_.deviceId == lift(deviceId))))
    }

  def saveSimState(state: SimStateView): Task[Unit] =
    Task.deferFuture {
      performIO(context.runIO(table.insert(lift(state))))
    }
}

object CassandraSimRepo {
  final case class Queries(keyspace: String,
                           tableName: String = "sim_states",
                           matViewName: String = "sim_states_by_device_id") {
    def createTable: String =
      s"""
        CREATE TABLE IF NOT EXISTS $keyspace.$tableName (
          sim_id text,
          device_id text,
          user_id text,
          created_at timestamp,
          version bigint,
          PRIMARY KEY ((sim_id))
        )
       """

    def createMatView: String =
      s"""
     CREATE MATERIALIZED VIEW IF NOT EXISTS $keyspace.$matViewName AS
     SELECT sim_id, device_id, user_id, created_at, version
     FROM $keyspace.$tableName
     WHERE sim_id IS NOT NULL AND device_id IS NOT NULL
     PRIMARY KEY ((device_id), sim_id)
    """
  }

  def init(queries: Queries, context: CassandraAsyncContext[SnakeCase])(implicit ec: ExecutionContext): Task[Unit] =
    Task.deferFuture {
      for {
        _ <- context.executeAction(queries.createTable)
        _ <- context.executeAction(queries.createMatView)
      } yield ()
    }

  def apply(context: CassandraAsyncContext[SnakeCase])(implicit ec: ExecutionContext): CassandraSimRepo =
    new CassandraSimRepo(context)
}
