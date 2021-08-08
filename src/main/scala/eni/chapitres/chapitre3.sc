import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Random, Success, Try}
import java.net.{URI, UnknownHostException}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.matching.Regex
import scala.concurrent.duration._

/** 1. Fonctions */
/** 1.1. Définition */

val fonctionUnArg = (i: Int) => i * 1.2
val fonctionDeuxArg = (i: Int, j: Double) => i * j
val fonctionDeuxArgDecimal: (Int, Double) => BigDecimal = (i: Int, j: Double) => i * j
val fonctionSansArg = () => println("Bonjour")
val fonctionUnArgSansDef: Int => Double = _ * 1.2

/** 1.2. Multi-lignes */

val fonctionLongue = (i: Int) => {
  val inconnue = Math.random()
  val somme = i + inconnue
  s"Voici le résultat $somme"
}

/** 1.3. Utilisation */

fonctionUnArg(5)
fonctionDeuxArg(25, 1.5)
fonctionSansArg()
fonctionLongue(5)

fonctionUnArg.apply(12)
fonctionDeuxArg(67, 3.34)
fonctionSansArg.apply()
fonctionLongue(5)

/** 1.4. Fonctions anonymes */

(i: Int) => 3 * i
"jour".exists(_ == 'a')

/** 1.5. Fonctions en argument */

val doubleFonction = (f: Int => Double, e: Int) => f(e) * 2

doubleFonction(fonctionUnArg, 12)
doubleFonction(_ * 5, 42)
doubleFonction(x => fonctionDeuxArg(x, 3.5), 12)
doubleFonction(fonctionDeuxArg(_, 3.5), 12)

/** 1.6. Fonctions récursives */

val fonctionDivise: (Double, Double) => Double = (nombre: Double, seuil: Double) => {
  if (nombre < seuil) {
    nombre
  } else {
    val moitie = nombre / 2
    fonctionDivise(moitie, seuil)
  }
}

fonctionDivise(12, 0.4)
/*
fonctionDivise(3, 0)
// java.lang.StackOverflowError
 */

/** 2. Fonctions pures */

val exemple = "exemple"
exemple.length
exemple.substring(4)

var animals: ArrayBuffer[String] = ArrayBuffer.empty[String]

def ajouterAnimal(animal: String): ArrayBuffer[String] = {
  animals += animal
}

ajouterAnimal("chat")
ajouterAnimal("chien")

/** 3. Trait */

/** 3.2. Étendre un trait */

trait Animal {
  def nombreDePattes(): Int

  def son(): String

  def imprimeSon(): String = s"L'animal fait ${son()}"
}

class Chien extends Animal {
  def nombreDePattes(): Int = 4

  def son(): String = "Ouaf !"
}

val rex = new Chien
rex.nombreDePattes()
rex.son()
rex.imprimeSon()

class Colley extends Chien {
  override def imprimeSon(): String = "Le chien fait Ouaf !"
}

val lassie = new Colley()
lassie.nombreDePattes()
lassie.son()
lassie.imprimeSon()

/** 3.3. Étendre plusieurs traits */

trait Ami {
  def nom: String
}

class Chat(nomDuChat: String) extends Animal with Ami {
  def nombreDePattes(): Int = 4

  def son(): String = "Miaou !"

  def nom: String = nomDuChat
}

val tom = new Chat("Tom")

tom.nombreDePattes()
tom.son()
tom.imprimeSon()
tom.nom

/** 3.4. Traits scellé */

/*
class Visseuse extends Outil
// illegal inheritance from sealed trait Outil
 */

/** 3.4. Classes abstraites */

abstract class ClasseAmi(nom: String)

class ClasseChat(val nom: String) extends ClasseAmi(nom)

val classeTom = new ClasseChat("tom")

class Labrador(nomDuChien: String) extends Chien with Ami {
  def nom: String = nomDuChien
}

/** 4. Classes abstraites */

/** 4.1. Définition */

