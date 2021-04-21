package eni.exemples

import org.scalatest.flatspec.AnyFlatSpec

class FlatSpecTest extends AnyFlatSpec {

  it should "Accéder à un élément d'une liste vide renvoie une exception" in {
    val exception = intercept[NoSuchElementException] {
      Seq().head
    }
    assert(exception.getMessage == "head of empty list")
  }

  behavior of "Le texte 'Exemple'"
  val exemple = "Exemple"

  it should "contenir le mot 'Ex'" in {
    assert(exemple.contains("Ex"))
  }

  it should "avoir sept lettres" in {
    assert(exemple.length == 7)
  }

  "Diviser par zéro" should "renvoyer une exception" in {
    assertThrows[ArithmeticException] {
      1 / 0
    }
  }

  behavior of "Opérations basiques"

  ignore should "Un plus un égal trois" in {
    assert(1 + 1 == 3)
  }
}
