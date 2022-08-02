package spark.job

  object DateMapper {
    import slick.jdbc.MySQLProfile.api._

    implicit val utilDate2SqlTimestampMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp](
      { utilDate => new java.sql.Timestamp(utilDate.getTime()) },
      { sqlTimestamp => new java.util.Date(sqlTimestamp.getTime()) })

  }
