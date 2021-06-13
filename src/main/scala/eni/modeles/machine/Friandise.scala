package eni.modeles.machine

sealed trait Friandise extends Produit {
  override val prix: Double = 1.0
  override val quantiteInitiale: Int = 5
}

case object Chocolat extends Friandise {
  override val code: String = "CHO"
}

case object Bonbons extends Friandise {
  override val code: String = "BBN"
}

case object BarreEnergetique extends Friandise {
  override val code: String = "BAR"
  override val prix: Double = 1.5
}

case object Reglisse extends Friandise {
  override val code: String = "REG"
  override val quantiteInitiale: Int = 3
}

