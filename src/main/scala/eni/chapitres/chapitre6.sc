import slick.jdbc.H2Profile.api._

import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

/** 2. Installation */

val db = Database.forConfig("h2memoire")
implicit val executor: ExecutionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

/** 3. Tables */
/** 3.1. Définition */

class Utilisateurs(tag: Tag) extends Table[(Int, String, Option[String], Int, String)](tag, "UTILISATEURS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def nom = column[String]("NOM", O.Length(20, varying = true))
  def prenom = column[Option[String]]("PRENOM")
  def age = column[Int]("AGE", O.Default(18))
  def email = column[String]("EMAIL", O.Unique)
  def * = (id, nom, prenom, age, email)
}
val utilisateurs = TableQuery[Utilisateurs]

/** 3.2. Initialisation */

val initialisation = db.run(DBIO.seq(
  utilisateurs.schema.create,
  utilisateurs += (0, "Dubois", Some("Dimitri"), 29, "ddubois@test.fr"),
  utilisateurs += (0, "Mercier", None, 72, "lmercier@test.fr"),
  utilisateurs += (0, "Polka", Some("Marie"), 65, "mpolka@test.fr")
))
Await.result(initialisation, Duration.Inf)

/** 4. Requêtes */
/** 4.1. Récupération */

println(utilisateurs.result.statements.mkString("\n"))
val recuperation = db.run(utilisateurs.result).map { result =>
  println(s"${result.length} utilisateurs enregistrés")
  result.foreach(println)
}
Await.result(recuperation, Duration.Inf)

println(utilisateurs.map(_.email).result.statements.mkString("\n"))
val recuperationChamps = db.run(utilisateurs.map(_.email).result).map { result =>
  println("Emails des utilisateurs")
  result.foreach(println)
}
Await.result(recuperationChamps, Duration.Inf)

println(utilisateurs.filter(_.age > 40).result.statements.mkString("\n"))
val recuperationFiltre = db
  .run(utilisateurs.filter(_.age > 40).result)
  .map { result =>
    println(s"${result.length} utilisateurs d'âge supérieur à 40")
    result.foreach(println)
  }
Await.result(recuperationFiltre, Duration.Inf)

val recuperationInconnu = db
  .run(utilisateurs.filter(_.prenom === "Louise").result)
  .map { result =>
    println("Utilisateurs avec le nom Louise")
    println(result)
  }
Await.result(recuperationInconnu, Duration.Inf)

println(utilisateurs.sortBy(_.age).result.statements.mkString("\n"))
val recuperationOrdonnee = db
  .run(utilisateurs.sortBy(_.age).result)
  .map { result =>
    println("Utilisateurs ordonnés par âge")
    result.foreach(println)
  }
Await.result(recuperationOrdonnee, Duration.Inf)

println(utilisateurs.take(2).result.statements.mkString("\n"))
val recuperationLimite = db
  .run(utilisateurs.take(2).result)
  .map { result =>
    println("Récupération des deux premiers utilisateurs")
    result.foreach(println)
  }
Await.result(recuperationLimite, Duration.Inf)

println(utilisateurs.drop(2).result.statements.mkString("\n"))
val recuperationOffset = db
  .run(utilisateurs.drop(2).result)
  .map { result =>
    println("Récupération de tous les utilisateurs sauf les deux premiers")
    result.foreach(println)
  }
Await.result(recuperationOffset, Duration.Inf)

/* EXERCICE 1 : Créer une requête pour récupérer uniquement les emails des utilisateurs dont l’âge est inférieur à 70
triés par nom de taille croissante.

  println(utilisateurs.filter(_.age < 70).sortBy(_.nom.length).map(_.email).result.statements.mkString("\n"))
  // select "EMAIL" from "UTILISATEURS" where "AGE" < 70 order by length("NOM")

  val requete = db
    .run(utilisateurs.filter(_.age < 70).sortBy(_.nom.length).map(_.email).result)
    .map { result => println(result) }
  // Vector(mpolka@test.fr, ddubois@test.fr)
 */