abstract class ClasseCouleur

case class CouleurPleine(nom: String) extends ClasseCouleur

val couleur = CouleurPleine("rouge")
couleur.nom

println(couleur)

class CouleurMonochrome(nom: String) extends ClasseCouleur

val couleurMonochrome = new CouleurMonochrome("gris")

println(couleurMonochrome)

couleur == CouleurPleine("rouge")
couleurMonochrome == new CouleurMonochrome("gris")

/** 4.2. Méthode copy */

val couleurBlue = couleur.copy(nom = "bleu")

/*
// SOLUTION EXERCICE 1

abstract class ClasseCouleur {
  def denomination: String
}

case class CouleurPleine(nom: String) extends ClasseCouleur {
  def denomination: String = nom
}
 */

/** 5. Case object */

sealed trait Couleur

sealed trait CouleurBasique extends Couleur

case object Bleu extends CouleurBasique

case object Rouge extends CouleurBasique

case object Blanc extends CouleurBasique

case object Jaune extends CouleurBasique

case object Noir extends CouleurBasique

println(Bleu)

/** 6. Pattern matching */

/** 6.2. Pattern constructeurs */

case class Mixte(couleurA: CouleurBasique, couleurB: CouleurBasique) extends Couleur

def extraireMixte(couleur: Couleur) = couleur match {
  case Mixte(Blanc, Noir) => print("mixte spécial")
  case Mixte(_, _) => print("mixte normal")
}

extraireMixte(Mixte(Blanc, Noir))
extraireMixte(Mixte(Bleu, Rouge))
/*
extraireMixte(Bleu)
// scala.MatchError: Bleu (of class Bleu$)
 */

def extraireMixteExplicite(couleur: Couleur) = couleur match {
  case Mixte(Blanc, Noir) => print("mixte spécial")
  case Mixte(couleurA, couleurB) => print(s"mixte de $couleurA et $couleurB")
  case Bleu => print(s"$Bleu n'est pas un mixte")
  case Rouge => print(s"$Rouge n'est pas un mixte")
  case Blanc => print(s"$Blanc n'est pas un mixte")
  case Jaune => print(s"$Jaune n'est pas un mixte")
  case Noir => print(s"$Noir n'est pas un mixte")
}

extraireMixteExplicite(Noir)

def extraireMixteSimplifie(couleur: Couleur) = couleur match {
  case Mixte(Blanc, Noir) => print("mixte spécial")
  case Mixte(couleurA, couleurB) => print(s"mixte de $couleurA et $couleurB")
  case Bleu | Rouge | Blanc | Jaune | Noir => print(s"$couleur n'est pas un mixte")
}

extraireMixteSimplifie(Rouge)

/** 6.3. Pattern par défaut */

def extraireMixteDefaut(couleur: Couleur) = couleur match {
  case Mixte(Blanc, _) => print("mixte spécial")
  case Mixte(_, _) => print("mixte normal")
  case _ => print(s"$couleur n'est pas un mixte")
}

extraireMixteDefaut(Mixte(Blanc, Rouge))
extraireMixteDefaut(Mixte(Bleu, Rouge))
extraireMixteDefaut(Jaune)

/** 6.4. Pattern séquences */

def demoSequence(valeur: Any) = valeur match {
  case Seq(0, _, _) => "3 éléments"
  case Seq(0, _*) => "n éléments"
  case _ => "inconnu"
}

demoSequence(Seq(0, 1, 2))
demoSequence(Seq(0, 1, 2, 3, 4))
demoSequence(Nil)

/** 6.5. Pattern n-uplets */

def demoTuple(valeur: Any) = valeur match {
  case (a, b, c) => s"3-uplet: $a, $b, $c"
  case (a, b, c, d) => s"4-uplet: $a, $b, $c, $d"
  case _ => "inconnu"
}

demoTuple((1, "test", Nil))
demoTuple((1, "test", Nil, true))
demoTuple((1, "test"))

