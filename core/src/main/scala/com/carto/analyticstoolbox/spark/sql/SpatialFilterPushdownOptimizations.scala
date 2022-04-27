/*
 * Copyright 2022 Azavea
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

package com.carto.analyticstoolbox.spark.sql

import com.carto.analyticstoolbox.spark.sql.rules.SpatialFilterPushdownRules
import org.apache.spark.sql.SparkSessionExtensions

class SpatialFilterPushdownOptimizations extends (SparkSessionExtensions => Unit) {
  import java.io._
  def writeFile(filename: String, s: String): Unit = {
    val file = new File(filename)
    val bw   = new BufferedWriter(new FileWriter(file))
    bw.write(s)
    bw.close()
  }

  def apply(e: SparkSessionExtensions): Unit = e.injectOptimizerRule { _ =>
    println("NOW INITIATING OPTIMIZATIONS")
    writeFile("/databricks/test", "NOW INITIATING OPTIMIZATIONS")
    SpatialFilterPushdownRules
  }
}
