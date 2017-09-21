package differentTasks.comon

import java.time.{Instant, LocalDateTime, ZoneOffset}

import com.datastax.driver.core.utils.UUIDs

object IsoInstantToCassandraUUID extends App {

  val instant = Instant.parse("2017-03-01T00:00:00.00Z")
  val localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
  val uuid = UUIDs.startOf(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli)
  println(uuid)
}
