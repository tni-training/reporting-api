package com.thirdnormal.spark.repo

import org.scalatest.funsuite.AnyFunSuite
import com.thirdnormal.spark.{SparkJob, SparkJobRepository}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class SparkJobRepositoryTest extends AnyFunSuite with SparkJobRepository with ScalaFutures{
  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500,Millis))

  test("Add New Job"){
//    val res = create(SparkJob("Job59", "159", "Thirty Nine", "Word", "2.12", 2, "process", 2))
//    whenReady(res) { res =>
//      assert(res.id.get === 59)
//    }
  }

  test("Get job by id"){

    // If job is not present
    val isJob = getById(555)
    whenReady(isJob){res =>
      assert(res === None)
    }

    //When Job is Present
    val isJobAvail = getById(26)
    whenReady(isJobAvail){resp =>
      //        assert(resp === Some(SparkJob("Job","129","Twenty Nine","Word","2.12",2,"process",2,Thu Aug 04 12:44:49 IST 2022,"Thu Aug 04 12:44:49 IST 2022",Some(30))))
        assert(resp.get.action === "Job26" )
    }

  }

  test("Get all Jobs"){
    val allJobs = getAll()
    whenReady(allJobs){ resp =>
      assert(resp.length === 34)

    }
  }

  test("Delete Job By Id"){
    // IF JOB ID IS NOT PRESENT
    val isJobAvail = deleteJob(555)
    whenReady(isJobAvail){resp =>
      assert(resp === 0)
    }

    // IF JOB ID IS PRESENT
//    val isJob = deleteJob(22)
//    whenReady(isJob){resp =>
//      assert(resp === 1)
//    }
  }

  //TESTING UPDATES

  test("Update Job"){
    //IF WE DON'T PASS THE JOB ID
//    val isUpdated = update(SparkJob("Job59", "159", "Thirty Nine", "Word", "2.12", 2, "process", 2))
//    whenReady(isUpdated){resp =>
//      assert(resp === 0)
//    }

//     IF WE PASS THE JOB ID AND IT IS NOT PRESENT
    val isJobAvail = update(SparkJob("Job59", "159", "Thirty Nine", "Word", "2.12", 2, "process", 2, id=Some(155)))
    whenReady(isJobAvail){resp =>
      assert(resp === 0)
    }
  }

  //  IF WE PASS THE JOB ID and it is Present

  val isUpdated = update(SparkJob("Job25", "125", "Thirty Nine", "Word", "2.12", 2, "process", 2, id=Some(25)))
  whenReady(isUpdated){resp =>
    assert(resp === 1)
  }
}
