package eni.modeles.client

case class Evaluation(idUtilisateur: Int,
                      idJeu: Int,
                      tempsDeJeu: Duree,
                      note: Double,
                      commentaire: String)
