package com.thirdnormal.spark.service

import akka.actor.Status.Success
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.thirdnormal.spark.Server.routes
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.thirdnormal.spark.json.JsonUtility
import com.thirdnormal.spark.{SparkJob, SparkJobApi, SparkJobRepository}
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoSugar

import java.util.Date
import scala.concurrent.{ExecutionContext, Future}


class ApiTest extends AnyWordSpec with Matchers with ScalatestRouteTest with MockitoSugar with JsonUtility {
  val mockedSparkJobRepository = mock[SparkJobRepository]

  object TestObject extends SparkJobApi{
  implicit val ec: ExecutionContext = ExecutionContext.global
  val sparkJobRepository= mockedSparkJobRepository
}

  "This Spark Job" should {
    "Delete User" in {
      when(mockedSparkJobRepository.deleteMultipleJob(List(1,2))) thenReturn Future(1)
      Delete("/removejob?ids=1,2") ~> Route.seal(TestObject.routes) ~> check{
        responseAs[String] shouldEqual "Job ids which are present is Deleted"
      }
    }

    "Get user" in {
      when(mockedSparkJobRepository.getById(any[Int])) thenReturn Future(None)
      Get("/job?id=2") ~> Route.seal(TestObject.routes) ~> check {
        responseAs[String] shouldEqual "Job with id 2 is not present."
      }
     val job = SparkJob("Job56", "156", "Nineteen", "Word", "2.12", 2, "process", 2, id=Some(10))
      when(mockedSparkJobRepository.getById(10)) thenReturn Future(Some(any[SparkJob]))
      Get("/job?id=10") ~> Route.seal(TestObject.routes) ~> check{
        responseAs[String] shouldEqual "null"
      }
    }

    // Get all Jobs
    "Get all Jobs" in {
      when(mockedSparkJobRepository.getAll()).thenReturn(Future(List()))
      Get("/alljobs") ~> Route.seal(TestObject.routes) ~> check{
        responseAs[String] shouldEqual "[]"
      }
    }

    // ADDING JOB

    "add job" in {
      val job = SparkJob("Job56", "156", "Nineteen", "Word", "2.12", 2, "process", 2)
      when(mockedSparkJobRepository.create(any[SparkJob])).thenReturn(Future(any[SparkJob]))
      Post("/addjob", write(job)) ~> Route.seal(TestObject.routes) ~> check {
        responseAs[String] shouldEqual "null"
      }
    }


        "Update user " in {
          //IF WE DON'T PASS THE JOB ID
          val job1 = SparkJob("Job56", "156", "Nineteen", "Word", "2.12", 2, "process", 2)
          when(mockedSparkJobRepository.update(any[SparkJob])) thenReturn Future(1)
          Put("/updatejob", write(job1)) ~> Route.seal(TestObject.routes) ~> check {
            responseAs[String] shouldEqual "Opps! it seems you haven't pass the job id."
          }

          //WHEN WE PASS THE JOB ID
          val job2 = SparkJob("Job56", "156", "Nineteen", "Word", "2.12", 2, "process", 2, id=Some(1))
          when(mockedSparkJobRepository.update(any[SparkJob])) thenReturn Future(1)
          Put("/updatejob", write(job2)) ~> Route.seal(TestObject.routes) ~> check {
            responseAs[String] shouldEqual "Updated"
          }

        }
  }
}

