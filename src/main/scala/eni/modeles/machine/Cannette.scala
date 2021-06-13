package eni.modeles.machine

sealed trait Cannette extends Produit {
  final val prefixe: String = "C-"
  override final lazy val code: String = s"$prefixe${boisson.code}"
  override val prix: Double = 1.0
  override val quantiteInitiale: Int = 10

  def boisson: Boisson
}

case object CannetteSoda extends Cannette {
  override val boisson: Boisson = Soda
}

case object CannetteBiere extends Cannette {
  override val boisson: Boisson = Biere
  override val prix: Double = 2.0
  override val quantiteInitiale: Int = 5
}

case object CannetteJus extends Cannette {
  override val boisson: Boisson = Jus
}
