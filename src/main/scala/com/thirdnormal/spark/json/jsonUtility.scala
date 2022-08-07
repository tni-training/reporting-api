package com.thirdnormal.spark.json

import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}

trait JsonUtility {

  implicit val formats = DefaultFormats

  def write[T <: AnyRef](value: T): String = Serialization.write(value)

  def parse(value: String): JValue = JsonMethods.parse(value)


}
