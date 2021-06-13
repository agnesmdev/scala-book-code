package eni.modeles.machine

case class EtatProduit(vendus: Int, restants: Int) {
  def acheter: EtatProduit = EtatProduit(vendus + 1, restants - 1)

  def supprimer: EtatProduit = EtatProduit(vendus, 0)

  def ajouter(nombre: Int): EtatProduit = EtatProduit(vendus, restants + nombre)
}

object EtatProduit {
  def apply(produit: Produit): EtatProduit = EtatProduit(0, produit.quantiteInitiale)
}
