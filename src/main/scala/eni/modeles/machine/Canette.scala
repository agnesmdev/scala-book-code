package eni.modeles.machine

sealed trait Canette extends Produit {
  final val prefixe: String = "C-"
  override final lazy val code: String = s"$prefixe${boisson.code}"
  override val prix: Double = 1.0
  override val quantiteInitiale: Int = 10

  def boisson: Boisson
}

case object CanetteSoda extends Canette {
  override val boisson: Boisson = Soda
}

case object CanetteBiere extends Canette {
  override val boisson: Boisson = Biere
  override val prix: Double = 2.0
  override val quantiteInitiale: Int = 5
}

case object CanetteJus extends Canette {
  override val boisson: Boisson = Jus
}
