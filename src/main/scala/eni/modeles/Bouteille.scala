package eni.modeles

sealed trait Bouteille extends Produit {
  final val prefixe: String = "B-"
  override final lazy val code: String = s"$prefixe${boisson.code}"
  override val prix: Double = 2.0
  override val quantiteInitiale: Int = 7

  def boisson: Boisson
}

case object BouteilleEau extends Bouteille {
  override val boisson: Boisson = Eau
  override val prix: Double = 1.0
}

case object BouteilleSoda extends Bouteille {
  override val boisson: Boisson = Soda
}

case object BouteilleJus extends Bouteille {
  override val boisson: Boisson = Jus
  override val quantiteInitiale: Int = 5
}
