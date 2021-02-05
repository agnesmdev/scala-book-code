package eni

import org.scalatest.funsuite.AnyFunSuite

class FunSuiteTest extends AnyFunSuite {

  val exemple = "Exemple"

  test("Le texte 'Exemple' contient le mot 'Ex'") {
    assert(exemple.contains("Ex"))
  }

  test("Le texte 'Exemple' a sept lettres") {
    assert(exemple.length == 7)
  }

  test("Diviser par zéro renvoie une exception") {
    assertThrows[ArithmeticException] {
      1 / 0
    }
  }

  test("Accéder à un élément d'une liste vide renvoie une exception") {
    val exception = intercept[NoSuchElementException] {
      Seq().head
    }
    assert(exception.getMessage == "head of empty list")
  }

  ignore("Un plus un égal trois") {
    assert(1 + 1 == 3)
  }

  ignore("Un plus un égal trois avec un message explicite") {
    assert(1 + 1 === 3)
  }
}
