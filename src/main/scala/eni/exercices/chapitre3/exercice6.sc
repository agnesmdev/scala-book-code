/**
 * Définir une méthode transforme qui prend en entrée une liste d’Int et qui fait les opérations suivantes :
 * - prend les 6 premiers éléments de la liste supérieurs à 0
 * - les divise par 5
 * - prend les premiers éléments de la liste inférieurs à 1
 * - si la liste contient 1 élément, on renvoie "UN" sinon, renvoie la taille en String
 */
def transforme(l: Seq[Int]): String = {
  val l1 = l.filter(_ > 0)
  val l2 = l1.take(6)
  val l3 = l2.map(_ / 5)
  val l4 = l3.takeWhile(_ < 1)
  l4 match {
    case Seq(_) => "UN"
    case l => l.length.toString
  }
}

// version simplifiée
def transformeSimplifiee(l: Seq[Int]): String = {
  l.filter(_ > 0).take(6).map(_ / 5).takeWhile(_ < 1) match {
    case Seq(_) => "UN"
    case l => l.length.toString
  }
}
