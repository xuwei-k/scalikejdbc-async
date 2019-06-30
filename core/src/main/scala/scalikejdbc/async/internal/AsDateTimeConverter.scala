package scalikejdbc.async.internal

import org.joda.time._
import scalikejdbc.JavaUtilDateConverter

import scala.concurrent.duration.FiniteDuration

private[scalikejdbc] object AsDateTimeConverter {

  // TODO add java.time.* classes ?
  def unapply(any: Any): Option[JavaUtilDateConverter] = PartialFunction.condOpt(any) {
    case d: java.util.Date =>
      new JavaUtilDateConverter(d)
    case ri: ReadableInstant =>
      new JavaUtilDateConverter(new java.util.Date(ri.getMillis))
    case ldt: LocalDateTime =>
      new JavaUtilDateConverter(new java.util.Date(ldt.toDateTime.getMillis))
    case ld: LocalDate =>
      new JavaUtilDateConverter(new java.util.Date(ld.toDate.getTime))
    case lt: LocalTime =>
      new JavaUtilDateConverter(new java.util.Date(lt.toDateTimeToday.getMillis))
    case fd: FiniteDuration =>
      new JavaUtilDateConverter(new java.util.Date(fd.toMillis))
  }
}