/** 4.2. Insertion */

val requeteInsertionMultiple = utilisateurs ++= Seq(
  (0, "Noël", Some("Maël"), 12, "mnoel@test.fr"),
  (0, "Julian", Some("Henrietta"), 43, "hjulian@test.fr"),
  (0, "Gallam", None, 17, "kgallam@test.fr")
)
println(requeteInsertionMultiple.statements.mkString("\n"))
val insertionMultiple = db
  .run(requeteInsertionMultiple)
  .flatMap { result =>
    println(s"$result utilisateurs ont été créés")
    db.run(utilisateurs.filter(_.id >= 4).result)
  }
  .map { result =>
    println("Utilisateur 4, 5 et 6 créés")
    result.foreach(println)
  }
Await.result(insertionMultiple, Duration.Inf)

/*
// val insertionEchec = db.run(utilisateurs += (0, "Henriette", Some("Julian"), 35, "hjulian@test.fr"))
// Await.result(insertionEchec, Duration.Inf)
// org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_4 ON PUBLIC.UTILISATEURS(EMAIL) VALUES 5"; SQL statement:
// insert into "UTILISATEURS" ("NOM","PRENOM","AGE","EMAIL")  values (?,?,?,?) [23505-200]
 */

/** 4.3. Update */

val age = utilisateurs.filter(_.id === 3).map(_.age)
val actionMiseAJour = age.update(62)
println(actionMiseAJour.statements.mkString("\n"))
val miseAJour = db
  .run(actionMiseAJour)
  .flatMap { result =>
    println(s"$result lignes ont été modifiées")
    db.run(utilisateurs.filter(_.id === 3).result)
  }
  .map { result =>
    println("Utilisateur 3 mis à jour")
    println(result.head)
  }
Await.result(miseAJour, Duration.Inf)

val ageEtPrenom = utilisateurs.filter(_.id === 3).map(u => (u.age, u.prenom))
val actionMiseAJourChamps = ageEtPrenom.update((28, Some("Dominique")))
println(actionMiseAJourChamps.statements.mkString("\n"))
val miseAJourChamps = db
  .run(actionMiseAJourChamps)
  .flatMap { result =>
    println(s"$result lignes ont été modifiées")
    db.run(utilisateurs.filter(_.id === 1).result)
  }
  .map { result =>
    println("Utilisateur 1 mis à jour")
    println(result.head)
  }
Await.result(miseAJourChamps, Duration.Inf)

val mineurs = utilisateurs.filter(_.age < 18).map(_.age)
val actionMiseAJourMultiple = mineurs.update(18)
val miseAJourMultiple = db
  .run(actionMiseAJourMultiple)
  .flatMap { result =>
    println(s"$result lignes ont été modifiées")
    db.run(utilisateurs.filter(_.age === 18).result)
  }
  .map { result =>
    println("Utilisateurs 4 et 6 mis à jour")
    result.foreach(println)
  }
Await.result(miseAJourMultiple, Duration.Inf)

/*
val email = utilisateurs.filter(_.id === 6).map(_.email)
val miseAJourInterdite = db.run(email.update("lmercier@test.fr"))
Await.result(miseAJourInterdite, Duration.Inf)
// org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_4 ON PUBLIC.UTILISATEURS(EMAIL) VALUES 2"; SQL statement:
// update "UTILISATEURS" set "EMAIL" = ? where "UTILISATEURS"."ID" = 6 [23505-200]
 */

/* EXERCICE 2 : Créer une requête pour modifier tous les paramètres de l’utilisateur avec l’identifiant 4.

  val requete = utilisateurs.filter(_.id === 4)
  val miseAJour = db
    .run(requete.update((4, "Loris", Some("Louis"), 12, "lloris@test.fr")))
    .flatMap(_ => db.run(requete.result))
    .map(println)
  // Vector((4,Loris,Some(Louis),12,lloris@test.fr))
 */

