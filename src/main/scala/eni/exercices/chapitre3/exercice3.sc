/**
 * Définir la méthode denomination qui renvoie une String dans le trait ClasseCouleur et
 * l’implémenter dans les case class enfants en utilisant l’attribut nom.
 */

abstract class ClasseCouleur {
  def denomination: String
}

case class CouleurPleine(nom: String) extends ClasseCouleur {
  def denomination: String = nom
}
