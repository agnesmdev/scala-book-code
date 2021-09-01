package eni.modeles.client

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait ClientJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object FormateurDuree extends RootJsonFormat[Duree] {
    override def write(d: Duree): JsValue = JsString(s"${d.heures}:${"%02d".format(d.minutes)}")

    override def read(json: JsValue): Duree = json match {
      case JsString(duree) => try {
        val Array(heures, minutes) = duree.split(":").map(_.toInt)
        Duree(heures, minutes)
      } catch {
        case _: Exception => deserializationError("Mauvais format de durée")
      }
      case _ => deserializationError("Durée attendue")
    }
  }


  implicit object FormateurUtilisateur extends RootJsonFormat[Utilisateur] {
    override def write(u: Utilisateur): JsValue = JsObject(
      "id" -> JsNumber(u.id),
      "nom" -> JsString(u.nom),
      "prenom" -> JsString(u.prenom),
      "age" -> JsNumber(u.age),
      "email" -> JsString(u.email)
    )

    override def read(value: JsValue): Utilisateur = value.asJsObject.getFields("nom", "prenom", "age", "email") match {
      case Seq(JsString(nom), JsString(prenom), JsNumber(age), JsString(email)) =>
        val id = value.asJsObject.getFields("id") match {
          case Seq(JsNumber(id)) => id.intValue()
          case _ => 0
        }

        Utilisateur(id, nom, prenom, age.intValue(), email)
      case _ => throw DeserializationException("Mauvais format d'utilisateur")
    }
  }

  implicit val formateurEvaluation: RootJsonFormat[Evaluation] = jsonFormat5(Evaluation)
}
