import scala.annotation.tailrec

/**
 * Redéfinir la méthode zipWithIndex uniquement avec les méthodes head et tail.
 */
@tailrec
def zipWithIndexRedefini[T](liste: List[T], resultat: List[(T, Int)] = Nil): List[(T, Int)] = {
  if (liste.isEmpty) {
    resultat
  } else {
    zipWithIndexRedefini(liste.tail, resultat ++ List((liste.head, resultat.length)))
  }
}

