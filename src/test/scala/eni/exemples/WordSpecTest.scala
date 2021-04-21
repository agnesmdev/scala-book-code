package eni.exemples

import org.scalatest.wordspec.AnyWordSpec

class WordSpecTest extends AnyWordSpec {

  "Un texte" when {
    "égal à 'Exemple'" must {
      val exemple = "Exemple"

      "contenir le mot 'Ex'" in {
        assert(exemple.contains("Ex"))
      }

      "avoir sept lettres" in {
        assert(exemple.length == 7)
      }
    }
  }

  "Opérations basiques" when {
    "Diviser par zéro" must {
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

    val equal = afterWord("être égal à")

    "Un plus un" should equal {
      "trois" ignore {
        assert(1 + 1 === 3)
      }
    }
  }
}
