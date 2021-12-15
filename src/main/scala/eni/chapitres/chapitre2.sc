import java.text.SimpleDateFormat
import java.time._
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.{Calendar, Date, Locale}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/** 2. Variables */
/** 2.1. Définition */

val jour = "Lundi"
/*
jour = "Mardi"
// Error: reassignment to val
 */

var jourMutable = "Lundi"
jourMutable = "Mardi"

/** 2.2. Variable lazy */

lazy val mot = "Aujourd'hui"
mot

lazy val dateCourante = System.currentTimeMillis()
val langueLocale = Locale.getDefault.getLanguage
val dateActuelle = if (langueLocale == "fr") {
  "Temps actuel : " + dateCourante
} else if (langueLocale == "en") {
  "Current time: " + dateCourante
} else {
  "Langage inconnu"
}

/** 2.3. Type d'une variable */

val int = 10
val long: Long = 10

/** 2.4. Structure des types */

val phraseJava = new String("Comment vas-tu ?")
val memePhraseJava = new String("Comment vas-tu ?")

phraseJava == memePhraseJava
phraseJava equals memePhraseJava
phraseJava eq memePhraseJava

val valeurNulle: String = null
valeurNulle == null

/*
valeurNulle equals null
// java.lang.NullPointerException
 */

/** 2.5. Chaîne de caractères */

val texte = "Bonjour"
val charactere = 'a'

texte.length
"".length

texte.isEmpty
"".isEmpty

texte.isBlank
"".isBlank
"    ".isBlank

texte.indexOf("o")
texte.indexOf("our")
texte.indexOf("o", 3)
texte.indexOf("a")
texte.contains("jour")

texte.replace("o", "0")
texte.replace("a", "A")
texte.replaceFirst("o", "0")

texte.split("o")
texte.split("a")

texte.substring(3, 6)
/*
texte.substring(3, 9)
// java.lang.StringIndexOutOfBoundsException: begin 3, end 9, length 7
 */

"   Texte    ".trim

texte.startsWith("Bon")
texte.endsWith("jour")

texte.toLowerCase
texte.toUpperCase

/** 2.5.1. Interpolation de String */

texte + " Jean !"
s"$texte Marie !"
s"${texte.replace("o", "0")} M4r13 !"
s"Date courante : $dateCourante"

/** 2.5.2. Multi-lignes */

val texteLong =
  """Un texte
     très très
     long"""

val texteLongAligne =
  """Un texte
    |très très
    |long""".stripMargin

/** 2.6. Valeurs numériques */
/** 2.6.1. Types */

val octet: Byte = 123 // nombre entier signé de 8 bits
val court: Short = -12345 // nombre entier signé de 16 bits
val entier: Int = 123456789 // nombre entier signé de 32 bits
val entierLong: Long = -123456789 // nombre entier signé de 64 bits
val flottant: Float = 1234.56789f // nombre décimal signé de 32 bits
val double: Double = -12345.6789 // nombre décimal signé de 64 bits

val grandEntier = BigInt(entierLong)
val grandDecimal = BigDecimal(double)

val entierParDefaut = 12
val decimalParDefaut = 123.45

/** 2.6.2. Opérations basiques */

12 + 89.5
198 - 65
25 * 5
val divisionEuclidienne = 15 / 10
val division = 5.0 / 3

import scala.math.BigDecimal.RoundingMode._
BigDecimal(division).setScale(2, UP)
BigDecimal(division).setScale(2, DOWN)
BigDecimal(division).setScale(2, FLOOR)
BigDecimal(division).setScale(2, CEILING)
BigDecimal(division).setScale(2, HALF_UP)
BigDecimal(division).setScale(2, HALF_DOWN)
BigDecimal(division).setScale(2, HALF_EVEN)

/*
BigDecimal(division).setScale(2, UNNECESSARY)
// java.lang.ArithmeticException: Rounding necessary
 */

val divisionMoitie = -155.5 / 10
BigDecimal(divisionMoitie).setScale(1, UP)
BigDecimal(divisionMoitie).setScale(1, DOWN)
BigDecimal(divisionMoitie).setScale(1, FLOOR)
BigDecimal(divisionMoitie).setScale(1, CEILING)
BigDecimal(divisionMoitie).setScale(1, HALF_UP)
BigDecimal(divisionMoitie).setScale(1, HALF_DOWN)
BigDecimal(divisionMoitie).setScale(1, HALF_EVEN)

