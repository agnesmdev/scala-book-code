/**
 * Redéfinir la méthode collect uniquement avec les méthodes map et filter.
 */

def collectRedefini[T, U](liste: List[T], filtre: T => Boolean, operation: T => U): List[U] = {
  liste.filter(filtre).map(operation)
}