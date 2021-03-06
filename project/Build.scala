import sbt._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "megam_play"

  val appVersion = "0.1.0"

  val organization = "Megam Systems"

  val homepage = Some(url("http://www.megam.co"))

  val startYear = Some(2013)

  val description = "RESTful API server for the megam platform. Uses Riak and Memcache"

  /**
   *   if you use groupID %% artifactID % revision instead of groupID % artifactID % revision
   *   (the difference is the double %% after the groupID), sbt will add your project’s Scala version
   *   to the artifact name.
   */

  val scalazVersion = "7.0.3"
  val scalaCheckVersion = "1.10.1"
  val play2AuthVersion = "0.10.1"
  val specs2Version = "2.1.1"
  val liftJsonVersion = "2.5.1"
  val megamVersion = "0.1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore, javaEbean,
    "com.twitter.service" % "snowflake" % "1.0.2" from "https://s3-ap-southeast-1.amazonaws.com/megampub/0.1/jars/snowflake.jar", //don't move this below.
    "com.github.indykish" % "megam_common_2.10" % megamVersion excludeAll (
      ExclusionRule("commons-logging","commons-logging"),
      ExclusionRule("org.slf4j","slf4j-jdk14")),
    "com.github.mumoshu" %% "play2-memcached" % "0.3.0.2",
    "jp.t2v" %% "play2.auth" % play2AuthVersion,
    "jp.t2v" %% "play2.auth.test" % play2AuthVersion % "test",
    "com.stackmob" %% "newman" % "0.23.0" % "test")

  val main = play.Project(appName, appVersion, appDependencies).settings(
    sbt.Keys.resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    sbt.Keys.resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    sbt.Keys.resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots",
    sbt.Keys.resolvers += "Twitter Repo" at "http://maven.twttr.com", // finagle 
    sbt.Keys.resolvers += "spray repo" at "http://repo.spray.io", //spray client used in newman.
    sbt.Keys.resolvers += "spray nightly" at "http://nightlies.spray.io", //spray client nighly used in newman (0.23.0).
    sbt.Keys.resolvers += "Spy Repository" at "http://files.couchbase.com/maven2" // required to resolve `spymemcached`, the plugin's dependency.
    )

}
