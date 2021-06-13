package eni.modeles.machine

trait Produit {

  override final def toString: String = code

  def prix: Double

  def code: String

  def quantiteInitiale: Int
}

object Produit {
  val tousLesProduits: List[Produit] = List(
    BouteilleEau,
    BouteilleSoda,
    BouteilleJus,
    CannetteSoda,
    CannetteBiere,
    CannetteJus,
    Chocolat,
    Bonbons,
    BarreEnergetique,
    Reglisse
  )

  def parse(code: String): Option[Produit] = code match {
    case BouteilleEau.code => Some(BouteilleEau)
    case BouteilleSoda.code => Some(BouteilleSoda)
    case BouteilleJus.code => Some(BouteilleJus)
    case CannetteSoda.code => Some(CannetteSoda)
    case CannetteBiere.code => Some(CannetteBiere)
    case CannetteJus.code => Some(CannetteJus)
    case Chocolat.code => Some(Chocolat)
    case Bonbons.code => Some(Bonbons)
    case BarreEnergetique.code => Some(BarreEnergetique)
    case Reglisse.code => Some(Reglisse)
    case _ => None
  }
}
