//package spark.job
//
//import org.slf4j.LoggerFactory
//import scala.concurrent.duration.DurationInt
//import scala.concurrent.{Await, Future}
//
//object SparkJobApp extends App {
//
//  val logger = LoggerFactory.getLogger(this.getClass)
//
//  //FOR CREATING
//
////  val sparkJob20: SparkJob = SparkJob("Job20", "120", "Twenty", "Word", "2.12", 2, "process", 2)
////  val res = SparkJobRepository.create(sparkJob20).waitTillCompletion
////  logger.info("Created -> "+res)
//
////  FOR ACCESSING ALL JOBS
//  val allJobs = SparkJobRepository.getAll().waitTillCompletion
//  logger.info("\nAll Jobs are: "+ allJobs)
//
//  // FOR ACCESSING JOB BY ID
////  val job= SparkJobRepository.getById(19).waitTillCompletion
////  logger.info(s"\nJob with id 19 : " + job.get)
//
//  // FOR DELETING
////  val  res = SparkJobRepository.deleteJob(14).waitTillCompletion
////  logger.info("\nInformation Deleted--- "+ res + "\n")
//
//  //FOR UPDATING
////  val sparkJob15: SparkJob = SparkJob("Job15", "115", "Fifteeen", "Word", "2.12", 2, "process", 2, id=Some(15))
////  val upRes = SparkJobRepository.update(sparkJob15).waitTillCompletion
////  logger.info("Updated-> "+upRes)
//
//  implicit class Waiting[T](val f: Future[T])  {
//    def waitTillCompletion: T= {
//      Await.result(f, 15.seconds)
//    }
//  }
//}

package spark.job

import org.slf4j.LoggerFactory

import scala.concurrent.duration.{DurationInt, SECONDS}
import scala.concurrent.{Await, Future}
import scala.util.Success

//AKKA
import akka.http.scaladsl.Http

import akka.actor.ActorSystem

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
  import spark.job.json.JsonUtility._
object SparkJobApp{
  implicit val system: ActorSystem = ActorSystem("RestServiceApp")

  implicit val materializer: ActorMaterializer = ActorMaterializer()


  def main(args: Array[String]): Unit ={
    // ADDING JOBS
    val routes: Route = concat(
      post{
        path("addjob"){
         entity(as[String]){
            newJobJson => {
              complete {
                val newJob = parse(newJobJson).extract[SparkJob]
                val jobWithId = SparkJobRepository.create(newJob).waitTillCompletion
                write(jobWithId)
              }
           }
          }
        }
      },
      //GETTING ALL JOBS
      get{
        path("alljobs"){
        complete{
          val alljobs = SparkJobRepository.getAll().waitTillCompletion
          write(alljobs)
        }
        }
      },
      get{
        path("job"){
          parameters('id.as[Int]){ id =>
            complete{
              val job = SparkJobRepository.getById(id).waitTillCompletion

              if(job == None){
                s"Job with id $id is not present."
              }else {
                write(job.get)
              }
            }

          }
        }
      },

      // DELETING JOB
      delete{
        path("removejob"){
          parameters('id.as[Int]){ id =>
            complete{
              val isDeleted = SparkJobRepository.deleteJob(id).waitTillCompletion
              if(isDeleted == 1){
                "Job is deleted"
              }else {
                s"Job with id $id is not present."
              }
            }

          }
        }
      },
      put{
        path("updatejob"){
          entity(as[String]) {
            jobJson =>
              complete{
                val job = parse(jobJson).extract[SparkJob]
                val isJob = SparkJobRepository.getById(job.id.get).waitTillCompletion
                if(isJob == None){
                  s"Job with id ${job.id.get} is not present."
                }else{
                  SparkJobRepository.update(job).waitTillCompletion
                  s"Updated ${write(isJob)}"
                }
                }
          }
        }
      }


    )

    val port=8080
    Http().newServerAt("localhost",port).bind(routes)
    println(s"Server is running on port $port")

  }


  implicit class Waiting[T](val f: Future[T])  {
    def waitTillCompletion: T= {
      Await.result(f, 15.seconds)
    }
  }
}