/** 4.4. Upsert */

val upsertAjouter = utilisateurs.insertOrUpdate((7, "Pantin", Some("Louise"), 37, "lpantin@test.fr"))
println(upsertAjouter.statements.mkString("\n"))
val ajouterOuModifier = db
  .run(upsertAjouter)
  .flatMap { result =>
    println(s"$result lignes ont été modifiées")
    db.run(utilisateurs.filter(_.id === 7).result)
  }
  .map { result =>
    println("Utilisateur 7 ajouté")
    println(result.head)
  }
Await.result(ajouterOuModifier, Duration.Inf)

val ajouterOuModifierExistant = db
  .run(utilisateurs.insertOrUpdate((3, "Polko", Some("Marie"), 62, "mpolko@test.fr")))
  .flatMap { result =>
    println(s"$result lignes ont été modifiées")
    db.run(utilisateurs.filter(_.id === 3).result)
  }
  .map { result =>
    println("Utilisateur 3 mis à jour")
    println(result.head)
  }
Await.result(ajouterOuModifierExistant, Duration.Inf)

/*
val ajouterOuModifierErreur = db.run(utilisateurs.insertOrUpdate((85, "Henriette", "Julian", 35, "hjulian@test.fr")))
Await.result(ajouterOuModifierErreur, Duration.Inf)
// org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_4 ON PUBLIC.UTILISATEURS(EMAIL) VALUES 5"; SQL statement:
 */

/** 4.5. Delete */

val utilisateurASupprimer = utilisateurs.filter(_.id === 2)
val actionSuppression = utilisateurASupprimer.delete
println(actionSuppression.statements.mkString("\n"))
val suppression = db
  .run(actionSuppression)
  .flatMap { result =>
    println(s"$result lignes ont été supprimées")
    db.run(utilisateurs.filter(_.id === 2).result)
  }
  .map { result =>
    println("Utilisateur 2 supprimé")
    println(result.headOption)
  }
Await.result(suppression, Duration.Inf)

val utilisateursAges = utilisateurs.filter(_.age > 40)
val actionSuppressionMultiple = utilisateursAges.delete
val suppressionMultiple = db
  .run(actionSuppressionMultiple)
  .map { result =>
    println(s"$result lignes ont été supprimées")
  }
Await.result(suppressionMultiple, Duration.Inf)

val utilisateurInconnu = utilisateurs.filter(_.id === 25)
val actionSuppressionInconnu = utilisateurInconnu.delete
val suppressionInconnu = db
  .run(actionSuppressionInconnu)
  .map { result =>
    println(s"$result lignes ont été supprimées")
  }
Await.result(suppressionInconnu, Duration.Inf)

/** 4.6. Contraintes */
/** 4.6.1. Clé étrangère */

class Evaluations(tag: Tag) extends Table[(Int, Double)](tag, "EVALUATIONS") {
  def idUtilisateur = column[Int]("ID_UTILISATEUR")
  def note = column[Double]("NOTE")
  def utilisateur = foreignKey("FK_EVALUATION", idUtilisateur, utilisateurs)(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.Cascade)
  def * = (idUtilisateur, note)
}
val evaluations = TableQuery[Evaluations]

val creationEvaluations = db.run(DBIO.seq(
  evaluations.schema.create,
  evaluations += (1, 5.8),
  evaluations += (1, 7.5),
  evaluations += (6, 5.8)
))
Await.result(creationEvaluations, Duration.Inf)

val suppressionUtilisateur = utilisateurs.filter(_.id === 6)
val actionSuppressionUtilisateur = db
  .run(suppressionUtilisateur.delete)
  .flatMap(_ => db.run(evaluations.filter(_.idUtilisateur === 6).result))
  .map(result => println(s"${result.length} évaluations pour l'utilisateur 6"))
Await.result(actionSuppressionUtilisateur, Duration.Inf)

