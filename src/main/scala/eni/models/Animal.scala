package eni.models

case class Animal(nom: String, espece: String, nombreDePattes: Int) {
  def description: String = s"L'animal de l'espèce $espece nommé $nom a $nombreDePattes pattes !"
}
