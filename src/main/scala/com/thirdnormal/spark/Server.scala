package com.thirdnormal.spark

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

object Server extends App with SparkJobApi with CorsSupport{

  implicit val system: ActorSystem = ActorSystem("RestServiceApp")

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val ec: ExecutionContext = system.dispatcher


  val port = 8081

  Http().newServerAt("localhost", port).bind(corsHandler(routes))
  println(s"Server is running on port $port")

}
