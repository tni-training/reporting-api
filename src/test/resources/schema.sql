CREATE TABLE spark_job (
  id int(11) NOT NULL AUTO_INCREMENT,
   action varchar(100) DEFAULT NULL,
  submission_id varchar(50) DEFAULT NULL,
  message text,
  jar_params text,
  server_spark_version varchar(50) DEFAULT NULL,
  is_accepted tinyint(1) DEFAULT NULL,
  status varchar(50) DEFAULT NULL,
  is_completed tinyint(1) DEFAULT NULL,
  created_at timestamp NULL DEFAULT NULL,
  modified_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
)
