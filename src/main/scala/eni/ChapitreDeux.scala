package eni

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, Period}
import scala.io.StdIn.{readInt, readLine}
import Console.{RESET, BLUE, GREEN, RED, BOLD}

object ChapitreDeux {

  def main(args: Array[String]): Unit = {
    print(s"${BLUE}Quel est votre âge ? $RESET")
    val age = readInt()

    print(s"${BLUE}Combien de dates voulez-vous renseigner ? $RESET")
    val nombre = readInt()
    val formatteur = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val maintenant = LocalDate.now()
    var total = 0

    val ages = for (numero <- 1 to nombre) yield {
      print(s"${BLUE}Quel est sa date de naissance de la personne #$numero ? Écrivez-là au format dd/MM/yyyy : $RESET")
      val date = LocalDate.parse(readLine(), formatteur)

      val annees = Period.between(date, maintenant).getYears
      total += annees
      annees
    }

    val indexAge = ages.indexOf(age)
    if (indexAge >= 0) {
      println(s"La personne ${GREEN}#${indexAge + 1}$RESET a le même âge que vous !")
    } else {
      println(s"${RED}${BOLD}Personne n'a votre âge !$RESET")
    }

    val moyenne: BigDecimal = total.toDouble / nombre
    println(s"L'âge moyen est de ${BLUE}${moyenne.setScale(1, BigDecimal.RoundingMode.HALF_UP)}$RESET ans")
  }
}
