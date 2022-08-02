package spark.job

import org.slf4j.LoggerFactory
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object SparkJobApp extends App {

  val logger = LoggerFactory.getLogger(this.getClass)

  //FOR CREATING

//  val sparkJob19: SparkJob = SparkJob("Job19", "119", "Nineteen", "Word", "2.12", 2, "process", 2)
//  val res = SparkJobRepository.create(sparkJob19).waitTillCompletion
//  logger.info("Created -> "+res)

  //FOR ACCESSING ALL JOBS
//  val allJobs = SparkJobRepository.getAll().waitTillCompletion
//  logger.info("\nAll Jobs are: "+ allJobs)

  // FOR ACCESSING JOB BY ID
  val job= SparkJobRepository.getById(19).waitTillCompletion
  logger.info(s"\nJob with id 19 : " + job.get)

  // FOR DELETING
//  val  res = SparkJobRepository.deleteJob(13).waitTillCompletion
//  logger.info("\nInformation Deleted--- "+ res + "\n")

  //FOR UPDATING
//  val sparkJob15: SparkJob = SparkJob("Job15", "115", "Fifteeen", "Word", "2.12", 2, "process", 2, id=Some(15))
//  val upRes = SparkJobRepository.update(sparkJob15).waitTillCompletion
//  logger.info("Updated-> "+upRes)

  implicit class Waiting[T](val f: Future[T])  {
    def waitTillCompletion: T= {
      Await.result(f, 15.seconds)
    }
  }
}
