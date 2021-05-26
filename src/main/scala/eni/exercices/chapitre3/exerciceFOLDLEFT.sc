import scala.annotation.tailrec

/**
 * Redéfinir la méthode foldLeft uniquement avec les méthodes head et tail.
 */

@tailrec
def foldLeftRedefini[T, U](liste: List[T], operation: (U, T) => U, valeurInitiale: U): U = {
  if (liste.isEmpty) {
    valeurInitiale
  } else {
    foldLeftRedefini(liste.tail, operation, operation(valeurInitiale, liste.head))
  }
}
