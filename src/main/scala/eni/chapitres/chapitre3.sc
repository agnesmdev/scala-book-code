import java.util.concurrent.Executors
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Random, Success, Try}

/** 1. Fonctions */
/** 1.1. Définition */

val fonctionUnArg = (i: Int) => i * 1.2
val fonctionDeuxArg = (i: Int, j: Double) => i * j
val fonctionDeuxArgDecimal: (Int, Double) => BigDecimal = (i: Int, j: Double) => i * j
val fonctionSansArg = () => println("Bonjour")

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

import java.net.{URI, UnknownHostException}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

var animals: ArrayBuffer[String] = ArrayBuffer.empty[String]

def addAnimal(animal: String): ArrayBuffer[String] = {
  animals += animal
}

addAnimal("chat")
addAnimal("chien")

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

case class CouleurPleine(name: String) extends ClasseCouleur

val couleur = CouleurPleine("rouge")
couleur.name

println(couleur)

class CouleurMonochrome(name: String) extends ClasseCouleur

val couleurMonochrome = new CouleurMonochrome("gris")

println(couleurMonochrome)

couleur == CouleurPleine("rouge")
couleurMonochrome == new CouleurMonochrome("gris")

/** 4.2. Méthode copy */

val couleurBlue = couleur.copy(name = "bleu")

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

/*
// SOLUTION EXERCICE 2

def tombola(value: Any) = value match {
  case "BINGO" => true
  case s: String if s.length > 5 => true
  case s: String => false
  case i: Int if i % 2 == 0 => true
  case i: Int => false
  case Seq(_, _, _) => true
  case l: Seq[Any] => false
  case (a, b) => true
  case _ => false
}

// version simplifiée
def tombola(value: Any) = value match {
  case "BINGO" => true
  case s: String if s.length > 5 => true
  case i: Int if i % 2 == 0 => true
  case Seq(_, _, _) => true
  case _ => false
}
 */

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

/** 8. Fonctions communes aux séquences */

val nombres = Seq(2, 3, 5, 8, 13, 21)
val singleton = Seq(65)
val listeVide: Seq[Int] = Nil

/** 8.1. map */

nombres.map(_ * 2)
nombres.map(_.toString)
listeVide.map(_.toString)

/** 8.2. exists */

nombres.exists(_ < 10)
listeVide.exists(_ < 10)

/** 8.3. filter */

nombres.filter(_ < 10)
nombres.filter(_ > 30)
listeVide.filter(_ > 30)

/** 8.4. foreach */

nombres.foreach(print(_))
listeVide.foreach(print(_))

/** 8.5. mkString */

nombres.mkString(" - ")
singleton.mkString(" - ")
listeVide.mkString

/** 8.6. head */

nombres.head
/*
listeVide.head
// java.util.NoSuchElementException: head of empty list
 */

nombres.headOption
listeVide.headOption

/** 8.7. tail */

nombres.tail
singleton.tail
/*
Nil.tail
// java.lang.UnsupportedOperationException: tail of empty list
 */

def queue(sequence: Seq[Any]) = sequence match {
  case _ :: tail => tail
  case _ => Nil
}

queue(nombres)
queue(Nil)

/** 8.8. take / takeWhite */

nombres.take(2)
nombres.take(10)
listeVide.take(2)

nombres.takeWhile(_ % 2 == 0)
nombres.takeWhile(_ % 3 == 0)
listeVide.takeWhile(_ % 3 == 0)

/*
// SOLUTION EXERCICE 3

def transforme(l: Seq[Int]): String = {
  l.filter(_ > 0).take(6).map(_ / 5).takeWhile(_ < 1) match {
    case Seq(_) => "UN"
    case l => l.length.toString
  }
}
 */

/** 9. Gestion des erreurs */
/** 9.1. try / catch / finally */

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

/** 9.2. Option */

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

/** 9.3. Either */

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

/** 9.4. Try */

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

/** 9.5. For compréhension */
/** 9.5.1. Option */

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

/** 9.5.2. Either */

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

/** 9.5.3. Try */

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

/** 9.5.3. List */

def sommeStringEtInt(listeString: Seq[String], listeInt: Seq[Int]) = {
  for {
    i <- listeInt
    s <- listeString
  } yield {
    s"$i : $s"
  }
}

val listeString = Seq("Un", "Deux", "Trois")
val listeInt = Seq(1, 2)
sommeStringEtInt(listeString, listeInt)

def stringParTaille(listeString: Seq[String], listeInt: Seq[Int]) = {
  for {
    i <- listeInt
    s <- listeString if s.length == i
  } yield {
    s"$i : $s"
  }
}

val tailles = Seq(2, 4, 5)
stringParTaille(listeString, tailles)
stringParTaille(listeString, listeVide)
stringParTaille(Nil, tailles)

/** 10. Future */
/** 10.1. Définition */

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

/** 10.2. Fonctions liées aux Future */
/** 10.2.1. andThen */

def imprimeFuture(future: Future[Any]) = future.andThen {
  case Success(resultat) => println(s"Succès : $resultat")
  case Failure(e) => println(s"Échec : $e")
}

val imprimeSucces = imprimeFuture(futureSucces)
val imprimeEchec = imprimeFuture(futureEchec)

imprimeSucces
imprimeEchec

/** 10.2.2. recover */

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

/** 10.2.3. map */

val resultatDouble = resultat.map(_ * 2)
resultatDouble

/** 10.2. Pattern matching */

val tacheTriple = for {
  x <- tacheLongue()
  y <- tacheLongue()
  z <- tacheLongue()
} yield {
  x + y + z
}

Thread.sleep(30000)
tacheTriple