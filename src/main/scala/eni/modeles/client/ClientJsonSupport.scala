package eni.modeles.client

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

object ClientJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object UserJsonFormat extends RootJsonFormat[Utilisateur] {
    def write(c: Utilisateur): JsObject = JsObject(
      "id" -> JsNumber(c.id),
      "nom" -> JsString(c.name),
      "email" -> JsString(c.email),
      "telephone" -> JsString(c.phone)
    )

    def read(value: JsValue): Utilisateur = jsonFormat4(Utilisateur).read(value);
  }
}
