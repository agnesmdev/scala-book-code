import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Random, Success}

/**
 * Définir une méthode calculLong qui génère un nombre aléatoire inférieur à 200 et le renvoie s’il est inférieur à 100
 * ou envoie une exception dans le cas inverse. Définir une autre méthode qui exécute la méthode calculLong deux fois et
 * qui imprime la multiplication des deux nombres obtenus en cas de succès ou un message d’erreur en cas d’échec.
 */
def calculLong(): Future[Int] = {
  val nombre = Random.nextInt(200)
  if (nombre > 100) Future.failed(new Exception(s"Nombre $nombre > 100"))
  else Future.successful(nombre)
}

def multiplicationCalculLong(): Future[Int] = {
  val resultat = for {
    x <- calculLong()
    y <- calculLong()
  } yield {
    x * y
  }
  resultat.andThen {
    case Success(nombre) => println(s"Succès : $nombre")
    case Failure(exception) => println(s"Échec : ${exception.getMessage}")
  }
}