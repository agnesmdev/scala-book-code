package eni.exceptions

case class CodeInconnuException(code: String) extends Exception {
  override def getMessage: String = s"Code inconnu $code"
}
