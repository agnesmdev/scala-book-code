import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
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

val texteLong =
  """Un texte
                   très très
                   long"""

val texteLongAligne =
  """Un texte
    |très très
    |long""".stripMargin

texte + " Jean !"
s"$texte Marie !"
s"${texte.replace("o", "0")} M4r13 !"
s"Aujourd'hui : $date"

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
 */

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

unArg (12)

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

devoir.toString

class Telephone(numero: String) {
  override def toString = numero.grouped(2).toSeq.mkString(" ")
}

val telephone = new Telephone("0612974028")
telephone.toString

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

/** 6.7. Héritage */

class Appartement(lieu: String) extends Maison(1, lieu)
val appartement = new Appartement("lieu")
appartement.description

appartement.isInstanceOf[Appartement]
appartement.isInstanceOf[Maison]

/** 6.8. Visibilité des champs */

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

/** 7. Objets singletons */
/** 7.1. Définition */

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

case class Carre(longueur: Int) {
  def aire: Double = Carre.calculerAire(longueur)
}

object Carre {
  private def calculerAire(longueur: Int) = longueur * longueur
}

val carre = Carre(15)
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
val Paragraphe(titre2, texte2) = paragrapheSansTitre