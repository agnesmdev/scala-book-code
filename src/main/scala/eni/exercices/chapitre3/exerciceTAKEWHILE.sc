import scala.annotation.tailrec

/**
 * Redéfinir la méthode takeWhile uniquement avec les méthodes head et tail.
 */

@tailrec
def takeWhileRedefini[T](liste: List[T], filtre: T => Boolean, resultat: List[T]): List[T] = {
  if (liste.isEmpty || !filtre(liste.head)) {
    resultat
  } else {
    takeWhileRedefini(liste.tail, filtre, resultat ++ Seq(liste.head))
  }
}