package eni

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{AttributeKey, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{concat, path, _}
import akka.http.scaladsl.server._
import eni.chapitres.chapitre5.FormateurJson._
import eni.chapitres.chapitre5.Requete

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Random, Success}

object ServeurApp extends App {

  private val host = "0.0.0.0"
  private val port = 9000

  implicit val system: ActorSystem = ActorSystem("serveur-app")
  implicit val executor: ExecutionContext = system.dispatcher

  val route: Route = { contexte =>
    contexte.complete("Complétion de la route")
  }

  val routeInaccessible: Route = {
    complete("Cette route est inaccessible")
  }

  val routeExtraite = path("extraite") {
    extractUri { uri =>
      complete(s"Complétion de la route $uri")
    }
  }

  val routeChemin = path("chemin") {
    complete("Complétion de la route /chemin")
  }

  val routeCheminCompose = path("chemin" / "compose") {
    complete("Complétion de la route /chemin/compose")
  }

  val routeCheminNombre = path("chemin" / IntNumber) { nombre =>
    complete(s"Complétion de la route /chemin/$nombre")
  }

  val routeCheminSegment = path("chemin" / Segment) { segment =>
    complete(s"Complétion de la route /chemin/$segment")
  }

  val routeCheminReste = path("chemin" / Remaining) { reste =>
    complete(s"Complétion de la route /chemin/$reste")
  }

  val routesImbriquees = pathPrefix("autreChemin") {
    concat(
      path("compose") {
        complete("Complétion de la route /autreChemin/compose")
      },
      path(IntNumber) { nombre =>
        complete(s"Complétion de la route /autreChemin/$nombre")
      },
      path(Segment) { segment =>
        complete(s"Complétion de la route /autreChemin/$segment")
      },
      pathEnd {
        complete("Complétion de la route /autreChemin")
      }
    )
  }

  val routeComplete = path("complete") {
    complete(HttpResponse(status = StatusCodes.Accepted, entity = "Complétion de la route /complete"))
  }

  val routeSansCorps = path("sansCorps") {
    complete(StatusCodes.NoContent)
  }

  val routeStatutCorps = path("statutCorps") {
    complete(StatusCodes.Created -> "Complétion de la route /statutCorps")
  }

  def pileOuFace: Future[String] = {
    val nombreAleatoire = Random.nextInt()
    if (nombreAleatoire % 2 == 0) Future.successful("Succès")
    else Future.failed(new Throwable("Échec"))
  }

  val routeFuture = path("future") {
    onComplete(pileOuFace) {
      case Success(message) => complete(StatusCodes.OK -> message)
      case Failure(erreur) => complete(StatusCodes.BadGateway -> erreur.getMessage)
    }
  }

  val routeMethode = path("methode") {
    get {
      complete("Complétion de la route avec la méthode GET")
    } ~
      post {
        complete("Complétion de la route avec la méthode POST")
      } ~
      put {
        complete("Complétion de la route avec la méthode PUT")
      } ~
      patch {
        complete("Complétion de la route avec la méthode PATCH")
      } ~
      delete {
        complete("Complétion de la route avec la méthode DELETE")
      }
  }

  val routeCorps = path("corps") {
    post {
      entity(as[Requete]) { requete =>
        complete(StatusCodes.Accepted, s"Requête ${requete.id} avec ${requete.nombre} demande(s) acceptée(s)")
      }
    }
  }

  val routeCorpsReponse = path("corpsReponse" / Segment) { id =>
    get {
      complete(StatusCodes.OK, Requete(id, 0))
    }
  }

  val routeDivision = path("division" / IntNumber) { nombre =>
    try {
      val division = 100 / nombre
      complete(s"Complétion de la route /division/$nombre : $division")
    } catch {
      case _: ArithmeticException => complete(StatusCodes.BadRequest -> "Division par zéro impossible")
    }
  }

  val gestionnaireErreur: ExceptionHandler = ExceptionHandler {
    case _: ArithmeticException =>
      extractUri { uri =>
        System.err.println(s"La requête $uri s'est terminée avec une erreur ${StatusCodes.BadRequest}")
        complete(StatusCodes.BadRequest -> "Division par zéro impossible")
      }
  }

  val routeDivisionGeree = path("divisionGeree" / IntNumber) { nombre =>
    handleExceptions(gestionnaireErreur) {
      val division = 100 / nombre
      complete(s"Complétion de la route /division/$nombre : $division")
    }
  }

  val routeInverse = path("inverse" / IntNumber) { nombre =>
    if (nombre == 0) failWith(new ArithmeticException())
    else complete(s"Complétion de la route /inverse/$nombre : ${1.0f / nombre}")
  }

  val routeRejet = path("rejet") {
    reject
  }

  val routeRejetValidation = path("rejet" / "validation") {
    reject(ValidationRejection("Rejet de la route"))
  }

  val gestionnaireRejet = RejectionHandler
    .newBuilder()
    .handle {
      case ValidationRejection(message, _) => extractUri { uri =>
        System.err.println(s"La requête $uri s'est terminée avec une erreur ${StatusCodes.Conflict}")
        complete(StatusCodes.Conflict, s"Erreur de validation : $message")
      }
    }
    .handleNotFound {
      extractUri { uri =>
        System.err.println(s"La route $uri n'est pas gérée")
        complete(StatusCodes.NotFound, s"La route $uri n'est pas gérée")
      }
    }
    .handleAll[Rejection] { rejets =>
      extractUri { uri =>
        val typeRejets = rejets.map(_.getClass.getSimpleName).distinct.mkString("/")
        System.err.println(s"Une erreur $typeRejets s'est produite sur la route $uri")
        complete(StatusCodes.ImATeapot, s"Une erreur $typeRejets s'est produite sur la route $uri")
      }
    }
    .result()

  val routeRejetValidationGeree = path("rejetGere" / "validation") {
    handleRejections(gestionnaireRejet) {
      reject(ValidationRejection("Rejet de la route"))
    }
  }

  val routeRejetAutre = path("rejet" / "autre") {
    reject(MissingAttributeRejection(AttributeKey("autre")))
  }

  val routes: Route = handleExceptions(gestionnaireErreur) {
    handleRejections(gestionnaireRejet) {
      concat(
        routeCheminCompose,
        routeCheminNombre,
        routeCheminSegment,
        routeCheminReste,
        routeChemin,
        routeExtraite,
        routesImbriquees,
        routeComplete,
        routeSansCorps,
        routeStatutCorps,
        routeFuture,
        routeMethode,
        routeCorps,
        routeCorpsReponse,
        routeDivision,
        routeInverse,
        routeRejet,
        routeRejetValidation,
        routeRejetAutre
        //routeDivisionGeree,
        //routeRejetValidationGeree,
        //route,
        //routeInaccessible
      )
    }
  }

  Http()
    .newServerAt(host, port)
    .bind(routes)
    .onComplete {
      case Success(_) => println("Serveur disponible au port 9000")
      case Failure(exception) =>
        println(exception.getMessage)
        System.exit(-1)
    }
}