/*
45 / 0
java.lang.ArithmeticException: / by zero
 */

/** 2.6.3. Opérations mathématiques */

Math.abs(-456)
Math.max(123, 45)
Math.min(98, 145)
Math.pow(2, 4)
Math.round(12.5)
Math.round(12.3)
Math.sqrt(4)
Math.sqrt(5)

/** 2.6.4. Conversion */

octet.toShort
court.toInt
entier.toLong
entierLong.toFloat
flottant.toDouble

double.toInt
grandDecimal.toInt
entier.toShort
grandEntier.toShort

octet.toString
court.toString
entier.toString
entierLong.toString
flottant.toString

/** 2.7. Boolean */

val bonneReponse = true
val mauvaiseReponse = false

bonneReponse & mauvaiseReponse
bonneReponse | mauvaiseReponse

/** 2.8. Éléments temporels */
/** 2.8.1. Period */

val periode = Period.of(2, 5, 27)
s"""${periode.getYears} années,
   |${periode.getMonths} mois,
   |${periode.getDays} jours"""

periode.get(ChronoUnit.DAYS)

val periodeFuture = periode.plusDays(6)
periode.plus(periodeFuture)

val periodePassee = periode.minusYears(2)
periode.minus(periodePassee)

/** 2.8.2. Duration */

val duree = Duration.of(10192927, ChronoUnit.MILLIS)
s"""${duree.getSeconds} secondes,
   |${duree.getNano} nanosecondes"""
s"""${duree.toHours} heures ou
   |${duree.toMinutes} minutes"""

val dureeFuture = duree.plusDays(2)
duree.plus(1, ChronoUnit.HOURS)
duree.plus(dureeFuture)

val dureePassee = duree.minusMillis(252)
duree.minus(3, ChronoUnit.MINUTES)
duree.minus(dureePassee)

/** 2.8.3. Date */

val date = new Date()
val calendrier = Calendar.getInstance()
calendrier.getTime

calendrier.set(2021, 9, 13, 15, 38, 15)
val dateCalendrier = calendrier.getTime

calendrier.set(Calendar.MINUTE, 56)
val dateMillisecondes = calendrier.getTime

calendrier.add(Calendar.HOUR, 2)
val dateAjoutee = calendrier.getTime

dateAjoutee.after(date)
dateCalendrier.before(date)

val formateurDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss")
formateurDate.format(date)

val dateParsee = formateurDate.parse("1993-08-12 08:45:30")

/*
formatDate.parse("1993-08-12 08-45-30")
// java.text.ParseException: Unparseable date: "1993-08-12 08-45-30"
 */

/** 2.8.4. LocalDate */

val dateLocale = LocalDate.now()
val zoneParDefaut = ZoneId.systemDefault()
LocalDate.now(zoneParDefaut)

val dateLocaleAvecZone = LocalDate.now(ZoneId.of("GMT+9"))
ZoneId.getAvailableZoneIds

s"""${dateLocale.getYear} années,
   |${dateLocale.getMonthValue} mois,
   |${dateLocale.getDayOfMonth} jours"""

dateLocale.plusDays(3)
dateLocale.plus(2, ChronoUnit.MONTHS)

dateLocale.minusDays(7)
dateLocale.minus(1, ChronoUnit.YEARS)

val dateLocaleAjoutee = dateLocale
  .plusYears(3)
  .plusMonths(9)
  .plusDays(2)
dateLocale.plus(periode)

val periodeEntreDates = Period.between(dateLocale, dateLocaleAjoutee)

dateLocale.isAfter(dateLocaleAjoutee)
dateLocale.isBefore(dateLocaleAjoutee)

val formateurDateLocale = DateTimeFormatter.ofPattern("yyyy MM dd")
dateLocale.format(formateurDateLocale)
dateLocale.format(DateTimeFormatter.ISO_DATE)

LocalDate.parse("2010-12-25")
/*
LocalDate.parse("2015 01 27")
// java.time.format.DateTimeParseException: Text '2015 01 27' could not be parsed at index 4
 */

