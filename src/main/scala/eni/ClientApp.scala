package eni

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, concat, parameters, path, _}
import akka.stream.ActorMaterializer
import eni.modeles.client.ClientJsonSupport._
import eni.services.impl.ClientServiceImpl

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object ClientApp extends App {

  private val host = "0.0.0.0"
  private val port = 9100
  private val clientService = new ClientServiceImpl()

  implicit val system: ActorSystem = ActorSystem()
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def routes = concat(
    path("utilisateurs")(
      parameters("nom".as[String]) { nom =>
        onComplete(clientService.rechercheUtilisateurs(nom.toLowerCase)) {
          case Success(utilisateurs) => complete(utilisateurs)
          case Failure(erreur) => complete(
            StatusCodes.BadGateway -> s"Erreur lors de la récupération des utilisateurs contenant le nom '$nom' : ${erreur.getMessage}"
          )
        }
      }
    )
  )

  Http()
    .bindAndHandle(routes, host, port)
    .onComplete {
      case Success(_) => println("Client prêt à recevoir des requêtes !")
      case Failure(exception) =>
        println(exception.getMessage)
        System.exit(-1)
    }
}
