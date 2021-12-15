package eni

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, concat, _}
import eni.modeles.client.{ClientJsonSupport, Utilisateur}
import eni.services.impl.ClientServiceImpl

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object ClientApp extends App with ClientJsonSupport {

  implicit val system: ActorSystem = ActorSystem("client-app")
  implicit val executor: ExecutionContext = system.dispatcher

  private val host = "0.0.0.0"
  private val port = 9100
  private val clientService = new ClientServiceImpl()

  def recuperationEvaluationsUtilisateur(id: Int) = get {
    onComplete(clientService.rechercheEvaluationsUtilisateur(id)) {
      case Success(utilisateurs) => complete(utilisateurs)
      case Failure(erreur) => complete(
        StatusCodes.BadGateway -> s"Erreur lors de la récupération des évaluations de utilisateur '$id' : ${erreur.getMessage}"
      )
    }
  }

  val creationUtilisateur = post {
    entity(as[Utilisateur]) { utilisateur =>
      onComplete(clientService.ajoutUtilisateur(utilisateur)) {
        case Success(utilisateur) => complete(StatusCodes.Created, utilisateur)
        case Failure(erreur) => complete(
          StatusCodes.BadGateway -> s"Erreur lors de la création d'un utilisateur nommé '${utilisateur.nom}' : ${erreur.getMessage}"
        )
      }
    }
  }

  def routes = concat(
    pathPrefix("utilisateurs") {
      pathEnd {
        creationUtilisateur
      } ~ path(IntNumber) { id =>
        recuperationEvaluationsUtilisateur(id)
      }
    }
  )

  Http()
    .newServerAt(host, port)
    .bind(routes)
    .onComplete {
      case Success(_) => println("Client prêt à recevoir des requêtes !")
      case Failure(exception) =>
        println(exception.getMessage)
        System.exit(-1)
    }
}
