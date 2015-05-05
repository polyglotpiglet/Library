package com.ojha.library

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorFlowMaterializer
import com.ojha.library.book.BookApi
import com.ojha.library.config.Configurable

/**
 * Created by alexandra on 04/05/15.
 */
object Main extends App with BookApi with Configurable {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  val host = config.getString("library.server.host")
  val port = config.getInt("library.server.port")

  val route: Route =
    pathPrefix("library") {
      path("book" / Rest) { id =>
        get {
          complete {
            getBookInfo(id).map[ToResponseMarshallable] {
              case Right(x) => x
              case Left(errorMessage) => BadRequest -> errorMessage
            }
//            "Received GET request for order " + id
          }
        } ~
          put {
            complete {
              "Received PUT request for order " + id
            }
          }
      }
    }


  Http().bindAndHandle(route, host, port)

}

