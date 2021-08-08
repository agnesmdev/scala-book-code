import scala.annotation.tailrec

/**
 * Redéfinir la méthode mkString uniquement avec les méthodes head et tail.
 */

@tailrec
def mkStringRedefini[T](liste: List[T], separateur: String, resultat: String = ""): String = {
  if (liste.isEmpty) {
    resultat
  } else {
    mkStringRedefini(liste.tail, separateur, resultat + separateur + liste.head)
  }
}