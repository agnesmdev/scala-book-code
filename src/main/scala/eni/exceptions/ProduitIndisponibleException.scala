package eni.exceptions

import eni.modeles.Produit

case class ProduitIndisponibleException(produit: Produit) extends Exception {

  override def getMessage: String = s"Produit $produit indisponible"
}
