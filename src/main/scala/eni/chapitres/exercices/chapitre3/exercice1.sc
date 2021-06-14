/**
 * Définir une fonction récursive qui implémente l’opération de puissance d’un nombre.
 */

val puissance: (Int, Int) => Int = (nombre, exposant) => {
  if (exposant == 0) {
    1
  } else if (exposant == 1) {
    nombre
  } else {
    puissance(nombre * exposant, exposant - 1)
  }
}
