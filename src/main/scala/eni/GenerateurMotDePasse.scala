package eni

import com.typesafe.config.ConfigFactory

import java.security.MessageDigest
import scala.io.StdIn.readLine
import javax.xml.bind.DatatypeConverter

object GenerateurMotDePasse {

  def main(args: Array[String]): Unit = {
    val secret = ConfigFactory.load().getString("api.secret")

    println("Veuillez entrer un nom d'utilisateur : ")
    val utilisateur = readLine()
    val hashage = MessageDigest.getInstance("SHA-256")
    val motDePasse = s"$secret-$utilisateur"
    val motDePasseHashe = DatatypeConverter.printHexBinary(hashage.digest(motDePasse.getBytes))

    println(s"Voici votre mot de passe : $motDePasseHashe")
  }
}
