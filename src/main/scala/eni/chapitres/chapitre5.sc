import spray.json._

case class Requete(id: String, nombre: Int)
case class RequeteComplexe(requetes: List[Requete])

object FormateurJson extends DefaultJsonProtocol {
  implicit val formateurRequete: RootJsonFormat[Requete] = jsonFormat2(Requete)
  implicit val formateurRequeteComplexe: RootJsonFormat[RequeteComplexe] = jsonFormat1(RequeteComplexe)
}

val requete = Requete("123", 2)

import FormateurJson._

val jsonDeserialise = requete.toJson
jsonDeserialise.prettyPrint
jsonDeserialise.toString

val json = """{"id": "abc", "nombre": 12}""".parseJson
/*
"""{id: "abc", nombre: 12}""".parseJson
// spray.json.JsonParser$ParsingException: Unexpected character 'i' at input index 1 (line 1, position 2), expected '"':
 */

json.convertTo[Requete]
/*
"""{"id": 1234, "nombre": 12}""".parseJson.convertTo[Requete]
// spray.json.DeserializationException: Expected String as JsString, but got 1234
 */

val requeteComplexe = RequeteComplexe(List(requete))
requeteComplexe.toJson.toString
"""{"requetes": [{"id": "abc", "nombre": 12}]}""".parseJson.convertTo[RequeteComplexe]