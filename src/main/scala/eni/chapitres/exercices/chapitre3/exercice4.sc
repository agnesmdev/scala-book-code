/**
 * Définir une méthode qui prend en argument deux nombres et renvoie le moyenne de ces deux nombres si celui-ci est un entier.
 */
def moyenne(nombre1: Int, nombre2: Int) = {
  val moyenne: Double = (nombre1 + nombre2) / 2.0
  if (moyenne.isValidInt) Some(moyenne.toInt) else None
}
