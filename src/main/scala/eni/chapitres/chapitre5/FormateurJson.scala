package eni.chapitres.chapitre5

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object FormateurJson extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val formateurRequete: RootJsonFormat[Requete] = jsonFormat2(Requete)
  implicit val formateurRequeteComplexe: RootJsonFormat[RequeteComplexe] = jsonFormat1(RequeteComplexe)
}
