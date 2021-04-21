package eni

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, concat, get, path, pathPrefix, _}
import akka.stream.ActorMaterializer
import eni.exceptions.{MonnaieInsuffisanteException, ProduitIndisponibleException}
import eni.modeles.CommandeJsonSupport._
import eni.modeles.{Commande, Produit}
import eni.services.impl.ProduitServiceImpl

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object MachineApp extends App {

  private val host = "0.0.0.0"
  private val port = 9000
  private val produitService = new ProduitServiceImpl()

  implicit val system: ActorSystem = ActorSystem()
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def routes = concat(
    totalRoute,
    pathPrefix("produits") {
      acheterProduit ~
        tousLesProduitsRoute ~
        // Regex pour récupérer le code du produit
        path("""(.+)""".r) { code =>
          produitRestant(code) ~
            supprimerProduit(code) ~
            ajouterProduit(code)
        }
    }
  )

  def tousLesProduitsRoute = pathEnd {
    get {
      val produits = Produit.tousLesProduits.map(_.code)
      complete(s"Voici la liste des produits achetables : ${produits.mkString(" / ")}")
    }
  }

  def totalRoute = path("total")(
    get {
      complete(s"Montant total : ${produitService.total()}")
    }
  )

  def acheterProduit = pathEnd {
    post {
      entity(as[Commande]) { commande =>
        Produit.parse(commande.code) match {
          case None => complete(StatusCodes.BadRequest -> s"Code ${commande.code} inconnu")
          case Some(produit) => onComplete(produitService.acheterProduit(produit, commande.euros)) {
            case Success(reste) => complete(s"Voici votre monnaie : $reste €")
            case Failure(ex: ProduitIndisponibleException) => complete(StatusCodes.NotFound, ex.getMessage)
            case Failure(ex: MonnaieInsuffisanteException) => complete(StatusCodes.BadRequest, ex.getMessage)
            case Failure(ex: Throwable) => complete(StatusCodes.InternalServerError, ex.getMessage)
          }
        }
      }
    }
  }

  def supprimerProduit(code: String) = pathEnd {
    delete {
      Produit.parse(code) match {
        case None => complete(StatusCodes.BadRequest -> s"Code $code inconnu")
        case Some(produit) =>
          val montant = produitService.supprimerProduit(produit)
          complete(s"Produit $produit retiré de la machine, $montant rapporté grâce à ce produit")
      }
    }
  }

  def ajouterProduit(code: String) = pathEnd {
    put {
      parameters("nombre".as[Int]) { nombre =>
        validate(nombre > 0, s"Impossible d'ajouter $nombre produits, nombre supérieur à 0 demandé") {
          Produit.parse(code) match {
            case None => complete(StatusCodes.BadRequest -> s"Code $code inconnu")
            case Some(produit) =>
              val total = produitService.ajouterProduit(produit, nombre)
              complete(s"$nombre produit(s) $produit ajouté à la machine, $total disponibles")
          }
        }
      }
    }
  }

  def produitRestant(code: String) = pathEnd {
    get {
      Produit.parse(code) match {
        case None => complete(StatusCodes.BadRequest -> s"Code $code inconnu")
        case Some(produit) =>
          val reste = produitService.produitsRestants(produit)
          complete(s"Il reste $reste produits $produit")
      }
    }
  }

  Http()
    .bindAndHandle(routes, host, port)
    .onComplete {
      case Success(_) => println("Machine prête à recevoir des commandes !")
      case Failure(exception) =>
        println(exception.getMessage)
        System.exit(-1)
    }
}