class Livres(tag: Tag) extends Table[(Int, String)](tag, "LIVRES") {
  def idUtilisateur = column[Int]("ID_UTILISATEUR")
  def nom = column[String]("NOM")
  def utilisateur = foreignKey("FK_LIVRE", idUtilisateur, utilisateurs)(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.Restrict)
  def * = (idUtilisateur, nom)
}
val livres = TableQuery[Livres]

val creationLivres = db.run(DBIO.seq(
  livres.schema.create,
  livres += (1, "Lire en dormant"),
  livres += (1, "Pourquoi je mens ?"),
  livres += (1, "Au lecteur éclairé")
))
Await.result(creationLivres, Duration.Inf)

/*
val actionSuppressionImpossible = db.run(utilisateurs.filter(_.id === 1).delete)
Await.result(actionSuppressionImpossible, Duration.Inf)
// org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Referential integrity constraint violation: "FK_LIVRE: PUBLIC.LIVRES FOREIGN KEY(ID_UTILISATEUR) REFERENCES PUBLIC.UTILISATEURS(ID) (1)"; SQL statement:
// delete from "UTILISATEURS" where "UTILISATEURS"."ID" = 1 [23503-200]
 */

val utilisateurApresSuppression = db
  .run(utilisateurs.filter(_.id === 1).result)
  .map(utilisateur => println(s"Utilisateur 1 toujours présent : $utilisateur"))
Await.result(utilisateurApresSuppression, Duration.Inf)

val livresApresSuppression = db
  .run(livres.filter(_.idUtilisateur === 1).result)
  .map(livres => println(s"Livres de l'utilisateur 1 toujours présents : ${livres.length}"))
Await.result(livresApresSuppression, Duration.Inf)

/** 4.6.2. Index */

class Visites(tag: Tag) extends Table[(String, LocalDate)](tag, "VISITES") {
  def lieu = column[String]("LIEU")
  def date = column[LocalDate]("DATE")
  def indexVisite = index("index_visite", (lieu, date), unique = true)
  def * = (lieu, date)
}
val visites = TableQuery[Visites]

val creationVisites = db.run(DBIO.seq(
  visites.schema.create,
  visites += ("Zoo", LocalDate.of(2021, 1, 1)),
  visites += ("Zoo", LocalDate.of(2021, 5, 23)),
  visites += ("Musée", LocalDate.of(2021, 1, 1)),
))
Await.result(creationVisites, Duration.Inf)

/*
val ajoutIndexImpossible = db.run(visites += ("Zoo", LocalDate.of(2021, 5, 23)))
Await.result(ajoutIndexImpossible, Duration.Inf)
// org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Unique index or primary key violation: "PUBLIC.index_visite ON PUBLIC.VISITES(LIEU, DATE) VALUES 2"; SQL statement:
// insert into "VISITES" ("LIEU","DATE")  values (?,?) [23505-200]
 */

/** 4.7. Aggrégations */

val agesUtilisateurs = utilisateurs.map(_.age)
class Vide(tag: Tag) extends Table[(Option[Int], String)](tag, "VIDE") {
  def valeur = column[Option[Int]]("VALEUR")
  def nom = column[String]("NOM")
  def * = (valeur, nom)
}
val vide = TableQuery[Vide]

val creationVide = db.run(DBIO.seq(
  vide.schema.create,
  vide += (None, "Bonjour"),
  vide += (None, "Au revoir")
))
Await.result(creationVide, Duration.Inf)

val valeursVides = vide.map(_.valeur)
val nomsVides = vide.map(_.nom)

/** 4.7.1. Minimum */

val minimum = agesUtilisateurs.min.result
println(minimum.statements.mkString("\n"))
val requeteMinimum = db.run(minimum).map(r => println(s"Minimum ${r.head}"))
Await.result(requeteMinimum, Duration.Inf)

val requeteMinimumVide = db
  .run(valeursVides.min.result)
  .map(r => println(s"Minimum vide $r"))
Await.result(requeteMinimumVide, Duration.Inf)

