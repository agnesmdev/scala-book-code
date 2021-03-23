/**
 * Définir une classe Labrador qui prend comme argument son nom et qui étend à la fois la classe Chien et le trait Ami.
 */

trait Animal {
  def nombreDePattes(): Int

  def son(): String

  def imprimeSon(): String = s"L'animal fait ${son()}"
}

class Chien extends Animal {
  def nombreDePattes(): Int = 4

  def son(): String = "Ouaf !"
}

trait Ami {
  def nom: String
}

class Labrador(nomDuChien: String) extends Chien with Ami {
  def nom: String = nomDuChien
}
