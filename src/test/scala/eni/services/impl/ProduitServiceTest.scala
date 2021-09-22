package eni.services.impl

import eni.exceptions.{MonnaieInsuffisanteException, ProduitIndisponibleException}
import eni.modeles.machine.{BarreEnergetique, BouteilleJus, CanetteBiere, CanetteJus, Chocolat, Reglisse}
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AsyncFeatureSpec

class ProduitServiceTest extends AsyncFeatureSpec with GivenWhenThen {

  Scenario("État initial") {
    Given("Un produit quelconque")
    val produit = Chocolat

    When("La machine est initialisée")
    val produitService = new ProduitServiceImpl

    Then("Le total est nul")
    assert(produitService.total() == 0.0)

    Then("Il reste encore 10 produits")
    assert(produitService.produitsRestants(produit).restants == produit.quantiteInitiale)
  }

  Scenario("Achat d'un produit concluant") {
    Given("Avec assez de monnaie")
    val produitService = new ProduitServiceImpl
    val produit = CanetteBiere
    val monnaie = produit.prix + 1

    When("Achat d'un produit")
    val resultat = produitService.acheterProduit(produit, monnaie)

    Then("1 euro est rendu")
    resultat.map { reste =>
      assert(reste == 1.0)

      Then("Il reste un produit en moins")
      val etatProduit = produitService.produitsRestants(produit)
      assert(etatProduit.restants == produit.quantiteInitiale - 1)

      Then("Le total est mis à jour")
      val total = produitService.total()
      assert(total == produit.prix)
    }
  }

  Scenario("Achat d'un produit sans assez de monnaie") {
    Given("Avec trop peu de monnaie")
    val produitService = new ProduitServiceImpl
    val produit = BouteilleJus
    val monnaie = produit.prix - 1

    When("Achat d'un produit")
    recoverToSucceededIf[MonnaieInsuffisanteException] {
      Then("Le futur se termine en échec")
      produitService.acheterProduit(produit, monnaie)
    }
  }

  Scenario("Achat d'un produit manquant") {
    Given("Un produit supprimé")
    val produitService = new ProduitServiceImpl
    val produit = Reglisse
    produitService.supprimerProduit(produit)

    Given("Avec assez de monnaie")
    val monnaie = produit.prix

    When("Achat d'un produit")
    recoverToSucceededIf[ProduitIndisponibleException] {
      Then("Le futur se termine en échec")
      produitService.acheterProduit(produit, monnaie)
    }
  }

  Scenario("Suppression d'un produit") {
    Given("Un produit encore disponible")
    val produitService = new ProduitServiceImpl
    val produit = CanetteJus

    Given("Achat du produit avec assez d'argent")
    produitService.acheterProduit(produit, produit.prix)

    When("Suppression du produit")
    val resultat = produitService.supprimerProduit(produit)

    Then("Le montant vendu est 1.0")
    assert(resultat == 1.0)

    Then("Le produit n'est plus disponible")
    val etatProduit = produitService.produitsRestants(produit)
    assert(etatProduit.restants == 0)
  }

  Scenario("Ajout d'un produit") {
    Given("Un produit quelconque")
    val produitService = new ProduitServiceImpl
    val produit = BarreEnergetique

    When("Ajout du produit")
    val resultat = produitService.ajouterProduit(produit, 5)

    Then("Il reste 10 produits")
    val etatProduit = produitService.produitsRestants(produit)
    assert(etatProduit.restants == produit.quantiteInitiale + 5)
  }
}
