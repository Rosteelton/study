package differentTasks.doobie

import akka.Done
import doobie.free.connection.ConnectionIO
import doobie.imports._
import doobie.util.transactor.Transactor
object Doobie extends App {

//  implicit class SqlIdeFixer(sc: StringContext) extends doobie.syntax.string.SqlInterpolator(sc) {
//    def sql2[A: Param](args: A): doobie.syntax.string.Builder[A] = this.sql.applyProduct(args)
//  }




  //
//
//  type Rec = Record.`'id -> String, 'text -> String`.T
//
//  case class Rec2(id: String, text: String)
//
//  val program1: ConnectionIO[Int] = 42.pure[ConnectionIO]
//
//  val program3 = sql"SELECT * from tmp".query[Rec2].list
//
//
//  val task = program3.transact(xa)
//
//  val result = task.unsafePerformIO
//
//  println(result)

  val xa: Transactor[IOLite] = DriverManagerTransactor[IOLite]("org.postgresql.Driver", "jdbc:postgresql://127.0.0.1:5432/qwerty", "postgres", "postgres")
val tableName = "aaaaaa"

  val createQuery = sql"CREATE TABLE IF NOT EXISTS $tableName (consumer_id text,tag text,consumer_offset uuid,PRIMARY KEY (consumer_id, tag));".update.run



 // val createTypeQuery = sql"CREATE TYPE IF NOT EXISTS money AS (amount text, currency text)".update

  //def createType: ConnectionIO[Unit] = createTypeQuery.run.void

val checkTypeExisting: ConnectionIO[Boolean] = sql"select exists (select 1 from pg_type where typname = 'money')".query[Boolean].unique



  //println(checkTypeExisting.transact(xa).unsafePerformIO)

  //createType.transact(xa).unsafePerformIO

  val b = createQuery.transact(xa).map(_ => Done).unsafePerformIO

  println(b)







}