/** 6.6. Pattern typé */

def demoType(valeur: Any) = valeur match {
  case s: String => s.length
  case m: Map[_, _] => m.size
  case _ => -1
}

demoType("string")
demoType(Map(1 -> true, 2 -> false))
demoType(1)

def verifierInt(valeur: Any) = valeur match {
  case _: Map[Int, Int] => "map int"
  case _: Seq[Int] => "séquence int"
  case _ => "inconnu"
}

verifierInt(Map(1 -> 12, 2 -> 24))
verifierInt(Map(1 -> "un", 2 -> "deux"))
verifierInt(Seq(1, 2))
verifierInt(Seq("un", "deux"))

def verifierArrayInt(valeur: Any) = valeur match {
  case _: Array[Int] => "array int"
  case _ => "inconnu"
}

verifierArrayInt(Array(1))
verifierArrayInt(Array(""))

/** 6.7. Pattern avec condition */

/*
def simplifierMixte(couleur: Mixte) = couleur match {
   case Mixte(couleurA, couleurA) => couleurA
   case Mixte(_, _) => couleur
}
// Error: couleurA is already defined as value couleurA
// case Mixte(couleurA, couleurA) => couleurA
 */

def simplifierMixte(couleur: Mixte) = couleur match {
  case Mixte(couleurA, couleurB) if couleurA == couleurB => couleurA
  case Mixte(_, _) => couleur
}

simplifierMixte(Mixte(Blanc, Blanc))
simplifierMixte(Mixte(Bleu, Rouge))

/** 7. Option */
/** 7.1. Définition */

val animauxMap = Map("chat" -> 4)
animauxMap.get("chat")
animauxMap.get("chien")

val optionDefinie = Some(12)
optionDefinie.isDefined
optionDefinie.isEmpty
optionDefinie.get

/*
None.get
// java.util.NoSuchElementException: None.get
 */
None.getOrElse("défaut")

/** 7.2. Pattern matching */

def recupererAnimaux(animal: String) = animauxMap.get(animal) match {
  case Some(number) => number
  case None => -1
}

recupererAnimaux("chat")
recupererAnimaux("chien")

def demoOptionType(valeur: Any) = valeur match {
  case _: Option[Int] => "nombre"
  case _: Option[String] => "phrase"
  case _ => "autre"
}

demoOptionType(Some(34))
demoOptionType(Some("Lorem Ipsum"))

/** 7.3. Bonnes pratiques */

val valeurNulle: String = null
val optionNulle = Option(valeurNulle)

/** 8. Expressions régulières */

val motDePasse = "M0tD3p4ss3"
val regex = new Regex("\\d\\w")

regex.findFirstIn(motDePasse)
val occurrences = regex.findAllIn(motDePasse)
occurrences.toList
regex.replaceFirstIn(motDePasse, " ")
regex.replaceFirstIn("MotDePasse", " ")
regex.replaceAllIn(motDePasse, " ")
regex.replaceAllIn("MotDePasse", " ")

val regexQuestion = """Combien de (\w+) a-t-(il|elle) \? (\d+)""".r

def extraireReponse(phrase: String) = phrase match {
  case regexQuestion(element, personne, nombre) => s"${personne.capitalize} a $nombre $element"
  case _ => "Pas de réponse"
}

extraireReponse("Combien de tables a-t-il ? 6")
extraireReponse("Combien de tableaux a-t-elle ? 2")
extraireReponse("Combien de casques audio a-t-elle ? 5")

/** 9. Fonctions communes aux collections */

val nombres = List(2, 3, 5, 8, 13, 21)
val singleton = List(65)
val listeVide: List[Int] = Nil

/** 9.1. Accès aux éléments d'une collection */
/** 9.1.1. head */

nombres.head
/*
listeVide.head
// java.util.NoSuchElementException: head of empty list
 */

nombres.headOption
listeVide.headOption

/** 9.1.2. tail */

