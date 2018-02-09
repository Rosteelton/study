package ru.steelcrow.tests

import java.net.InetSocketAddress
import java.time.Instant

import com.datastax.driver.core.Cluster
import com.dimafeng.testcontainers.{ForAllTestContainer, GenericContainer}
import com.typesafe.config.{Config, ConfigFactory}
import differentTasks.quill.{CassandraSimRepo, SimStateView}
import io.getquill.{CassandraAsyncContext, SnakeCase}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Promise
import scala.concurrent.duration._
import scala.util.Try

class QuilSpec
    extends FlatSpec
    with Matchers
    with ForAllTestContainer
    with ScalaFutures { spec =>

  val container: GenericContainer = new GenericContainer(
    "cassandra:latest",
    Seq(9042)
  )

  private def createKeyspaceQuery = s"""
      CREATE KEYSPACE IF NOT EXISTS $keyspace
      WITH REPLICATION = { 'class': 'SimpleStrategy','replication_factor': 1 }
    """

  override def afterStart(): Unit = {
    println(s"""
         |
        |${container.container.getContainerIpAddress}
         |${container.container.getMappedPort(9042)}
         |
        |
        |
      """.stripMargin)

    val cluster = Cluster
      .builder()
      .addContactPointsWithPorts(
        new InetSocketAddress(container.container.getContainerIpAddress,
                              container.container.getMappedPort(9042)))
      .build()

    val session = cluster.connect()
    session.execute(createKeyspaceQuery)

    cassandraContextP.complete(
      Try(new CassandraAsyncContext(SnakeCase, cluster, keyspace, 1000)))
  }

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(20.seconds, 150.millis)

  def config: Config = ConfigFactory.parseString(s"""
       |keyspace=test
       |preparedStatementCacheSize=1000
       |session.contactPoint=${container.container.getContainerIpAddress}
       |session.withPort=${container.container.getMappedPort(9042)}
       |session.queryOptions.consistencyLevel=LOCAL_QUORUM
       |session.withoutMetrics=true
       |session.withoutJMXReporting=false
       |session.credentials.0=root
       |session.credentials.1=pass
       |session.maxSchemaAgreementWaitSeconds=1
       |session.addressTranslator=com.datastax.driver.core.policies.IdentityTranslator
     """.stripMargin)

  val keyspace = "test"

  val cassandraContextP: Promise[CassandraAsyncContext[SnakeCase]] =
    Promise[CassandraAsyncContext[SnakeCase]]

  val cassandraContextT: Task[CassandraAsyncContext[SnakeCase]] =
    Task.deferFuture(cassandraContextP.future)

  val queries = CassandraSimRepo.Queries(keyspace)
  val repoT = cassandraContextT.map(ctx => new CassandraSimRepo(ctx))

  val now: Instant = Instant.now()

  val simStateView = SimStateView(
    "qwerty4",
    "qwerty",
    Some("qwerty"),
    now,
    1
  )

  "QuilSpec" should "work" in {
    val result = for {
      ctx <- cassandraContextT
      _ <- Task.deferFuture(ctx.executeAction(createKeyspaceQuery))
      _ <- CassandraSimRepo.init(queries, ctx)
      repo <- repoT
      _ <- repo.saveSimState(simStateView)
      r <- repo.getSimState(simStateView.simId)
    } yield r

    result.runAsync.futureValue shouldBe Some(simStateView)
  }
}
