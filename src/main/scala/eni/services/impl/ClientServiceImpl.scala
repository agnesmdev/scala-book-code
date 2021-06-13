package eni.services.impl

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory
import eni.modeles.client.JsonSupport._
import eni.modeles.client.Utilisateur
import eni.services.IClientService

import scala.concurrent.{ExecutionContext, Future}

class ClientServiceImpl extends IClientService {

  private val host: String = ConfigFactory.load().getString("api.placeholder.host")

  override def rechercheUtilisateurs(nom: String)(implicit system: ActorSystem, mat: Materializer, executor: ExecutionContext): Future[List[Utilisateur]] = {
    val request = HttpRequest(uri = s"$host/users")
    Unmarshal(request).to[List[Utilisateur]].map(utilisateurs => {
      utilisateurs.filter(_.name.toLowerCase.contains(nom.toLowerCase))
    })
  }
}
