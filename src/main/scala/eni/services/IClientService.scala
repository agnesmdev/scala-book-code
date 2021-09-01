package eni.services

import eni.modeles.client.{Evaluation, Utilisateur}

import scala.concurrent.Future

trait IClientService {

  def rechercheEvaluationsUtilisateur(id: Int): Future[List[Evaluation]]

  def ajoutUtilisateur(utilisateur: Utilisateur): Future[Utilisateur]
}
