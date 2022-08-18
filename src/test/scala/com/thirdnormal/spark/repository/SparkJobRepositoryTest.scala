package com.thirdnormal.spark.repository

import com.thirdnormal.spark.connection.H2DBComponent
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.time.{Millis, Seconds, Span}

class SparkJobRepositoryTest extends AnyFunSuite with SparkJobRepository with H2DBComponent with ScalaFutures{
  implicit val defaultPatience = PatienceConfig(timeout= Span(5,Seconds), interval= Span(500, Millis))

  //  TESTING CREATING NEW JOB
  test("Add new job") {
    val res = create(SparkJob("Job6", "6", "Six", "Word", "2.12", 2, "process", 2))
    whenReady(res){result =>
      assert(result.id.get === 6 && result.action === "Job6")
    }
  }

  //  TESTING GET ALL JOBS
  test("Get all Job") {
    val res = getAll()
    whenReady(res){ response =>
      assert( response.length === 4 && response(0).message === "One")
    }
  }

  //  TESTING GET JOB BY ID
  test("Get Job By Id"){

    //  passing the job id which is not present
    val res = getById(55)
    whenReady(res){result=>
      assert(result === None)
    }

    //  passing the job id which is present
    val response = getById(1)
    whenReady(response){result =>
      assert(result.get.action === "Job1" && result.get.id.get === 1 )
    }
  }

  //  TESTING UPDATING JOB
  test("Updating a Job on basis of job id"){

    //  updating a job which is not present: Job is 55 is not present
    val res = update(SparkJob("Job55", "55", "Fifty Five", "Word", "2.12", 2, "process", 2, id=Some(55)))
    whenReady(res){result =>
      assert(result === 0)
    }

    //  updating a job which is present
    val response = update(SparkJob("Job2", "2", "Two", "Second Word", "2.12", 2, "process", 2, id=Some(2)))
    whenReady(response){result=>
      assert(result === 1)
    }
  }

  //  TESTING DELETING JOB
  test("Deleting Jobs on basis of ids"){

    //  deleting job which is not present
    val res = deleteMultipleJob(List(10,11))
    whenReady(res){result =>
      assert(result ===0)
    }

    // deleting single job which is present
    val resp = deleteMultipleJob(List(1))
    whenReady(resp){result =>
      assert(result === 1)
    }

    // deleting multiple job which is present
    val response = deleteMultipleJob(List(4,5))
    whenReady(response){result =>
      assert(result === 2)
    }
  }
}
