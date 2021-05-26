import scala.annotation.tailrec

/**
 * Redéfinir la méthode exists uniquement avec les méthodes head et tail.
 */

@tailrec
def existsRedefini[T](liste: List[T], condition: T => Boolean): Boolean = {
  if (liste.isEmpty) {
    false
  } else if (condition(liste.head)) {
    true
  } else {
    existsRedefini(liste.tail, condition)
  }
}