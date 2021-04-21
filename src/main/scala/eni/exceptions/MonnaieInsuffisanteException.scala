package eni.exceptions

import eni.modeles.Produit

case class MonnaieInsuffisanteException(produit: Produit, monnaie: Double) extends Exception {

  override def getMessage: String = s"Monnaie insuffisante pour le produit $produit, merci de donner ${produit.prix - monnaie} € en plus"
}
