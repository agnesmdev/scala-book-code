package eni.services.impl

import eni.exceptions.{MonnaieInsuffisanteException, ProduitIndisponibleException}
import eni.modeles.{EtatProduit, Produit}
import eni.services.IProduitService

import scala.collection.mutable
import scala.concurrent.Future

class ProduitServiceImpl extends IProduitService {

  private val mapProduits: mutable.HashMap[Produit, EtatProduit] = new mutable.HashMap[Produit, EtatProduit]()

  override def total(): Double = {
    mapProduits.map {
      case (produit, etat) => produit.prix * etat.vendus
    }.sum
  }

  override def acheterProduit(produit: Produit, monnaie: Double): Future[Double] = {
    // Vérifier la monnaie
    val difference = monnaie - produit.prix
    if (difference < 0) {
      return Future.failed(MonnaieInsuffisanteException(produit, monnaie))
    }

    // Récupérer l'état du produit
    val etat = getEtatProduit(produit)

    // Vérifier la quantité
    if (etat.restants <= 0) {
      return Future.failed(ProduitIndisponibleException(produit))
    }

    // Ajouter le produit
    val nouvelEtat = etat.acheter
    mapProduits.put(produit, nouvelEtat)

    // Rendre la monnaie
    Future.successful(difference)
  }

  override def ajouterProduit(produit: Produit, nombre: Int): Int = {
    val etat = getEtatProduit(produit)

    // Modifier le produit
    val nouvelEtat = etat.ajouter(nombre)
    mapProduits.put(produit, nouvelEtat)

    nouvelEtat.restants
  }

  override def supprimerProduit(produit: Produit): Double = {
    val etat = getEtatProduit(produit)

    // Modifier le produit
    val nouvelEtat = etat.supprimer
    mapProduits.put(produit, nouvelEtat)

    etat.vendus * produit.prix
  }

  override def produitsRestants(produit: Produit): Int = {
    getEtatProduit(produit).restants
  }

  private def getEtatProduit(produit: Produit): EtatProduit = {
    mapProduits.getOrElse(produit, EtatProduit(produit))
  }
}
