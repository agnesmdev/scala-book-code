package eni

import com.typesafe.config.ConfigFactory
import io.circe.config.syntax._
import io.circe.generic.auto._

import java.io.File

object ChapitreQuatre {

  case class Environnement(obligatoire: String, optionnelle: Option[String])

  def main(args: Array[String]): Unit = {
    println("Début du programme")

    val configuration = ConfigFactory.load()
    // println("Variable d'environnement VARIABLE_OBLIGATOIRE : " + configuration.getString("environnement.obligatoire"))
    // println("Variable d'environnement VARIABLE_OPTIONNELLE : " + configuration.getString("environnement.optionnelle"))

    val environnement = configuration.getConfig("environnement").as[Environnement]
    println("Variables d'environnement : " + environnement)

    val fichierConfiguration = new File( "src/main/resources/dev.conf")
    val configurationDev = ConfigFactory.parseFile(fichierConfiguration)

    println("[DEFAUT] Variable texte : " + configuration.getString("variable.texte"))
    println("[DEV] Variable dev : " + configurationDev.getInt("dev"))
    println("[DEV] Variable texte : " + configurationDev.getString("variable.texte"))

    /*
    println("[DEV] Variable nombre : " + configurationDev.getInt("variable.nombre"))
    // com.typesafe.config.ConfigException$Missing: No configuration setting found for key 'variable.nombre'
     */

    val configurationComplete = configurationDev.withFallback(configuration)
    println("[COMPLETE] Variable texte : " + configurationComplete.getString("variable.texte"))
    println("[COMPLETE] Variable nombre : " + configurationComplete.getString("variable.nombre"))
  }
}
