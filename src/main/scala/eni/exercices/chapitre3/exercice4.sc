/**
 * Définir une méthode tombola qui renvoie un boolean selon les critères suivants :
 * - si l’entrée est une String :
 *    - si elle est égal à “BINGO”, on renvoie true
 *    - si sa taille est supérieure à 5, on renvoie true
 *    - sinon, on renvoie false
 * - si l’entrée est un Int :
 *    - s’il est pair, on renvoie true
 *    - sinon, on renvoie false
 * - si l’entrée est une liste :
 *    - si elle a 3 éléments, on renvoie true
 *    - sinon, on renvoie false
 * - si l'entrée est un couple, on renvoie true
 * - sinon, on renvoie false
 */


def tombola(value: Any) = value match {
  case "BINGO" => true
  case s: String if s.length > 5 => true
  case s: String => false
  case i: Int if i % 2 == 0 => true
  case i: Int => false
  case Seq(_, _, _) => true
  case l: Seq[Any] => false
  case (a, b) => true
  case _ => false
}

// version simplifiée
def tombolaSimplifiee(value: Any) = value match {
  case "BINGO" => true
  case s: String if s.length > 5 => true
  case i: Int if i % 2 == 0 => true
  case Seq(_, _, _) => true
  case _ => false
}
