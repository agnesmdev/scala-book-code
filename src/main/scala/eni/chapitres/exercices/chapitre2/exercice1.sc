/**
 * Créer deux variables :
 * - une correspondant à votre date de naissance
 * - une correspondant à la date actuelle
 * et écrire dans une String votre âge.
 */
import java.time.{LocalDate, Period}
val dateDeNaissance = LocalDate.of(1993, 9, 29)
val age = Period.between(dateDeNaissance, LocalDate.now())
s"J'ai ${age.getYears} ans et ${age.getMonths} mois !"