/*
LocalDate.parse("1998 11 23 12:08:34")
// java.time.format.DateTimeParseException: Text '1998 11 23 12:08:34' could not be parsed, unparsed text found at index 10
 */

LocalDate.parse("2015 01 27", formateurDateLocale)

/** 2.8.5. LocalDateTime */

val dateTempsLocale = LocalDateTime.now()
val dateTempsLocalAvecZone = LocalDateTime.now(ZoneId.of("GMT+9"))

s"""${dateTempsLocale.getYear} années,
   |${dateTempsLocale.getMonthValue} mois,
   |${dateTempsLocale.getDayOfMonth} jours,
   |${dateTempsLocale.getHour} heures,
   |${dateTempsLocale.getMinute} minutes,
   |${dateTempsLocale.getSecond} secondes"""

dateTempsLocale.plusHours(7)
dateTempsLocale.plus(2, ChronoUnit.SECONDS)

dateTempsLocale.minusNanos(745000)
dateTempsLocale.minus(1, ChronoUnit.MINUTES)

val dateTempsLocaleAjoutee = dateTempsLocale
  .plusHours(5)
  .plusMinutes(35)
  .plusSeconds(50)
dateTempsLocale.plus(duree)
dateTempsLocale.plus(periode)

val dureeEntreDatesTemps = Duration.between(dateTempsLocale, dateTempsLocaleAjoutee)

dateTempsLocale.isAfter(dateTempsLocaleAjoutee)
dateTempsLocale.isBefore(dateTempsLocaleAjoutee)

val formateurDateTempsLocale = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
dateTempsLocale.format(formateurDateTempsLocale)
dateTempsLocale.format(DateTimeFormatter.ISO_DATE_TIME)

LocalDateTime.parse("2010-12-25T15:35:45")
/*
LocalDateTime.parse("2015 01 27 08:23:00")
// java.time.format.DateTimeParseException: Text '2015 01 27 08:23:00' could not be parsed at index 4
 */

/*
LocalDateTime.parse("1998-11-23")
// java.time.format.DateTimeParseException: Text '1998-11-23' could not be parsed, unparsed text found at index 10
 */

LocalDateTime.parse("2021/06/07 02:36:41", formateurDateTempsLocale)

/** 2.9. Collections */
/** 2.9.2. Seq */

val sequence = Seq(1, 2, 3)
val sequencePlusSequence = sequence ++ sequence
sequence diff Seq(2)
sequencePlusSequence.distinct
sequence.reverse

sequence.take(2)
sequence.take(5)
sequence.drop(1)
sequence.drop(4)
sequence.dropRight(2)
sequence.dropRight(6)

val sequenceRemplie = Seq.fill(4)("mot")
val sequenceRemplieMethode = Seq.fill(3)(Random.nextPrintableChar())
val sequencePlage = Seq.range(0, 10)
val sequencePlageDeuxParDeux = Seq.range(0, 10, 2)

/** 2.9.3. List */

val liste = List(1, 2, 3)
val elementPlusListe = 0 :: liste
val listePlusListe = liste ::: liste

/** 2.9.4. Vector */

val vecteur = Vector(1, 2, 3)

/** 2.9.5. Set */

val set = Set(1, 2, 3)
set(0)

val setAvecDoublon = Set(1, 2, 3, 3, 1, 2)
set ++ Set(2, 3, 5)
set & Set(2, 3, 5)

/** 2.9.6. Map */

val map = Map("A" -> 12, "B" -> 4, "C" -> 4)
val mapAvecDoublon = Map("A" -> 12, "B" -> 4, "C" -> 4, "A" -> 1)
val mapVide = Map.empty[String, Int]

map("C")
/*
map("D")
// java.util.NoSuchElementException: key not found: D
 */

map.get("C")
map.get("D")
map.getOrElse("D", -1)
map.contains("A")

map.keys
map.values

val mapPlusCouple = map + ("D" -> 5)
val mapPlusCouples = map + ("D" -> 7, "F" -> 10)
mapPlusCouple ++ mapPlusCouples

/** 2.9.7. ArrayBuffer */

val arrayBuffer = ArrayBuffer(1, 2, 3)
arrayBuffer += 4
arrayBuffer += 5 += 6

