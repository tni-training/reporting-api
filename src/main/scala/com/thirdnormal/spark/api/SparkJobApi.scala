package com.thirdnormal.spark.api

import akka.http.scaladsl.server.Directives.{_symbol2NR, as, complete, concat, delete, entity, get, onSuccess, parameters, path, post, put}
import akka.http.scaladsl.server.Route
import com.thirdnormal.spark.json.JsonUtility
import com.thirdnormal.spark.repository.{SparkJob, SparkJobRepository}

import scala.concurrent.ExecutionContext

trait SparkJobApi extends JsonUtility {

  implicit val ec: ExecutionContext
  val sparkJobRepository: SparkJobRepository

  val routes: Route = concat(
    post {
      //  ADDING JOB
      path("addjob") {
        entity(as[String]) { newJobJson => {
          val newJob = parse(newJobJson).extract[SparkJob]
          onSuccess(sparkJobRepository.create(newJob)) { jobWithId =>
            complete(write(jobWithId))
          }
        }
        }
      }
    },

    //  GETTING ALL JOBS
    get {
      path("alljobs") {
        onSuccess(sparkJobRepository.getAll()) { response =>
          complete(write(response))
        }
      }
    },

    //  GETTING JOB BY ID
    get {
      path("job") {
        parameters('id.as[Int]) { id =>
          onSuccess(sparkJobRepository.getById(id)) {
            case Some(job) => complete(write(job))
            case None => complete(s"Job with id $id is not present.")
          }
        }
      }
    },

    //DELETING SINGLE OR MULTIPLE JOB WITH ID

    delete {
      path("removejob") {
        parameters('ids.as[String]) { ids =>
          val removeJobId = ids.split(',').map(_.toInt).toList
          onSuccess(sparkJobRepository.deleteMultipleJob(removeJobId)) { isDeleted =>
            val response = if (isDeleted == 0) {
              "Any of the Job ids are not present."
            } else {
              "Job ids which are present is Deleted"
            }
            complete(response)
          }
        }
      }
    },

    //  Updating Job
    put {
      path("updatejob") {
        entity(as[String]) {
          jobJson => {

            val job = parse(jobJson).extract[SparkJob]
            if (job.id.isEmpty) {
              complete("Opps! it seems you haven't pass the job id.")
            } else {
              onSuccess(sparkJobRepository.update(job)) { isUpdated =>
                val response = if (isUpdated == 1) {
                  "Updated"
                } else {
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
