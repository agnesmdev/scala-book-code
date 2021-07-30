/**
 * Définir une classe Trilogie qui étend la classe Livre qui prend comme argument trois chaînes de caractère,
 * un nombre de pages, et qui surcharge le paramètre epaisseur.
 */
class Livre(titre: String, pages: Int) {
  private val couverture = s"- $titre -"
  protected val epaisseur = pages * 10
  val description = s"Livre 1 : $couverture"
}

class Trilogie(livre1: String, livre2: String, livre3: String, pages: Int) extends Livre(s"$livre1 - $livre2 - $livre3", pages * 3) {
  override val epaisseur: Int = pages * 30
}