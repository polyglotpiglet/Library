package com.ojha.library

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.Segment
import akka.http.scaladsl.server.Route
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Sink
import HttpMethods.GET
import com.ojha.library.config.Configurable

import scala.concurrent.Future

/**
 * Created by alexandra on 04/05/15.
 */
object Main extends App with Configurable {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  val host = config.getString("library.server.host")
  val port = config.getInt("library.server.port")

  val serverSource = Http(system).bind(interface = host, port = port)

  val baseUrl = config.getString("library.isbndb.url")
  val key = config.getString("library.isbndb.key")
  val tail = "/book/084930315X"

  val url = baseUrl + key + tail


  val requestHandler: HttpRequest => HttpResponse = {
//    case HttpRequest(GET, Uri.Path("/library/book"), _, _, _) =>
    case HttpRequest(GET, Uri.Path("/library/book"), _, _ , _) =>
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/plain`, url))

    case _: HttpRequest =>
      HttpResponse(404, entity = "Resource is not found!")
  }

  val route: Route =
  pathPrefix("library"){
    path("book" / IntNumber) { id =>
      get {
        complete {
          "Received GET request for order " + id
        }
      } ~
        put {
          complete {
            "Received PUT request for order " + id
          }
        }
    }
  }



  val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
    println("Accepted new connection from " + connection.remoteAddress)
    connection handleWithSyncHandler requestHandler
  }).run()

}