nombres.tail
singleton.tail
/*
Nil.tail
// java.lang.UnsupportedOperationException: tail of empty list
 */

def queue(sequence: List[Any]) = sequence match {
  case _ :: tail => tail
  case _ => Nil
}

queue(nombres)
queue(Nil)

/** 9.1.3. exists */

nombres.exists(_ < 5)
listeVide.exists(_ < 5)

/** 9.1.4. find */

nombres.find(_ < 10)
nombres.find(_ > 30)
listeVide.find(_ > 30)

/** 9.2. Transformation de collection */
/** 9.2.1. map */

nombres.map(_ * 2)
nombres.map(_.toString)
listeVide.map(_.toString)

/** 9.2.2. flatMap */

def diviseurs(nombre: Int) = {
  (2 until nombre).filter(i => nombre % i == 0)
}
val diviseursNombres = nombres.map(diviseurs)
nombres.flatMap(diviseurs)

diviseursNombres.flatten

/** 9.2.3. filter */

nombres.filter(_ < 10)
nombres.filter(_ > 30)
listeVide.filter(_ > 30)

/** 9.2.4. collect */

nombres.collect {
  case nombre if nombre < 10 => nombre * 1.5
  case nombre if nombre > 20 => nombre / 2
}

/** 9.2.5. takeWhile */

nombres.takeWhile(_ % 2 == 0)
nombres.takeWhile(_ % 3 == 0)
listeVide.takeWhile(_ % 3 == 0)

/** 9.2.6. dropWhile */

nombres.dropWhile(_ % 2 == 0)
nombres.dropWhile(_ % 3 == 0)
listeVide.dropWhile(_ % 3 == 0)

/** 9.2.7. sorted */

val mots = List("crayon", "livre", "coquelicot", "ane", "joue")
mots.sorted
nombres.sorted

val dates = List(
  LocalDate.parse("2020-03-01"),
  LocalDate.parse("1993-09-29"),
  LocalDate.parse("2021-05-12")
)
/*
dates.sorted
// No implicit Ordering defined for java.time.LocalDate.
// dates.sorted
 */
implicit val ordreDate: Ordering[LocalDate] = Ordering.by(_.toEpochDay)
dates.sorted

/** 9.2.8. sortBy */

mots.sortBy(_.length)
dates.sortBy(_.toEpochDay)

/** 9.2.9. sortWith */

mots.sortWith((mot1, mot2) => mot1.last > mot2.last)

/** 9.2.10. zipWithIndex */

val motsIndexes = mots.zipWithIndex
motsIndexes.map {
  case (mot, index) if index % 2 == 0 => mot.reverse
  case (mot, _) => mot
}

/** 9.2.11. grouped */

val motsGroupes = mots.grouped(2)
motsGroupes.toList
mots.grouped(6).toList

/** 9.2.12. groupBy */

mots.groupBy(_.length)

/** 9.3. Opération sur une collection */
/** 9.3.1. foreach */

nombres.foreach(print(_))
listeVide.foreach(print(_))

/** 9.3.2. mkString */

nombres.mkString(" - ")
singleton.mkString(" - ")
nombres.mkString("(", " - ", ")")
listeVide.mkString("(", " - ", ")")
nombres.mkString
listeVide.mkString

/** 9.3.3. forall */

nombres.forall(_ < 5)
listeVide.forall(_ < 5)

/** 9.3.4. fold */

nombres.fold(0) {
  case (resultat, nombre) if nombre % 2 == 0 => resultat + nombre
  case (resultat, _) => resultat
}

/** 9.3.5. foldLeft / foldRight */

nombres.foldLeft("") {
  case (resultat, nombre) if nombre % 2 == 1 => resultat ++ nombre.toString
  case (resultat, _) => resultat
}

nombres.foldRight("") {
  case (nombre, resultat) if nombre % 2 == 1 => resultat ++ nombre.toString
  case (_, resultat) => resultat
}

