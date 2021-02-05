package eni

import org.scalatest.funspec.AnyFunSpec

class FunSpecTest extends AnyFunSpec {

  val exemple = "Exemple"

  describe("Le texte 'Exemple'") {
    it("contient le mot 'Ex'") {
      assert(exemple.contains("Ex"))
    }

    it("a sept lettres") {
      assert(exemple.length == 7)
    }
  }

  describe("Diviser par zéro") {
    it("renvoie une exception") {
      assertThrows[ArithmeticException] {
        1 / 0
      }
    }
  }

  describe("Opérations basiques") {
    it("Accéder à un élément d'une liste vide renvoie une exception") {
      val exception = intercept[NoSuchElementException] {
        Seq().head
      }
      assert(exception.getMessage == "head of empty list")
    }

    ignore("Un plus un égal trois") {
      assert(1 + 1 === 3)
    }
  }
}
