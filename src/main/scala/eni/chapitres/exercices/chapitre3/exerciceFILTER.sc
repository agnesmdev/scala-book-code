import scala.annotation.tailrec

/**
 * Redéfinir la méthode filter uniquement avec les méthodes head et tail.
 */

@tailrec
def filterRedefini[T](liste: List[T], filtre: T => Boolean, resultat: List[T] = Nil): List[T] = {
  if (liste.isEmpty) {
    resultat
  } else {
    filterRedefini(liste.tail, filtre, if (filtre(liste.head)) resultat ++ Seq(liste.head) else resultat)
  }
}