package eni.services.impl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Authorization, BasicHttpCredentials, RawHeader}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.typesafe.config.ConfigFactory
import eni.modeles.client.{ClientJsonSupport, Evaluation, Utilisateur}
import eni.services.IClientService
import spray.json._

import scala.concurrent.{ExecutionContext, Future}

class ClientServiceImpl(implicit system: ActorSystem, executor: ExecutionContext) extends IClientService with ClientJsonSupport {

  private val hote: String = ConfigFactory.load().getString("api.hote")
  private val utilisateur: String = ConfigFactory.load().getString("api.utilisateur")
  private val motDePasse: String = ConfigFactory.load().getString("api.motDePasse")
  private val http = Http()

  override def rechercheEvaluationsUtilisateur(id: Int): Future[List[Evaluation]] = {
    val request = HttpRequest(
      uri = s"$hote/utilisateurs/$id/evaluations",
      headers = List(Authorization(BasicHttpCredentials(utilisateur, motDePasse)))
    )
    http.singleRequest(request).flatMap {
      case response if response.status != StatusCodes.OK => Future.failed(new Throwable(s"Code ${response.status}"))
      case response => Unmarshal(response).to[List[Evaluation]]
    }.recover {
      case ex => throw new Throwable(s"Problème de connexion à l'hôte : ${ex.getMessage}")
    }
  }

  override def ajoutUtilisateur(utilisateurACreer: Utilisateur): Future[Utilisateur] = {
    val request = HttpRequest(
      uri = s"$hote/utilisateurs",
      method = HttpMethods.POST,
      headers = List(RawHeader("utilisateur", utilisateur), RawHeader("motDePasse", motDePasse)),
      entity = HttpEntity(ContentTypes.`application/json`, utilisateurACreer.toJson.toString())
    )
    http.singleRequest(request).flatMap {
      case response if response.status != StatusCodes.Created => Future.failed(new Throwable(s"Code ${response.status}"))
      case response => Unmarshal(response).to[Utilisateur]
    }.recover {
      case ex => throw new Throwable(s"Problème de connexion à l'hôte : ${ex.getMessage}")
    }
  }
}
