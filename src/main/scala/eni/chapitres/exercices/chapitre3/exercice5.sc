/**
 * Définir une expression régulière qui extrait d’une URI :
   - le caractère sécurisé de l’URI (http ou https) ;
   - le nom du site ;
   - la page.
 */
val uriRegex = """http(s?)://([\w.\-_]+).*/([\w.\-_]+)?\.?html$""".r
def extraireSite(uri: String) = uri match {
  case uriRegex(securite, site, page) =>
    s"Site $site ${if (securite != "s") "non " else ""}sécurisé, page : $page"
  case _ => "Site non reconnu"
}
extraireSite("https://docs.scala-lang.org/tour/regular-expression-patterns.html")