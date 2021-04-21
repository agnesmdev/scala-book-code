package eni.exemples

import org.scalatest.freespec.AnyFreeSpec

class FreeSpecTest extends AnyFreeSpec {

  "Le texte" - {
    "'Exemple'" - {
      val exemple = "Exemple"

      "contient le mot 'Ex'" in {
        assert(exemple.contains("Ex"))
      }

      "a sept lettres" in {
        assert(exemple.length == 7)
      }
    }
  }

  "Diviser par zéro" - {
    "renvoie une exception" in {
      assertThrows[ArithmeticException] {
        1 / 0
      }
    }
  }

  "Opérations basiques" - {
    "Accéder à un élément d'une liste vide renvoie une exception" in {
      val exception = intercept[NoSuchElementException] {
        Seq().head
      }
      assert(exception.getMessage == "head of empty list")
    }

    "Un plus un égal trois" ignore {
      assert(1 + 1 === 3)
    }
  }
}
