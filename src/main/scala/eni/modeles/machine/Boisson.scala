package eni.modeles.machine

sealed trait Boisson {
  def code: String
}

case object Eau extends Boisson {
  override val code: String = "EAU"
}

case object Biere extends Boisson {
  override val code: String = "BIR"
}

case object Soda extends Boisson {
  override val code: String = "SDA"
}

case object Jus extends Boisson {
  override val code: String = "JUS"
}