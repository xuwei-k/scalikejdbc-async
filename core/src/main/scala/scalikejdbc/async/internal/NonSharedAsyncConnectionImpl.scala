package scalikejdbc.async.internal

import com.github.jasync.sql.db.{ Connection => MauricioConnection }
import com.github.jasync.sql.db.pool.{ ConnectionPool => MauricioConnectionPool }
import scalikejdbc.async.NonSharedAsyncConnection
import scala.concurrent._
import scalikejdbc.async.ShortenedNames._

/**
 * Non-shared Asynchronous Connection
 * @param underlying jasync connection
 * @param pool jasync connection
 */
abstract class NonSharedAsyncConnectionImpl(
  val underlying: MauricioConnection,
  val pool: Option[MauricioConnectionPool[MauricioConnection]] = None)
  extends AsyncConnectionCommonImpl
  with NonSharedAsyncConnection {

  override def toNonSharedConnection()(implicit cxt: EC = ECGlobal): Future[NonSharedAsyncConnection] =
    Future.successful(this)

  override def release(): Unit = pool.map(_.giveBack(this.underlying))

}