nombres.reverse.foldRight("") {
  case (nombre, resultat) if nombre % 2 == 1 => resultat ++ nombre.toString
  case (_, resultat) => resultat
}

/** 9.3.6. sum */

nombres.sum

/** 10. Gestion des erreurs */
/** 10.1. try / catch / finally */

def imprimeSite(url: String) = {
  var contenuDuSite = ""
  try {
    contenuDuSite = Source.fromURL(URI.create(url).toURL).mkString
  } catch {
    case _: IllegalArgumentException => println("URL incorrecte")
    case _: UnknownHostException => println("Site inconnu")
  } finally {
    println(s"Contenu du site $url : $contenuDuSite")
  }
}

imprimeSite("http://www.perdu.com/")
imprimeSite("http://siteinconnu.fr")
imprimeSite("siteinconnu")

/*
imprimeSite(null)
// java.lang.NullPointerException
 */

/** 10.2. Option */

/*
"string".toInt
// java.lang.NumberFormatException: For input string: "string"
 */

def stringEnInt(s: String): Option[Int] = {
  try {
    Some(s.toInt)
  } catch {
    case _: Throwable => None
  }
}

stringEnInt("string")

/** 10.3. Either */

def racineCarree(i: Int): Either[String, Int] = {
  Math.sqrt(i) match {
    case c if c.isValidInt => Right(c.toInt)
    case _ => Left(s"$i n'est pas un carré")
  }
}

racineCarree(16)
racineCarree(12)

def imprimeRacineCarree(i: Int) = {
  racineCarree(i) match {
    case Left(erreur) => println(s"Erreur : $erreur")
    case Right(c) => println(s"Résultat : $c")
  }
}

imprimeRacineCarree(16)
imprimeRacineCarree(12)

/** 10.4. Try */

def stringEnDouble(s: String): Try[Double] = {
  Try(s.toDouble)
}

/*
"1 5".toDouble
java.lang.NumberFormatException: For input string: "1 5"
 */

stringEnDouble("1.5")
stringEnDouble("1 5")

def imprimeStringEnDouble(s: String) = {
  stringEnDouble(s) match {
    case Failure(erreur) => println(s"Erreur : $erreur")
    case Success(d) => println(s"Résultat : $d")
  }
}

imprimeStringEnDouble("1.5")
imprimeStringEnDouble("1 5")

/** 10.5. For compréhension */
/** 10.5.1. Option */

def sommeStringEnInt(s: String, t: String): Option[Int] = {
  for {
    i <- stringEnInt(s)
    j <- stringEnInt(t)
  } yield {
    i + j
  }
}

sommeStringEnInt("1", "3")
sommeStringEnInt("1", "trois")

/** 10.5.2. Collection */

def sommeStringEtInt(listeString: List[String], listeInt: List[Int]) = {
  for {
    i <- listeInt
    s <- listeString
  } yield {
    s"$i : $s"
  }
}

val listeString = List("Un", "Deux", "Trois")
val listeInt = List(1, 2)
sommeStringEtInt(listeString, listeInt)

def stringParTaille(listeString: List[String], listeInt: List[Int]) = {
  for {
    i <- listeInt
    s <- listeString if s.length == i
  } yield {
    s"$i : $s"
  }
}

val tailles = List(2, 4, 5)
stringParTaille(listeString, tailles)
stringParTaille(listeString, listeVide)
stringParTaille(Nil, tailles)

/** 10.5.3. Either */

def sommeRacineCarree(i: Int, j: Int): Either[String, Int] = {
  for {
    c <- racineCarree(i)
    d <- racineCarree(j)
  } yield {
    c + d
  }
}

sommeRacineCarree(1, 16)
sommeRacineCarree(1, 3)

/** 10.5.4. Try */

def sommeStringEnDouble(s: String, t: String): Try[Double] = {
  for {
    i <- stringEnDouble(s)
    j <- stringEnDouble(t)
  } yield {
    i + j
  }
}

sommeStringEnDouble("1.5", "3.9")
sommeStringEnDouble("1.5", "3 9")

