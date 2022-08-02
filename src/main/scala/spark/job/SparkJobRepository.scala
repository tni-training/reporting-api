package spark.job

import slick.lifted.ProvenShape
import spark.job
import scala.concurrent.Future
import java.util.Date
import DateMapper._


object SparkJobRepository {
  import slick.jdbc.MySQLProfile.api._

  private val db: Database= Database.forConfig("dbconf")
  private lazy val sparkJobTableQuery = TableQuery[SparkJobTable]

  private def sparkJobTableAutoInc = sparkJobTableQuery

  def create(sparkJob: SparkJob) = db.run{
    sparkJobTableAutoInc += sparkJob
  }

  def getAll(): Future[List[SparkJob]] = db.run{
    sparkJobTableQuery.to[List].result
  }

  def getById(id: Int): Future[Option[SparkJob]] = db.run{
    sparkJobTableQuery.filter(_.id === id).result.headOption
  }

  // Updating Spark Job Details
  def update(sparkJob: SparkJob): Future[Int] = db.run {
    //Just not updating created_at
    sparkJobTableQuery.filter(_.id === sparkJob.id.get).map(elem => (elem.action,elem.submissionId, elem.message,elem.jarParams, elem.serverSparkVersion, elem.isAccepted, elem.status, elem.isCompleted, elem.modifiedAt)).update(sparkJob.action,sparkJob.submissionId,sparkJob.message,sparkJob.jarParams,sparkJob.serverSparkVersion, sparkJob.isAccepted, sparkJob.status, sparkJob.isCompleted,sparkJob.modifiedAt)
  }
  def deleteJob(id: Int):Future[Int] =db.run{
    sparkJobTableQuery.filter(_.id === id).delete
  }

  class SparkJobTable(tag: Tag) extends Table[SparkJob](tag, "spark_job"){
      def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
      def action: Rep[String] = column[String]("action")
      def submissionId: Rep[String] = column[String]("submission_id")
      def message: Rep[String] = column[String]("message")
      def jarParams: Rep[String] = column[String]("jar_params")
      def serverSparkVersion: Rep[String] = column[String]("server_spark_version")
      def isAccepted: Rep[Int] = column[Int]("is_accepted")
      def status: Rep[String] = column[String]("status")
      def isCompleted: Rep[Int] = column[Int]("is_completed")
      def createdAt:Rep[Date] = column[Date]("created_at")
      def modifiedAt: Rep[Date] = column[Date]("modified_at")

      def * : ProvenShape[SparkJob] = (action, submissionId, message, jarParams, serverSparkVersion, isAccepted, status, isCompleted, createdAt, modifiedAt,id.?) <> (job.SparkJob.tupled, job.SparkJob.unapply)
  }
}
case class SparkJob(action: String, submissionId: String, message: String, jarParams: String, serverSparkVersion: String, isAccepted: Int, status: String, isCompleted: Int, createdAt: Date = new Date(), modifiedAt: Date = new Date(), id: Option[Int]= None )