package eni.modeles

import org.slf4j.LoggerFactory

case class Test(nom: String, numero: Int) {
  Test.loggerTest.trace(s"Creation du test n°$numero $nom")
}

object Test {
  private val loggerTest = LoggerFactory.getLogger(classOf[Test])
}
