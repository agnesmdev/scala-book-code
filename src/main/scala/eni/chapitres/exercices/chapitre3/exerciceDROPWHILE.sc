import scala.annotation.tailrec

/**
 * Redéfinir la méthode dropWhile uniquement avec les méthodes head et tail.
 */

@tailrec
def dropWhileRedefini[T](liste: List[T], filtre: T => Boolean): List[T] = {
  if (liste.isEmpty || !filtre(liste.head)) {
    liste
  } else {
    dropWhileRedefini(liste.tail, filtre)
  }
}