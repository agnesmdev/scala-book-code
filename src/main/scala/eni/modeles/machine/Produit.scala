package eni.modeles.machine

trait Produit {

  override final def toString: String = code

  def prix: Double

  def code: String

  def quantiteInitiale: Int

  def etatInitial: EtatProduit = EtatProduit(0, quantiteInitiale)
}

object Produit {
  val tousLesProduits: List[Produit] = List(
    BouteilleEau,
    BouteilleSoda,
    BouteilleJus,
    CanetteSoda,
    CanetteBiere,
    CanetteJus,
    Chocolat,
    Bonbons,
    BarreEnergetique,
    Reglisse
  )

  def parse(code: String): Option[Produit] = code match {
    case BouteilleEau.code => Some(BouteilleEau)
    case BouteilleSoda.code => Some(BouteilleSoda)
    case BouteilleJus.code => Some(BouteilleJus)
    case CanetteSoda.code => Some(CanetteSoda)
    case CanetteBiere.code => Some(CanetteBiere)
    case CanetteJus.code => Some(CanetteJus)
    case Chocolat.code => Some(Chocolat)
    case Bonbons.code => Some(Bonbons)
    case BarreEnergetique.code => Some(BarreEnergetique)
    case Reglisse.code => Some(Reglisse)
    case _ => None
  }
}
