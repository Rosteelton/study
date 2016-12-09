import java.time.format.DateTimeFormatter
import java.time.{LocalDate, Month}

val a = LocalDate.of(2016, Month.DECEMBER, 7).atTime(23, 59, 59, 66)

val formatter = DateTimeFormatter.ofPattern("yyyyMMddAAAAAAAA")

println(a.format(formatter))
