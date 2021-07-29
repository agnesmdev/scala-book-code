package eni.services

import eni.modeles.machine.{EtatProduit, Produit}

import scala.concurrent.Future

trait ProduitService {

  def total(): Double

  def acheterProduit(produit: Produit, monnaie: Double): Future[Double]

  def produitsRestants(produit: Produit): EtatProduit

  def ajouterProduit(produit: Produit, nombre: Int): Int

  def supprimerProduit(produit: Produit): Double
}
