package com.thirdnormal.spark

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.thirdnormal.spark.json.JsonUtility

import scala.concurrent.ExecutionContext

trait SparkJobApi extends SparkJobRepository with JsonUtility{

  implicit val ec: ExecutionContext

  val routes: Route = concat(
    post {
      //  ADDING JOB
      path("addjob") {
        entity(as[String]) { newJobJson => {
          val newJob = parse(newJobJson).extract[SparkJob]
          onSuccess(create(newJob)) { jobWithId =>
            complete(write(jobWithId))
          }
        }
        }
      }
    },

    //  GETTING ALL JOBS
    get {
      path("alljobs") {
        onSuccess(getAll()) { response =>
          complete(write(response))
        }
      }
    },

    //  GETTING JOB BY ID
    get {
      path("job") {
        parameters('id.as[Int]) { id =>
          onSuccess(getById(id)) {
            case Some(job) => complete(write(job))
            case None => complete(s"Job with id $id is not present.")
//            case None => complete(HttpResponse(StatusCodes.NotFound, entity= s"Job with id $id is not present."))
          }
        }
      }
    },

    //  DELETING JOB
    delete {
      path("removejob") {
        parameters('id.as[Int]) { id =>
          onSuccess(deleteJob(id)) { isDeleted =>
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
    //DELETING MULTIPLE JOB
    put{
      path("removejobs"){
        entity(as[String]){
          jobjson => {
            val job = parse(jobjson).extract[RemoveJobId].job_id
            onSuccess(deleteMultipleJob(job)){isDeleted =>
              val response = if (isDeleted == 0) {
                s"All Job ids are not present."
              } else {
                "All the jobs id which is present is Deleted"
              }
              complete(response)
            }
          }
        }
      }
    },

    //  Updating Job
    put{
      path("updatejob"){
        entity(as[String]){
          jobJson =>{

            val job = parse(jobJson).extract[SparkJob]
            if(job.id.isEmpty){
              complete("Opps! it seems you haven't pass the job id.")
            }else{
              onSuccess(update(job)){ isUpdated =>
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
case class RemoveJobId(job_id: List[Int])