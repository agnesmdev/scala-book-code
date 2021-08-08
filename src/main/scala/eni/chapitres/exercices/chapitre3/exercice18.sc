/**
 * Redéfinir la méthode sum uniquement avec les méthodes head et tail.
 */

def sumRedefini(liste: List[Int]): Int = {
  liste.foldLeft(0) {
    case (somme, element) => somme + element
  }
}