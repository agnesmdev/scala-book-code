/** Nom du projet */
name := "projet-scala"

/** Version de Scala */
scalaVersion := "2.12.10"

/** Nom du paquet parent */
organization := "eni.scala"

/** Version du projet */
version := "1.0.0"

/** Dépôt de librairies Scala ou Java */
resolvers += Resolver.bintrayRepo("tmacedo", "maven")

/** Déclaration de variable locale */
val AkkaVersion = "2.6.16"
val AkkaHttpVersion = "10.2.6"

/** Dépendances vers des libraires Scala ou Java */
libraryDependencies ++= Seq(
  /** Dépendance dans les classes de production dépendante de la version de Scala */
  "com.lihaoyi" %% "scalatags" % "0.9.4",

  /** Dépendance dans les classes de production indépendante de la version de Scala */
  "commons-net" % "commons-net" % "20030805.205232",

  /** Dépendance dans les classes de production provenant d'un répertoire personnalisé */
  "uk.gov.hmrc" %% "emailaddress" % "2.1.0",

  /** Dépendance pour les tests (deprecated) */
  "org.scalactic" %% "scalactic" % "3.2.9",

  /** Dépendances Akka pour créer une API */
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,

  /** Dépendance pour gérer le logging */
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.5",

  /** Dépendance Spray pour gérer les JSON */
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,

  /** Dépendances pour sérialiser la configuration */
  "io.circe" %% "circe-config" % "0.8.0",
  "io.circe" %% "circe-generic" % "0.14.1",

  /** Dépendance pour effectuer le hashage */
  "javax.xml.bind" % "jaxb-api" % "2.3.1",

  /** Dépendances pour les bases de données */
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.h2database" % "h2" % "1.4.200",

  /** Dépendances dans les classes de tests */
  "org.scalatest" %% "scalatest" % "3.2.9" % Test
)

lazy val projet = (project in file("."))
  .enablePlugins(ScoverageSbtPlugin)
