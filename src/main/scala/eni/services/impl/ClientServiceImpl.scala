package eni.services.impl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory
import eni.modeles.client.ClientJsonSupport._
import eni.modeles.client.Utilisateur
import eni.services.IClientService
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

class ClientServiceImpl extends IClientService {

  private val host: String = ConfigFactory.load().getString("api.placeholder.host")
  private val logger = LoggerFactory.getLogger(getClass.getName)

  override def rechercheUtilisateurs(nom: String)(implicit system: ActorSystem, mat: Materializer, executor: ExecutionContext): Future[List[Utilisateur]] = {
    logger.debug(s"Recherche d'utilisateurs contenant le nom '$nom' vers le host '$host'")
    val request = HttpRequest(uri = s"$host/users")
    Http().singleRequest(request).flatMap {
        case response if response.status != StatusCodes.OK => Future.failed(new Throwable(s"Code ${response.status}"))
        case response =>
          val futureUtilisateurs = Unmarshal(response).to[List[Utilisateur]]
          futureUtilisateurs.map(_.filter(_.name.toLowerCase.contains(nom)))
      }
  }
}