val requeteMinimumString = db
  .run(nomsVides.min.result)
  .map(r => println(s"Minimum string $r"))
Await.result(requeteMinimumString, Duration.Inf)

/** 4.7.2. Maximum */

val maximum = agesUtilisateurs.max.result
println(maximum.statements.mkString("\n"))
val requeteMaximum = db.run(maximum).map(r => println(s"Maximum ${r.head}"))
Await.result(requeteMaximum, Duration.Inf)

val requeteMaximumVide = db
  .run(valeursVides.max.result)
  .map(r => println(s"Maximum vide $r"))
Await.result(requeteMaximumVide, Duration.Inf)

val requeteMaximumString = db
  .run(nomsVides.max.result)
  .map(r => println(s"Maximum string $r"))
Await.result(requeteMaximumString, Duration.Inf)

/** 4.7.3. Somme */

val somme = agesUtilisateurs.sum.result
println(somme.statements.mkString("\n"))
val requeteSomme = db.run(somme).map(r => println(s"Somme ${r.head}"))
Await.result(requeteSomme, Duration.Inf)

val requeteSommeVide = db
  .run(valeursVides.sum.result)
  .map(r => println(s"Somme vide $r"))
Await.result(requeteSommeVide, Duration.Inf)

/*
val requeteSommeString = db.run(nomsVides.sum.result)
Await.result(requeteSommeString, Duration.Inf)
// org.h2.jdbc.JdbcSQLSyntaxErrorException: SUM or AVG on wrong data type for "SUM(NOM)"; SQL statement:
// select sum("NOM") from "VIDE" [90015-200]
 */

/** 4.7.4. Moyenne */

val moyenne = agesUtilisateurs.avg.result
println(moyenne.statements.mkString("\n"))
val requeteMoyenne = db.run(moyenne).map(r => println(s"Moyenne ${r.head}"))
Await.result(requeteMoyenne, Duration.Inf)

val requeteMoyenneVide = db
  .run(valeursVides.avg.result)
  .map(r => println(s"Moyenne vide $r"))
Await.result(requeteMoyenneVide, Duration.Inf)

/*
val requeteMoyenneString = db.run(nomsVides.avg.result)
Await.result(requeteMoyenneString, Duration.Inf)
// org.h2.jdbc.JdbcSQLSyntaxErrorException: SUM or AVG on wrong data type for "AVG(NOM)"; SQL statement:
// select avg("NOM") from "VIDE" [90015-200]
 */

/** 4.8. Join */
/** 4.8.1. Définition */

class Classes(tag: Tag) extends Table[(Int, String)](tag, "CLASSES") {
  def id = column[Int]("ID", O.PrimaryKey)
  def nom = column[String]("NOM", O.Unique)
  def * = (id, nom)
}
val classes = TableQuery[Classes]

class Eleves(tag: Tag) extends Table[(String, Int)](tag, "ELEVES") {
  def nom = column[String]("NOM", O.Unique)
  def idClasse = column[Int]("ID_CLASSE")
  def classe = foreignKey("FK_CLASSE_ELEVE", idClasse, classes)(_.id)
  def * = (nom, idClasse)
}
val eleves = TableQuery[Eleves]

class Professeurs(tag: Tag) extends Table[(String, Int)](tag, "PROFESSEURS") {
  def nom = column[String]("NOM", O.Unique)
  def idClasse = column[Int]("ID_CLASSE")
  def classe = foreignKey("FK_CLASSE_PROFESSEUR", idClasse, classes)(_.id)
  def * = (nom, idClasse)
}
val professeurs = TableQuery[Professeurs]

val remplissageClasses = db.run(DBIO.seq(
  classes.schema.create,
  eleves.schema.create,
  professeurs.schema.create,
  classes ++= Seq((1, "CE1"), (2, "CE2"), (3, "CM1"), (4, "CM2")),
  eleves ++= Seq(
    ("Romuald", 1), ("Pauline", 1),
    ("Olivia", 2), ("Néo", 2)
  ),
  professeurs ++= Seq(("M. Morel", 1), ("Mme Boudoir", 3))
))
Await.result(remplissageClasses, Duration.Inf)

