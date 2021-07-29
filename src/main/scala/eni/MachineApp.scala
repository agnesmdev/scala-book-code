package eni

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, concat, get, path, pathPrefix, _}
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler, ValidationRejection}
import akka.stream.ActorMaterializer
import eni.exceptions.{CodeInconnuException, MonnaieInsuffisanteException, ProduitIndisponibleException}
import eni.modeles.machine.MachineJsonSupport._
import eni.modeles.machine.{Commande, Produit}
import eni.services.impl.ProduitServiceImpl
import spray.json.DeserializationException

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object MachineApp extends App {

  private val host = "0.0.0.0"
  private val port = 9000
  private val produitService = new ProduitServiceImpl()

  val gestionnaireRejets: RejectionHandler = RejectionHandler.newBuilder()
    .handle {
      case ValidationRejection(message, _) => complete(StatusCodes.BadRequest, message)
    }
    .handleNotFound(complete((StatusCodes.NotFound, "Opération inconnue")))
    .result()

  val gestionnaireExceptions: ExceptionHandler = ExceptionHandler {
    case ex: ProduitIndisponibleException => complete(StatusCodes.NotFound, ex.getMessage)
    case ex@(_: MonnaieInsuffisanteException | _: CodeInconnuException | _: DeserializationException) =>
      complete(StatusCodes.BadRequest, ex.getMessage)
    case ex: Exception =>
      extractUri { uri =>
        complete(StatusCodes.InternalServerError, s"Erreur lors de l'appel à $uri : ${ex.getMessage}")
      }
  }

  implicit val system: ActorSystem = ActorSystem("serveur-app")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val totalRoute = path("total")(
    get {
      complete(s"Montant total : ${produitService.total()}")
    }
  )

  val tousLesProduitsRoute = pathEnd {
    get {
      complete(StatusCodes.OK, Produit.tousLesProduits)
    }
  }

  val acheterProduitRoute = pathEnd {
    post {
      entity(as[Commande]) { commande =>
        onComplete(produitService.acheterProduit(commande.produit, commande.euros)) {
          case Success(reste) => complete(s"Voici votre monnaie : $reste €")
          case Failure(ex) => failWith(ex)
        }
      }
    }
  }

  def supprimerProduitRoute(code: String) = pathEnd {
    delete {
      Produit.parse(code) match {
        case None => reject(ValidationRejection(s"Code $code inconnu"))
        case Some(produit) =>
          val montant = produitService.supprimerProduit(produit)
          complete(s"Produit $produit retiré de la machine, $montant rapporté grâce à ce produit")
      }
    }
  }

  def ajouterProduitRoute(code: String) = pathEnd {
    put {
      parameters("nombre".as[Int]) { nombre =>
        validate(nombre > 0, s"Impossible d'ajouter $nombre produits, nombre supérieur à 0 demandé") {
          Produit.parse(code) match {
            case None => reject(ValidationRejection(s"Code $code inconnu"))
            case Some(produit) =>
              val total = produitService.ajouterProduit(produit, nombre)
              complete(s"$nombre produit(s) $produit ajouté à la machine, $total disponibles")
          }
        }
      }
    }
  }

  def produitRestantRoute(code: String) = pathEnd {
    get {
      Produit.parse(code) match {
        case None => reject(ValidationRejection(s"Code $code inconnu"))
        case Some(produit) => complete(produitService.produitsRestants(produit))
      }
    }
  }

  val routes = handleRejections(gestionnaireRejets)(
    handleExceptions(gestionnaireExceptions)(
      concat(
        totalRoute,
        pathPrefix("produits") {
          tousLesProduitsRoute ~
            acheterProduitRoute ~
            // Regex pour récupérer le code du produit
            path("""(.+)""".r) { code =>
              produitRestantRoute(code) ~
                supprimerProduitRoute(code) ~
                ajouterProduitRoute(code)
            }
        }
      )
    )
  )


  Http()
    .newServerAt(host, port)
    .bind(routes)
    .onComplete {
      case Success(_) => println("Machine prête à recevoir des commandes !")
      case Failure(exception) =>
        println(exception.getMessage)
        System.exit(-1)
    }
}
