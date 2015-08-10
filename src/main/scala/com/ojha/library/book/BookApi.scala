package com.ojha.library.book

import java.io.IOException

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source, Flow}
import com.ojha.library.Main._
import com.ojha.library.dataModel.BookInfo
import play.api.libs.json.{Json, JsValue}

import scala.concurrent.Future

/**
 * Created by alexandra on 05/05/15.
 */

object BookApi {
  case class BookInfo2(title: String, author: String)
}

trait BookApi {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  private val baseUrl = config.getString("library.isbndb.url")
  private val key = config.getString("library.isbndb.key")

  val bookApi: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(baseUrl, 80)

  private def executeRequest(request: HttpRequest): Future[HttpResponse] = {
    Source.single(request).via(bookApi).runWith(Sink.head)
  }

  def getBookInfo(bookId: String): Future[BookInfo] = {

    executeRequest(RequestBuilding.Get(s"/api/v2/json/$key/book/$bookId")).flatMap { response =>
      response.status match {
        case OK         => Unmarshal(response.entity).to[String].map(jsonToBookInfo)
        case _          => throw new IOException(s"Book service returned response code: ${response.status}")
      }
    }
  }

  private def jsonToBookInfo(jsonString: String): BookInfo = {
    val json = Json.parse(jsonString)
    val author = ((json \ "data" \\ "author_data").head \\ "name").head.toString()
    val title = (json \ "data" \\ "title").head.toString()
    BookInfo(title, author)
  }

}
