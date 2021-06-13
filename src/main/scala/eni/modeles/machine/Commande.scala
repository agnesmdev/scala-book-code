package eni.modeles.machine

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Commande(code: String, euros: Double)

object CommandeJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val PortofolioFormats: RootJsonFormat[Commande] = jsonFormat2(Commande)
}
