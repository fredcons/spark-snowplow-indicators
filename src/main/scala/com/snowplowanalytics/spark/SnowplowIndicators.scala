/*
 * Copyright (c) 2012-2013 SnowPlow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.spark

// Spark
import org.apache.spark.SparkContext
import SparkContext._

object SnowplowIndicators {
  
  private val AppName = "SnowplowIndicatorsJob"

  // Run the word count. Agnostic to Spark's current mode of operation: can be run from tests as well as from main
  def execute(master: String, args: List[String], jars: Seq[String] = Nil) {

    val in = args(0)
    val out = args(1)

    val sc = new SparkContext(master, AppName, null, jars)
    val rawHits = sc.textFile(in).cache()
    val lines = rawHits.map{line =>
      val fields = line.split('\t')
      (new Visit(fields(15), fields(29)), 1)
    }

    val reduced = lines.reduceByKey((x, y) => x + y).sortByKey(true)

    reduced.saveAsTextFile(out)
  }

  case class Visit(userId: String, pageUrl: String) extends Ordered[Visit] {
    def compare(that: Visit): Int =  if ((this.userId compare that.userId) == 0) this.pageUrl compare that.pageUrl else this.userId compare that.userId
  }
}
