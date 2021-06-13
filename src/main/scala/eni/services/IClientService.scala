package eni.services

import akka.actor.ActorSystem
import akka.stream.Materializer
import eni.modeles.client.Utilisateur

import scala.concurrent.{ExecutionContext, Future}

trait IClientService {

  def rechercheUtilisateurs(nom: String)(implicit system: ActorSystem, mat: Materializer, executor: ExecutionContext): Future[List[Utilisateur]]
}
