package com.ojha.library

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Sink
import HttpMethods.GET

import scala.concurrent.Future

/**
 * Created by alexandra on 04/05/15.
 */
object Main extends App {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()


  val serverSource = Http(system).bind(interface = "localhost", port = 8080)

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/html`,
          "<html><body>Hello world!</body></html>"))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
      HttpResponse(entity = "PONG!")

    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) =>
      sys.error("BOOM!")

    case _: HttpRequest =>
      HttpResponse(404, entity = "Unknown resource!")
  }

  val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
    println("Accepted new connection from " + connection.remoteAddress)
    connection handleWithSyncHandler requestHandler
  }).run()

}
