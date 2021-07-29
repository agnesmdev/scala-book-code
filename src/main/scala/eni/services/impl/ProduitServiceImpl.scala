package eni.services.impl

import eni.exceptions.{MonnaieInsuffisanteException, ProduitIndisponibleException}
import eni.modeles.machine.{EtatProduit, Produit}
import eni.services.ProduitService

import scala.collection.mutable
import scala.concurrent.Future

class ProduitServiceImpl extends ProduitService {

  private val mapProduits: mutable.HashMap[Produit, EtatProduit] = new mutable.HashMap[Produit, EtatProduit]()

  override def total(): Double = {
    // Récupération des montants pour chaque produit
    val montants = mapProduits.map {
      case (produit, etat) => produit.prix * etat.vendus
    }

    // Somme de tous les montants
    montants.sum
  }

  override def produitsRestants(produit: Produit): EtatProduit = {
    mapProduits.getOrElse(produit, produit.etatInitial)
  }

  override def acheterProduit(produit: Produit, monnaie: Double): Future[Double] = {
    // Récupérer l'état du produit
    val etat = produitsRestants(produit)

    // Vérifier la quantité
    if (etat.restants == 0) {
      return Future.failed(ProduitIndisponibleException(produit))
    }

    // Vérifier la monnaie
    val difference = monnaie - produit.prix
    if (difference < 0) {
      return Future.failed(MonnaieInsuffisanteException(produit, monnaie))
    }

    // Ajouter le produit
    val nouvelEtat = etat.acheter
    mapProduits.put(produit, nouvelEtat)

    // Rendre la monnaie
    Future.successful(difference)
  }

  override def ajouterProduit(produit: Produit, nombre: Int): Int = {
    // Récupérer l'état du produit
    val etat = produitsRestants(produit)

    // Modifier le produit
    val nouvelEtat = etat.ajouter(nombre)
    mapProduits.put(produit, nouvelEtat)

    // Renvoyer les produits restants
    nouvelEtat.restants
  }

  override def supprimerProduit(produit: Produit): Double = {
    // Récupérer l'état du produit
    val etat = produitsRestants(produit)

    // Modifier le produit
    val nouvelEtat = etat.supprimer
    mapProduits.put(produit, nouvelEtat)

    // Renvoyer le total vendu
    etat.vendus * produit.prix
  }
}