/** 11. Future */
/** 11.1. Définition */

implicit val executor: ExecutionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

def tacheLongue(): Future[Int] = Future {
  Thread.sleep(2000)
  Random.nextInt(10)
}

val resultat = tacheLongue()
Await.result(resultat, 2.seconds)

/*
Await.result(tacheLongue(), 1.seconds)
// java.util.concurrent.TimeoutException: Futures timed out after [1 second]
 */

val futureSucces = Future.successful("Succès !")
val futureEchec = Future.failed(new Exception("Échec..."))

/*
Future.successful(throw new Exception("Échec..."))
// java.lang.Exception: Échec...
 */

/** 11.2. Fonctions liées aux Future */
/** 11.2.1. andThen */

def imprimeFuture(future: Future[Any]) = future.andThen {
  case Success(resultat) => println(s"Succès : $resultat")
  case Failure(e) => println(s"Échec : $e")
}

imprimeFuture(futureSucces)
imprimeFuture(futureEchec)

/** 11.2.2. recover */

def recupereErreur(future: Future[String]) = future.recover {
  case _: NullPointerException => throw new Exception("Erreur !")
  case e => s"Erreur générique : ${e.getMessage}"
}

val recupereSucces = recupereErreur(futureSucces)
val recupereErreurClassique = recupereErreur(futureEchec)

val futureEchecNpe = Future.failed(new NullPointerException())
val recupereErreurNpe = recupereErreur(futureEchecNpe)

recupereSucces
recupereErreurClassique
recupereErreurNpe

/** 11.2.3. map */

val resultatDouble = resultat.map(_ * 2)
resultatDouble

/** 11.3. Pattern matching */

val tacheTriple = for {
  x <- tacheLongue()
  y <- tacheLongue()
  z <- tacheLongue()
} yield {
  x + y + z
}

Thread.sleep(30000)
tacheTriple

/** 12. Implicites */
/** 12.1. Paramètres implicites */

val liste = List("blanc", "azur", "pourpre", "violet")
liste.sorted

implicit val ordre: Ordering[String] = Ordering.by(_.length)
liste.sorted

liste.sorted(Ordering.String)

/** 12.2. Conversions implicites */
/** 12.2.1. Changer le type d'un élément */

val i: Long = 1
/*
val j: Int = 1L
// // Type mismatch
 */

implicit def longVersInt(i: Long): Int = i.toInt
val j: Int = 1L

/** 12.2.2. Utiliser une méthode d'un autre type */

val k: Int = 100000

/*
k.length
// Error: value length is not a member of Int
 */

implicit def intVersString(i: Int): String = i.toString
k.length

/** 12.3. Classes implicites */

val paire = (123, 345)
/*
paire.min
// Error: value min is not a member of (Int, Int)
 */

implicit class MinInt(paire: (Int, Int)) {
  def min: Int = Math.min(paire._1, paire._2)
}

paire.min

/** 12.4. Règles */
/** 12.4.1. Le mot clé implicit */

def stringVersInt(s: String): Int = s.toInt
/*
val s: Int = "123"
// Error: type mismatch;
 */

/** 12.4.2. La portée d’un implicit */

1.seconds

/** 12.4.3. Un implicit par conversion */

/*
implicit val ordreInverse: Ordering[String] = ordre.reverse
liste.sorted
// Error: ambiguous implicit values:
// both value ordre of type => Ordering[String]
// and value ordreInverse of type => Ordering[String]
// match expected type scala.math.Ordering[String]
 */

/** 12.4.4. Une seule conversion à la fois */

/*
val m: String = 123L
// Error:(37, 17) type mismatch;
// found   : Long(123L)
// required: String
 */

def bonjour(implicit s: String): String = s"Bonjour $s"
implicit val nombre: Int = 123

/** 12.4.5. Priorité aux non-implicites */

implicit val s: String = "Marie"
bonjour
bonjour("Jean")
