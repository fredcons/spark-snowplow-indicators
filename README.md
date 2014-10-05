# Snowplow Indicators Project

## Introduction

This is a job written in Scala for the [Spark] [spark] cluster computing platform, with instructions for running on [Amazon Elastic MapReduce] [emr] in non-interactive mode.

_See also:_ [Scalding Example Project] [scalding-example-project] | [Cascalog Example Project] [cascalog-example-project]

## Building

Assuming you already have [SBT] [sbt] installed:

    $ git clone git://github.com/fredcons/spark-snowplow-indicators.git
    $ cd spark-snowplow-indicators
    $ sbt assembly

The 'fat jar' is now available as:

    target/spark-snowplow-indicators-0.1.jar

## Unit testing

The `assembly` command above runs the test suite - but you can also run this manually with:

    $ sbt test
    <snip>
    [info] A SnowplowIndicators job should
    [info] + count hits by day correctly
    [info] Passed: : Total 1, Failed 0, Errors 0, Passed 1, Skipped 0

## Running on Amazon EMR

### Prepare

Assuming you have already assembled the jarfile (see above), now upload the jar to an Amazon S3 bucket and make the file publicly accessible.

Next, upload the data file [`data/hello.txt`] [hello-txt] to S3.

### Run

Finally, you are ready to run this job using the [Amazon Ruby EMR client] [emr-client]:

```
$ elastic-mapreduce --jobflow j-2AVD0T5KO1Z79 --jar s3://elasticmapreduce/libs/script-runner/script-runner.jar
  --step-name "Spark Snowplow Indicators" --step-action TERMINATE_JOB_FLOW
  --arg s3://fredcons/snowplow/scripts/run-spark-job.sh
  --arg s3://fredcons/snowplow/libs/spark-snowplow-indicators_2.10-0.1.jar
  --arg com.snowplowanalytics.spark.SnowplowIndicatorsJob
  --arg s3n://fredcons/snowplow/data/ --arg s3n://fredcons/snowplow/results/spark3
```


[spark]: http://spark-project.org/
[wordcount]: https://github.com/twitter/scalding/blob/master/README.md
[snowplow]: http://snowplowanalytics.com
[data-pipelines-algos]: http://snowplowanalytics.com/services/pipelines.html

[scalding-example-project]: https://github.com/snowplow/scalding-example-project
[cascalog-example-project]: https://github.com/snowplow/cascalog-example-project

[issue-1]: https://github.com/snowplow/spark-example-project/issues/1
[issue-2]: https://github.com/snowplow/spark-example-project/issues/2
[aws-spark-tutorial]: http://aws.amazon.com/articles/4926593393724923
[spark-emr-howto]: https://forums.aws.amazon.com/thread.jspa?messageID=458398

[sbt]: http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html

[emr]: http://aws.amazon.com/elasticmapreduce/
[hello-txt]: https://github.com/snowplow/spark-example-project/raw/master/data/hello.txt
[emr-client]: http://aws.amazon.com/developertools/2264

[elasticity]: https://github.com/rslifka/elasticity
[spark-plug]: https://github.com/ogrodnek/spark-plug
[lemur]: https://github.com/TheClimateCorporation/lemur
[boto]: http://boto.readthedocs.org/en/latest/ref/emr.html

[license]: http://www.apache.org/licenses/LICENSE-2.0
