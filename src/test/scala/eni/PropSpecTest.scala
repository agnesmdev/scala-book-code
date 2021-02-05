package eni

import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.propspec.AnyPropSpec

class PropSpecTest extends AnyPropSpec with TableDrivenPropertyChecks with Matchers {

  private val animaux = Table(
    "animaux",
    Seq("chat", "chien"),
    Seq("poule", "perroquet"),
    Seq("serpent", "poisson")
  )

  property("Toutes les listes ont deux éléments") {
    forAll(animaux) { liste =>
      liste.length shouldEqual 2
    }
  }

  property("Accéder au troisième élément de chaque liste renvoie une exception") {
    forAll(animaux) { liste =>
      a[IndexOutOfBoundsException] should be thrownBy {
        liste(2)
      }
    }
  }
}
