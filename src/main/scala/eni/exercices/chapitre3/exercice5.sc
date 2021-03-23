/**
 * Définir une méthode qui prend en argument deux nombres et renvoie le milieu de ces deux nombres si celui-ci est un entier.
 */
def milieu(nombre1: Int, nombre2: Int) = {
  val milieu: Double = (nombre1 + nombre2) / 2.0
  if (milieu.isValidInt) Some(milieu.toInt) else None
}