arrayBuffer ++= List(7, 8)
arrayBuffer ++= List(9) ++= List(10)

arrayBuffer -= 4
arrayBuffer -= 5 -= 6
arrayBuffer -= 0

arrayBuffer --= List(7, 8)
arrayBuffer --= List(9) --= List(10)
arrayBuffer --= List(0, 3)

arrayBuffer.append(3)
arrayBuffer.append(4, 5, 6)
arrayBuffer

arrayBuffer.remove(4)
arrayBuffer
/*
arrayBuffer.remove(5)
// java.lang.IndexOutOfBoundsException: 5
 */

arrayBuffer.remove(3, 2)
arrayBuffer

/*
arrayBuffer.remove(3, 1)
// java.lang.IndexOutOfBoundsException: at 3 deleting 1
 */

arrayBuffer.prepend(0)
arrayBuffer.prepend(-4, -3)
arrayBuffer

arrayBuffer.insert(2, -2, -1)
arrayBuffer

/*
arrayBuffer.insert(10, 4, 5, 6)
// java.lang.IndexOutOfBoundsException: 10
 */

/** 2.9.8. HashSet */

val hashSet = mutable.HashSet('a', 'b', 'c')
hashSet += 'd'
hashSet += 'e' += 'f'
hashSet += 'a'

hashSet ++= List('g', 'h')
hashSet ++= List('i') ++= List('j')
hashSet ++= List('a', 'k')

hashSet -= 'd'
hashSet -= 'e' -= 'f'
hashSet -= ' '

hashSet --= List('g', 'h')
hashSet --= List('i') --= List('j')
hashSet --= List(' ', 'k')

hashSet.remove('c')
hashSet

hashSet.remove(' ')

/** 2.9.9. HashMap */

val hashMap = mutable.HashMap("bleu" -> 4, "vert" -> 9)
hashMap += ("rouge" -> 2)
hashMap += ("jaune" -> 3) += ("blanc" -> 6)
hashMap += ("rouge" -> 10)

hashMap ++= Map("noir" -> 5, "mauve" -> 1)
hashMap ++= Map("orange" -> 7) ++= Map("rose" -> 0)
hashMap ++= Map("blanc" -> 8, "marron" -> 1)

hashMap -= "blanc"
hashMap -= "orange" -= "noir"
hashMap -= "magenta"

hashMap.remove("mauve")
hashMap
hashMap.remove("kaki")

hashMap.put("rose", 7)
hashMap.put("cyan", 3)
hashMap

/** 2.9.10. Fonctions communes */

sequence
sequence(2)
/*
sequence(3)
java.lang.IndexOutOfBoundsException: 3
  at scala.collection.LinearSeqOptimized.apply(LinearSeqOptimized.scala:67)
  at scala.collection.LinearSeqOptimized.apply$(LinearSeqOptimized.scala:65)
  at scala.collection.immutable.List.apply(List.scala:89)
  ... 37 elided
 */

sequence.length
sequence.isEmpty

sequence.contains(1)
sequence.contains(5)
sequence.contains("un")

sequence.indexOf(1)
sequence.indexOf(5)
sequencePlusSequence.indexOf(2)

val collectionVide = Nil
collectionVide.isEmpty
collectionVide.length

collectionVide.contains(1)
collectionVide.indexOf(2)

/** 2.9.11. Tuple */

val tuple = (1, "deux", '3', true, List(4))
tuple._1
tuple._2
tuple._3
tuple._4
tuple._5

tuple.productIterator.foreach(println)

/** 3. Entrée / Sortie */
/** 3.1. Écrire dans la console */

print("Ceci est : ")
System.out.println("Message d'information")
System.err.println("Message d'erreur")

println(
  s"""${Console.BOLD}Texte gras
     |${Console.BLACK}et bleu
     |${Console.BLUE_B}avec un fond bleu
     |${Console.UNDERLINED}et souligné.
     |${Console.RESET}Retour à la normale.""".stripMargin)

/** 4. Structures de contrôle */
/** 4.1. if/else */

if (bonneReponse && !mauvaiseReponse) {
  "GAGNÉ"
} else if (bonneReponse && mauvaiseReponse) {
  "PRESQUE"
} else {
  "PERDU"
}

