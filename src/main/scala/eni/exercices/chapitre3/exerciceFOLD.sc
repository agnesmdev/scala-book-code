import scala.annotation.tailrec

/**
 * Redéfinir la méthode fold uniquement avec les méthodes head et tail.
 */

@tailrec
def foldRedefini[T](liste: List[T], operation: (T, T) => T, valeurInitiale: T): T = {
  if (liste.isEmpty) {
    valeurInitiale
  } else {
    foldRedefini(liste.tail, operation, operation(valeurInitiale, liste.head))
  }
}