/*
 * Copyright 2021 Azavea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carto.analyticstoolbox

import com.azavea.hiveless.serializers.{HConverter, HSerializer, UnaryDeserializer}
import cats.Id
import org.locationtech.jts.geom.Geometry
import org.apache.spark.sql.types.{BinaryType, DataType}
import org.locationtech.geomesa.spark.jts.util.WKBUtils

package object core extends Serializable {
  implicit def geometryConverter[T <: Geometry]: HConverter[T] = new HConverter[T] {
    def convert(argument: Any): T = WKBUtils.read(argument.asInstanceOf[Array[Byte]]).asInstanceOf[T]
  }

  implicit def geometryUnaryDeserializer[T <: Geometry: HConverter]: UnaryDeserializer[Id, T] =
    (arguments, inspectors) => HConverter[T].convert(UnaryDeserializer[Id, Array[Byte]].deserialize(arguments, inspectors))

  implicit def geometrySerializer[T <: Geometry]: HSerializer[T] = new HSerializer[T] {
    def dataType: DataType                 = BinaryType
    def serialize: Geometry => Array[Byte] = WKBUtils.write
  }
}