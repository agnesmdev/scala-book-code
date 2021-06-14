/**
 * Définir une boucle for qui parcourt deux listes de String et effectue pour les String de même taille :
 * - si les deux String commencent par la même lettre, on imprime les deux String séparées par un “+”
 * - sinon, on imprime les deux String séparées par un “-”
 */
val liste1 = List("tour", "camion", "téléphone")
val liste2 = List("acrobate", "chat", "poison")

for {
  s1 <- liste1
  s2 <- liste2 if s2.length == s1.length
} yield {
  val i1 = s1.head
  val i2 = s2.head

  if (i1 == i2) println(s"$s1 + $s2")
  else println(s"$s1 - $s2")
}