package eni.exceptions

import eni.modeles.machine.Produit

case class ProduitIndisponibleException(produit: Produit) extends Exception {

  override def getMessage: String = s"Produit $produit indisponible"
}
