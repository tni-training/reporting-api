package com.thirdnormal.spark

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.thirdnormal.spark.json.JsonUtility._

import scala.concurrent.ExecutionContext

trait SparkJobApi {

  implicit val ec: ExecutionContext

  val routes: Route = concat(
    post {
      path("addjob") {
        entity(as[String]) { newJobJson => {
          val newJob = parse(newJobJson).extract[SparkJob]
          onSuccess(SparkJobRepository.create(newJob)) { jobWithId =>
            complete(write(jobWithId))
          }
        }
        }
      }
    },
    //GETTING ALL JOBS
    get {
      path("alljobs") {
        onSuccess(SparkJobRepository.getAll()) { response =>
          complete(write(response))
        }
      }
    },
    get {
      path("job") {
        parameters('id.as[Int]) { id =>
          onSuccess(SparkJobRepository.getById(id)) {
            case Some(job) => complete(write(job))
            case None => complete(s"Job with id $id is not present.")
          }
        }
      }
    },

    // DELETING JOB
    delete {
      path("removejob") {
        parameters('id.as[Int]) { id =>
          onSuccess(SparkJobRepository.deleteJob(id)) { isDeleted =>
            val response = if (isDeleted == 1) {
              "Job is deleted"
            } else {
              s"Job with id $id is not present."
            }
            complete(response)
          }
        }
      }
    },

    // Updating Job
    put{
      path("updatejob"){
        entity(as[String]){
          jobJson =>{
            val job = parse(jobJson).extract[SparkJob]
            if(job.id.isEmpty){
              complete("Opps! it seems you haven't pass the job id.")
            }else{
              onSuccess(SparkJobRepository.update(job)){ isUpdated =>
                val response = if(isUpdated == 1) {
                  "Updated"
                }else {
                  s"Job with id ${job.id.get} is not present"
                }
                complete(response)
              }
            }

          }

        }
      }
    }

  )


}


