package eni

import scala.io.StdIn.{readBoolean, readInt, readLine}

object Bonjour extends App {

  print("Quel est votre prénom ? ")
  val prenom = readLine()

  print("Quel est votre nom ? ")
  val nom = readLine()

  print("Quel est votre âge ? ")
  val age = try {
    readInt()
  } catch {
    case _: Throwable => "???"
  }

  print("Allez-vous bien ? ")
  val humeur = readBoolean()

  println(s"Bonjour $prenom $nom !")
  println(s"Vous avez $age ans !")
  println(s"Vous vous sentez ${if (humeur) "bien" else "mal"}")
}

