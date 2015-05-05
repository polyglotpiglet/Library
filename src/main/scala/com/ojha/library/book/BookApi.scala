package com.ojha.library.book

import java.io.IOException

import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.scaladsl.{Sink, Source, Flow}
import com.ojha.library.Main._

import scala.concurrent.Future

/**
 * Created by alexandra on 05/05/15.
 */
trait BookApi {

  private val baseUrl = config.getString("library.isbndb.url")
  private val key = config.getString("library.isbndb.key")
//  val tail = "/book/084930315X"

  lazy val bookApi: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(baseUrl + key, 80)

  private def executeRequest(request: HttpRequest): Future[HttpResponse] = {
    Source.single(request).via(bookApi).runWith(Sink.head)
  }

  def getBookInfo(bookId: String): Future[Either[String, String]] = {
    executeRequest(RequestBuilding.Get(s"/book/$bookId")).flatMap { response =>
      response.status match {
        case OK         => Unmarshal(response.entity).to[String].map(Right(_))
        case BadRequest => Future.successful(Left(s"$bookId: incorrect IP format"))
        case _          => Unmarshal(response.entity).to[String].flatMap { entity =>
          val error = s"Book service returned response code: ${response.status} and entity: $entity"
          Future.failed(new IOException(error))
        }
      }
    }
  }

}