val resultat = if (bonneReponse) "GAGNÉ" else "PERDU"

/** 4.2. Structures d'itération */
/** 4.2.1. Structure for */

var nombre = 2
for (i <- 1 to 5) nombre += i
nombre

nombre = 2
for (i <- 1 until 5) nombre += i
nombre

var paragraphe = "Paragraphe : "
val mots = Seq("La", "plage", "est", "bleue")
for (mot <- mots) paragraphe += s"$mot "
paragraphe

val listeNombre = for (i <- 1 to 5) yield i * 4
val listeNombreAleatoire = for (i <- 1 to 5) yield {
  val aleatoire = Random.nextInt(10)
  println(s"Multiplication par $aleatoire")
  i * aleatoire
}
val motsCourts = for (mot <- mots if mot.length < 4) yield mot.toUpperCase
val motsLongs = for (mot <- mots if mot.length > 8) yield mot.toLowerCase

var index = 7

while (index > 0) {
  print(index)
  index -= 1
}

/*
while(true) {
  print(index)
  index -= 1
}
// java.nio.BufferOverflowException
*/

/** 5. Méthodes */
/** 5.1. Définition */

def unArg(i: Int) = i * 1.2
def deuxArg(i: Int, j: Int): Double = i * j
def sansArg() = ()
def deuxArgSepares(i: Int)(j: Int): Double = i * j

listeNombre.sortBy(identity)
listeNombre.sortBy(identity)(Ordering.Int.reverse)

def methodeLongue(i: Int) = {
  val inconnue = Math.random()
  val somme = i + inconnue
  s"Voici le résultat $somme"
}

unArg(5)
deuxArg(25, 1)
deuxArgSepares(25)(1)
sansArg()
methodeLongue(5)

/*
unArg("erreur")
// type mismatch;
// found   : String("erreur")
// required: Int
// unArg("erreur")
*/

unArg(12)

/** 5.3. Argument par défaut */

def argumentParDefaut(i: Int, j: Int = 1) = i * j

argumentParDefaut(12, 2)
argumentParDefaut(12)

/** 6. Classes */
/** 6.1. Création d'une classe */

class Vetement(nom: String)

val short = new Vetement("short")

/** 6.2. Accès aux champs */

class Vetements(val nom: String, var nombre: Int)

val shorts = new Vetements("short", 3)

shorts.nom
shorts.nombre

/*
shorts.nom = "SHORT"
// reassignment to val
 */
shorts.nombre += 2
shorts.nombre

/** 6.3. Champs supplémentaires */

class Maison(val chambres: Int, val lieu: String) {
  private val descriptionCourte = s"Maison à $lieu"
  val description = s"$descriptionCourte avec $chambres chambres"
  var superficie = chambres * 20
}

val maison = new Maison(3, "Nantes")
maison.description
maison.superficie

/*
maison.descriptionCourte
// value descriptionCourte in class Maison cannot be accessed in Maison
 */

maison.superficie = 100
maison.superficie

/** 6.4. Valeurs par défaut */

class Point(val x: Int = 0, val y: Int = 0)

val pointY = new Point(y = 2)
pointY.x
pointY.y

val pointX = new Point(x = 5)
pointX.x
pointX.y

/** 6.5. Méthodes d'une classe */

class Devoir(val note: Int) {
  def estimation(moyenne: Int) = if (note > moyenne) {
    s"$note : Bonne note"
  } else {
    s"$note : Mauvaise note"
  }
}

val devoir = new Devoir(12)
devoir.estimation(10)

/** 6.6. Constructeurs d’une classe */

class Article(val contenu: String) {
  def this(liste: Seq[String]) = this(liste.mkString("\n"))

  def this(titre: String, texte: String) = this(Seq(titre, texte))
}

val article = new Article("Titre\nTexte")
val articleListe = new Article(Seq("Titre", "Texte"))
val articleCouple = new Article("Titre", "Texte")

article.contenu
article.contenu == articleListe.contenu
article.contenu == articleCouple.contenu

article == articleListe
article == articleCouple
articleListe == articleCouple

/** 7. Héritage */
/** 7.1. Définition */

class Appartement(lieu: String) extends Maison(1, lieu)

