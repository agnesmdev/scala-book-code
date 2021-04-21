package eni.exemples

import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec

class FeatureSpecTest extends AnyFeatureSpec with GivenWhenThen {

  info("Tests effectués avec FeatureSpec")
  info("Écrits par une personne non développeuse")

  Scenario("Manipulation") {
    Given("Le texte 'Exemple'")
    val exemple = "Exemple"
    System.err.println()

    When("On récupère les deux premières lettres")
    val debut = exemple.substring(0, 2)

    Then("Elles sont égales à 'Ex'")
    assert(debut == "Ex")

    Then("Sa taille est de deux")
    assert(debut.length == 2)
  }

  Feature("Opérations basiques") {
    Scenario("Division par zéro") {
      Given("Le nombre 1")
      val nombre = 1

      When("On divise par 0")
      val exception = intercept[Exception] {
        nombre / 0
      }

      Then("Une exception de type ArithmeticException est levée")
      assert(exception.isInstanceOf[ArithmeticException])
    }

    Scenario("Accéder à un élément") {
      Given("Une liste vide")
      val liste = Seq()

      When("On accède à son premier élément")
      val exception = intercept[Exception] {
        liste.head
      }

      Then("Une exception de type NoSuchElementException est levée")
      assert(exception.isInstanceOf[NoSuchElementException])

      Then("Avec un message explicite")
      assert(exception.getMessage == "head of empty list")
    }

    Scenario("Addition de nombres") {
      Given("Deux nombres égaux à 1")
      val nombre_1 = 1
      val nombre_2 = 1

      When("On les additionne")
      val somme = nombre_1 + nombre_2

      Then("Le résultat est 3")
      assert(somme === 3)
    }
  }
}
