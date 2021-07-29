package eni.modeles.machine

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

object MachineJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val formateurProduit: RootJsonFormat[Produit] = new RootJsonFormat[Produit] {
    def write(p: Produit): JsObject = JsObject(
      "prix" -> JsNumber(p.prix),
      "code" -> JsString(p.code),
      "quantiteInitiale" -> JsNumber(p.quantiteInitiale)
    )

    def read(value: JsValue): Produit = value.asJsObject.getFields("code") match {
      case Seq(JsString(code)) => Produit.parse(code).getOrElse(throw DeserializationException(s"Code inconnu $code"))
      case _ => throw DeserializationException("Code attendu")
    }
  }

  implicit val formateurCommande: RootJsonFormat[Commande] = jsonFormat2(Commande)
  implicit val formateurEtatProduit: RootJsonFormat[EtatProduit] = jsonFormat2(EtatProduit)
}
