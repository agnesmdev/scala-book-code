package eni

import scala.io.StdIn.{readInt, readLine}

object Bonjour {

  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Impossible de lancer sans argument")
      System.exit(-1)
    }

    println(s"Bonjour ${args.mkString(" ")}")

    print("Quel est votre prénom ? ")
    val prenom = readLine()

    print("Quel est votre nom ? ")
    val nom = readLine()

    print("Quel est votre âge ? ")
    val age = lireInt()

    print("Allez-vous bien ? ")
    val humeur = lireBoolean()

    println(s"Bonjour $prenom $nom !")
    println(s"Vous avez $age ans !")
    println(s"Vous vous sentez ${if (humeur) "bien" else "mal"}")
  }

  def lireBoolean(): Boolean = readLine() match {
    case "Oui" | "O" | "oui" | "" => true
    case _ => false
  }

  def lireInt(): String = try {
    readInt().toString
  } catch {
    case _: Throwable => "???"
  }
}

