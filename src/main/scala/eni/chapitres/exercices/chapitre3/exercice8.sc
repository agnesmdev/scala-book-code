import scala.annotation.tailrec

/**
 * Redéfinir la méthode map uniquement avec les méthodes head et tail.
 */

@tailrec
def mapRedefini[T, U](liste: List[T], operation: T => U, resultat: List[U] = Nil): List[U] = {
  if (liste.isEmpty) {
    resultat
  } else {
    mapRedefini(liste.tail, operation, resultat ++ Seq(operation(liste.head)))
  }
}