import com.typesafe.config.ConfigFactory
import eni.modeles.Test
import io.circe.config.syntax._
import io.circe.generic.auto._
import org.slf4j.LoggerFactory

import scala.concurrent.duration.FiniteDuration

/** 3.5. Configuration d’une application */

val configuration = ConfigFactory.load()
configuration.getString("variable.texte")
configuration.getInt("variable.nombre")
configuration.getDouble("variable.decimal")
configuration.getDuration("variable.duree")
configuration.getBoolean("variable.booleen")
val objet = configuration.getObject("variable.objet")
val valeurObjet = objet.get("valeur")
val valeurObjetString = valeurObjet.render()
valeurObjetString.toLong

case class Objet(valeur: Long)

case class Variable(texte: String,
                    nombre: Int,
                    decimal: Double,
                    duree: FiniteDuration,
                    booleen: Boolean,
                    objet: Objet)

val variable = configuration.getConfig("variable")
variable.as[Variable]

/*
configuration.getString("inconnu")
// com.typesafe.config.ConfigException$Missing: [...] No configuration setting found for key 'inconnu'
 */

/*
configuration.getInt("variable.texte")
// com.typesafe.config.ConfigException$WrongType: [...] variable.texte has type STRING rather than NUMBER
 */

variable.as[Objet]

/** 3.6. Variables d’environnement */

sys.env
sys.env.get("USER")
sys.env.get("UTILISATEUR")
sys.env.getOrElse("UTILISATEUR", "Rien")

scala.util.Properties.envOrElse("USER", "Rien")
scala.util.Properties.envOrElse("UTILISATEUR", "Rien")
scala.util.Properties.envOrNone("UTILISATEUR")

val env = System.getenv()
env.get("USER")
env.get("UTILISATEUR")

System.getenv("USER")
System.getenv("UTILISATEUR")

/** 3.7. Écriture de log */

val logger = LoggerFactory.getLogger("TEST")

logger.error("Erreur")
logger.warn("Avertissement")
logger.info("Information")
logger.debug("Debug")
logger.trace("Trace")

val test = Test("Rapide", 1)