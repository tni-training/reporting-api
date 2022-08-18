package com.thirdnormal.spark

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.thirdnormal.spark.api.SparkJobApi
import com.thirdnormal.spark.repository.SparkJobRepository

import scala.concurrent.ExecutionContext

object Server extends App with SparkJobApi with CorsSupport{

  val sparkJobRepository: SparkJobRepository = SparkJobRepository
  implicit val system: ActorSystem = ActorSystem("RestServiceApp")

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val ec: ExecutionContext = system.dispatcher


  val port = 8081

  Http().newServerAt("localhost", port).bind(corsHandler(routes))
  println(s"Server is running on port $port")

}
