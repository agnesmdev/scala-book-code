import scala.annotation.tailrec

/**
 * Redéfinir la méthode find uniquement avec les méthodes head et tail.
 */

@tailrec
def findRedefini[T](liste: List[T], filtre: T => Boolean): Option[T] = {
  if (liste.isEmpty) {
    None
  } else if (filtre(liste.head)) {
    Some(liste.head)
  } else {
    findRedefini(liste.tail, filtre)
  }
}