package eni.modeles.client

import spray.json._

object JsonSupport extends DefaultJsonProtocol {
  implicit object UserJsonFormat extends RootJsonFormat[Utilisateur] {
    def write(c: Utilisateur): JsObject = JsObject(
      "id" -> JsNumber(c.id),
      "nom" -> JsString(c.name),
      "email" -> JsString(c.email),
      "telephone" -> JsNumber(c.phone)
    )

    def read(value: JsValue): Utilisateur = jsonFormat4(Utilisateur).read(value);
  }
}
