package differentTasks.comon

import java.time.{Instant, LocalDateTime, YearMonth, ZoneOffset}

import com.datastax.driver.core.utils.UUIDs

import scala.math.BigDecimal.RoundingMode

object IsoInstantToCassandraUUID extends App {
  val instant = Instant.parse("2017-03-01T00:00:00.00Z")
  val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
  val uuid = UUIDs.startOf(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli)
  println(uuid)

  val a = (BigDecimal(364) / 100).setScale(0, RoundingMode.UP).toInt
  println(a)

  val b = YearMonth.now()

  println(b.toString)
}
