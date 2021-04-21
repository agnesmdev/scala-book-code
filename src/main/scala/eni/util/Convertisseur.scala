package eni.util

import scala.io.StdIn.readLine

object Convertisseur {

  def booleanVersString(boolean: Boolean): String = {
    if (boolean) "OUI" else "NON"
  }

  def lireBoolean(): Boolean = readLine() match {
    case "Oui" | "O" | "oui" | "" => true
    case _ => false
  }
}
