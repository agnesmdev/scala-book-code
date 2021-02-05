package eni

import org.scalatest.wordspec.AnyWordSpec

class WordSpecTest extends AnyWordSpec {

  "Le texte" can {
    "'Exemple'" should {
      val exemple = "Exemple"

      "contenir le mot 'Ex'" in {
        assert(exemple.contains("Ex"))
      }

      "avoir sept lettres" in {
        assert(exemple.length == 7)
      }
    }
  }

  "Opérations basiques" can {
    "Diviser par zéro" should {
      "renvoyer une exception" in {
        assertThrows[ArithmeticException] {
          1 / 0
        }
      }
    }

    "Accéder à un élément d'une liste vide" should {
      "renvoyer une exception" in {
        val exception = intercept[NoSuchElementException] {
          Seq().head
        }
        assert(exception.getMessage == "head of empty list")
      }
    }

    "Un plus un" should {
      "être égal à trois" ignore {
        assert(1 + 1 === 3)
      }
    }
  }
}