/** 4.8.2. Left outer join */

val leftOuterJoin = eleves.joinLeft(professeurs).on(_.idClasse === _.idClasse)
println(leftOuterJoin.result.statements.mkString("\n"))
val classesLeftOuterJoin = db.run(leftOuterJoin.result).map { result =>
  println(s"Left outer join : ${result.length} résultats")
  result.foreach(println)
}
Await.result(classesLeftOuterJoin, Duration.Inf)

val professeursLeftOuterJoin = db
  .run(professeurs.joinLeft(eleves).on(_.idClasse === _.idClasse).result)
  .map { result =>
    println(s"Left outer join professeurs : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(professeursLeftOuterJoin, Duration.Inf)

/** 4.8.3. Right outer join */

val rightOuterJoin = eleves.joinRight(professeurs).on(_.idClasse === _.idClasse)
println(rightOuterJoin.result.statements.mkString("\n"))
val classesRightOuterJoin = db.run(rightOuterJoin.result).map { result =>
  println(s"Right outer join : ${result.length} résultats")
  result.foreach(println)
}
Await.result(classesRightOuterJoin, Duration.Inf)

val professeursRightOuterJoin = db
  .run(professeurs.joinRight(eleves).on(_.idClasse === _.idClasse).result)
  .map { result =>
    println(s"Right outer join professeurs : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(professeursRightOuterJoin, Duration.Inf)

/** 4.8.4. Inner join */

val innerJoin = eleves.join(professeurs).on(_.idClasse === _.idClasse).result
println(innerJoin.statements.mkString("\n"))
val classesInnerJoin = db.run(innerJoin).map { result =>
  println(s"Inner join : ${result.length} résultats")
  result.foreach(println)
}
Await.result(classesInnerJoin, Duration.Inf)

val professeursInnerJoin = db.
  run(professeurs.join(eleves).on(_.idClasse === _.idClasse).result)
  .map { result =>
    println(s"Inner join : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(professeursInnerJoin, Duration.Inf)

/** 4.8.5. Left join */

val leftJoin = leftOuterJoin.filter(_._2.isEmpty)
val classesLeftJoin = db.run(leftJoin.result)
  .map { result =>
    println(s"Left join : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(classesLeftJoin, Duration.Inf)

val professeursLeftJoin = db
  .run(professeurs.joinLeft(eleves).on(_.idClasse === _.idClasse).filter(_._2.isEmpty).result)
  .map { result =>
    println(s"Left join professeurs : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(professeursLeftJoin, Duration.Inf)

/** 4.8.6. Right join */

val rightJoin = rightOuterJoin.filter(_._1.isEmpty)
val classesRightJoin = db.run(rightJoin.result)
  .map { result =>
    println(s"Right join : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(classesRightJoin, Duration.Inf)

val professeursRightJoin = db
  .run(professeurs.joinRight(eleves).on(_.idClasse === _.idClasse).filter(_._1.isEmpty).result)
  .map { result =>
    println(s"Right join professeurs : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(professeursRightJoin, Duration.Inf)

/** 4.8.7. Full join */

val fullJoin = eleves.joinFull(professeurs).on(_.idClasse === _.idClasse).result
println(fullJoin.statements.mkString("\n"))
val classesFullJoin = db.run(fullJoin).map { result =>
  println(s"Full join : ${result.length} résultats")
  result.foreach(println)
}
Await.result(classesFullJoin, Duration.Inf)

val professeursFullJoin = db
  .run(professeurs.joinFull(eleves).on(_.idClasse === _.idClasse).result)
  .map { result =>
    println(s"Full join professeurs : ${result.length} résultats")
    result.foreach(println)
  }
Await.result(professeursFullJoin, Duration.Inf)
