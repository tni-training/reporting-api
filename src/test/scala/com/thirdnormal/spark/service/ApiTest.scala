package com.thirdnormal.spark.service

import akka.http.scaladsl.testkit.ScalatestRouteTest

import com.thirdnormal.spark.Server.routes
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.thirdnormal.spark.json.JsonUtility
import com.thirdnormal.spark.{SparkJob, SparkJobApi}

class ApiTest extends AnyWordSpec with Matchers with ScalatestRouteTest with JsonUtility{
  "This Spark Job" should {



    "Add new job" in {
//      Post("/addjob", write(SparkJob("Job56", "156", "Nineteen", "Word", "2.12", 2, "process", 2))) ~> routes ~> check {
//        //        responseAs[String] shouldEqual "SparkJob(\"Job19\", \"119\", \"Nineteen\", \"Word\", \"2.12\", 2, \"process\", 2)"
//      }
    }

    "get all job" in {
//      Get("/alljobs") ~> routes ~> check{
        //It will always give the time at which it is created
        //        responseAs[String] shouldEqual "[{\"action\":\"Job19\",\"submissionId\":\"119\",\"message\":\"Nineteen\",\"jarParams\":\"Word\",\"serverSparkVersion\":\"2.12\",\"isAccepted\":2,\"status\":\"process\",\"isCompleted\":2,\"createdAt\":\"2022-08-05T12:32:59Z\",\"modifiedAt\":\"2022-08-05T12:32:59Z\"}]"
//      }
    }

    "get job by id" in {
      Get("/job?id=18") ~> routes ~> check{
        //        responseAs[String] shouldEqual "A job will return with"
      }
    }

    "Update Job" in {
      Put("/updatejob", write(SparkJob("Job35", "135", "Nineteen", "Word", "2.12", 2, "process", 2, id=Some(33)))) ~> routes ~> check {
        responseAs[String] shouldEqual "Updated"
      }

    }


//    "Delete job by id" in {
//      Delete("/removejob?id=18") ~> routes ~> check{
//        responseAs[String] shouldEqual "Job is deleted"
//      }
//    }
//  }
  }
}