val appartement = new Appartement("Lyon")
appartement.lieu
appartement.description
appartement.chambres

appartement.isInstanceOf[Appartement]
appartement.isInstanceOf[Maison]

val appartementMaison: Maison = appartement

/*
val maisonAppartement: Appartement = maison
// found   : Maison
// required: Appartement
 */

/** 7.2. Champs / méthodes protected */

class Livre(titre: String, pages: Int) {
  private val couverture = s"- $titre -"
  protected val epaisseur = pages * 10
  val description = s"Livre 1 : $couverture"
}

val livre = new Livre("L'Autre", 356)

/*
livre.epaisseur
// value epaisseur in class Livre cannot be accessed in Livre
// Access to protected value epaisseur not permitted because
// enclosing object is not a subclass of
// class Livre where target is defined
 */

/*
livre.couverture
// value couverture in class Livre cannot be accessed in Livre
 */

livre.description

class Dictionnaire(titre: String, pages: Int) extends Livre(titre, pages) {
  val taille = epaisseur * 10
}

val dictionnaire = new Dictionnaire("Synonymes", 630)
dictionnaire.taille

/** 7.3. Surcharger les méthodes / champs */

class Villa(chambres: Int, lieu: String) extends Maison(chambres, lieu) {
  override val description: String = s"Villa à $lieu avec $chambres chambres"

  override def toString: String = description
}

appartement.toString

val villa = new Villa(5, "Montreuil")
villa.toString

/** 7.4. Surcharger les méthodes / champs */

class Chalet(chambres: Int, lieu: String) extends Maison(chambres, lieu) {
  override final val description: String = s"Chalet à $lieu avec $chambres chambres"

  final def prix(metres: Int): Int = 1000 * metres * chambres
}

/*
class GrandChalet(chambres: Int, lieu: String) extends Chalet(chambres, lieu) {
  override def prix(metres: Int): Int = 2000 * metres * chambres
}
// overriding method prix in class Chalet of type (metres: Int)Int;
// method prix cannot override final member
 */

final class Hotel(chambres: Int, lieu: String) extends Maison(chambres, lieu)

/*
class HotelDeluxe(chambres: Int, lieu: String) extends Hotel(chambres, lieu)
// illegal inheritance from final class Hotel
 */

/** 8. Objets singletons */
/** 8.1. Définition */

object Compteur {
  val nom = "COMPTEUR"
  private var total = 0
  def decompte = total
  def ajouter = total += 1
}

Compteur.ajouter
Compteur.decompte
Compteur.nom
/*
 Compteur.total
// Error: variable total in object Compteur cannot be accessed in object Compteur
 */

Random.nextInt()

import Random.nextDouble
nextDouble()

import Random._
nextLong()

/** 8.2. Objets companions */
/** 8.2.1. Définition */

class Carre(longueur: Int) {
  def aire: Double = Carre.calculerAire(longueur)
}

object Carre {
  private def calculerAire(longueur: Int) = longueur * longueur
}

val carre = new Carre(15)
carre.aire

/*
Carre.calculerAire(15)
// Error: value calculerAire is not a member of object Carre
 */

/** 8.2.2. Création de nouveaux constructeurs */

class Paragraphe(val contenu: String) {
  override def toString: String = contenu
}

object Paragraphe {
  def apply(liste: Seq[String]): Paragraphe = new Paragraphe(liste.mkString("\n"))

  def apply(titre: String, texte: String): Paragraphe = Paragraphe(Seq(titre, texte))

  def unapply(paragraphe: Paragraphe): Option[(String, String)] = {
    val elements = paragraphe.contenu.split("\n")
    if (elements.length < 2) None else Some(elements.head, elements.tail.mkString("\n"))
  }
}

val paragrapheString = new Paragraphe("Titre\nTexte")
val paragrapheListe = Paragraphe(Seq("Titre", "Texte"))
val paragrapheCouple = Paragraphe.apply("Titre", "Texte")

val Paragraphe(titreExtrait, texteExtrait) = paragrapheCouple

val paragrapheSansTitre = new Paragraphe("Texte")
/*
val Paragraphe(titre2, texte2) = paragrapheSansTitre
// scala.MatchError: Texte (of class Paragraphe)
 */
