import scala.annotation.tailrec

/**
 * Redéfinir la méthode forall uniquement avec les méthodes head et tail.
 */

@tailrec
def forallRedefini[T](liste: List[T], condition: T => Boolean, result: Boolean = false): Boolean = {
  if (liste.isEmpty) {
    result
  } else if (!condition(liste.head)) {
    false
  } else {
    forallRedefini(liste.tail, condition, true)
  }
}