package com.thirdnormal.spark.db

trait MySQLDB extends DBComponent {
  val profile = slick.jdbc.MySQLProfile
  import profile.api._

  val db: Database = MySQLDB.connectionPool
}

object MySQLDB {
  import slick.jdbc.MySQLProfile.api._
  private val connectionPool = Database.forConfig("dbconf")
}
