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
val AkkaVersion = "2.5.13"
val AkkaHttpVersion = "10.1.3"

/** Dépendances vers des libraires Scala ou Java */
libraryDependencies ++= Seq(
  /** Dépendance dans les classes de production dépendante de la version de Scala */
  "com.lihaoyi" %% "scalatags" % "0.8.2",

  /** Dépendance dans les classes de production indépendante de la version de Scala */
  "commons-net" % "commons-net" % "3.6",

  /** Dépendance dans les classes de production provenant d'un répertoire personnalisé */
  "uk.gov.hmrc" %% "emailaddress" % "2.1.0",

  /** Dépendance pour les tests (deprecated) */
  "org.scalactic" %% "scalactic" % "3.0.8",

  /** Dépendances Akka pour créer une API */
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,

  /** Dépendances Spray pour gérer les JSON */
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,

  /** Dépendances dans les classes de tests */
  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)

lazy val projet = (project in file("."))
  .enablePlugins(ScoverageSbtPlugin)
