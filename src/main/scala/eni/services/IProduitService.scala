package eni.services

import eni.modeles.machine.Produit

import scala.concurrent.Future

trait IProduitService {

  def total(): Double

  def acheterProduit(produit: Produit, monnaie: Double): Future[Double]

  def ajouterProduit(produit: Produit, nombre: Int): Int

  def supprimerProduit(produit: Produit): Double

  def produitsRestants(produit: Produit): Int
}
