import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
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

lazy val mot = "Aujourd'hui"
mot

lazy val date = LocalDateTime.now()
val langueLocale = Locale.getDefault.getLanguage
val bonjourNom = if (langueLocale == "fr") {
  "Aujourd'hui : " + date.format(DateTimeFormatter.ofPattern("DD-MM-YYYY HH:mm"))
} else if (langueLocale == "en") {
  "Today: " + date.format(DateTimeFormatter.ofPattern("YYYY-MM-DD h:mm a"))
} else {
  "Langage inconnu"
}

/** 2.2. Type d'une variable */

val int = 10
val long: Long = 10

/** 2.3. Types primitifs */

val texte = "Bonjour"
val charactere = 'a'

texte.length
texte.replace("o", "0")
texte.endsWith("jour")

texte + " Jean !"
s"$texte Marie !"
s"${texte.replace("o", "0")} M4r13 !"
s"Aujourd'hui : $date"

val texteLong =
  """Un texte
     très très
     long"""

val texteLongAligne =
  """Un texte
    |très très
    |long""".stripMargin

val octet: Byte = 123 // nombre entier signé de 8 bits
val court: Short = -12345 // nombre entier signé de 16 bits
val entier: Int = 123456789 // nombre entier signé de 32 bits
val entierLong: Long = -123456789 // nombre entier signé de 64 bits
val flottant: Float = 1234.56789f // nombre décimal signé de 32 bits
val double: Double = -12345.6789 // nombre décimal signé de 64 bits

val grandEntier = BigInt(entierLong)
val grandDecimal = BigDecimal(double)

octet.toShort
court.toInt
entier.toLong
entierLong.toFloat
flottant.toDouble

double.toInt
grandDecimal.toInt
entier.toShort
grandEntier.toShort

val entierParDefaut = 12
val decimalParDefaut = 123.45

val bonneReponse = true
val mauvaiseReponse = false

bonneReponse & mauvaiseReponse
bonneReponse | mauvaiseReponse

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
  ... 37 elided
 */

/** 2.4. Collections */
/** 2.4.2. Seq */

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

/** 2.4.3. List */

val liste = List(1, 2, 3)
val elementPlusListe = 0 :: liste
val listePlusListe = liste ::: liste

/** 2.4.1.2. Vector */

val vecteur = Vector(1, 2, 3)

/** 2.4.4. Vector */

val vecteur = Vector(1, 2, 3)

/** 2.4.5. Set */

val set = Set(1, 2, 3)
set(0)

val setAvecDoublon = Set(1, 2, 3, 3, 1, 2)
set ++ Set(2, 3, 5)
set & Set(2, 3, 5)

/** 2.4.6. Map */

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

/** 2.4.7. ArrayBuffer */

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

/** 2.4.8. HashSet */

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

/** 2.4.9. HashMap */

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
hashMap.remove("kaki")
hashMap

hashMap.update("vert", 8)
hashMap.update("lavande", 2)
hashMap

hashMap.put("rose", 7)
hashMap.put("cyan", 3)
hashMap

/** 2.4.10. Fonctions communes */

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

/** 2.4.11. Tuple */

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
  val description = s"Maison à $lieu avec $chambres chambres"
  var superficie = chambres * 20
}

val maison = new Maison(3, "Nantes")
maison.description
maison.superficie

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

/** 7. Héritage */
/** 7.1. Définition */

class Appartement(lieu: String) extends Maison(1, lieu)

val appartement = new Appartement("Lyon")
appartement.lieu
appartement.description

appartement.isInstanceOf[Appartement]
appartement.isInstanceOf[Maison]

val appartementMaison: Maison = appartement

/*
val maisonAppartement: Appartement = maison
// found   : Maison
// required: Appartement
// val maisonAppartement: Appartement = maison
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

/*
// SOLUTION EXERCICE 1

class Trilogie(livre1: String, livre2: String, livre3: String, pages: Int) extends Livre(s"$livre1 - $livre2 - $livre3", pages * 3) {
  override val epaisseur: Int = pages * 30
}
 */

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

Math.min(1, 5)

/*import Math.sqrt
sqrt(16)

import Math._
max(90, 23)*/

class Carre(longueur: Int) {
  def aire: Double = Carre.calculerAire(longueur)
}

object Carre {
  private def calculerAire(longueur: Int) = longueur * longueur
}

val carre = new Carre(15)
carre.aire

// Carre.calculerAire(15)

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
