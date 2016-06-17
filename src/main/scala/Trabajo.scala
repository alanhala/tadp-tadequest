trait StatPrincipal

case object Hp extends StatPrincipal
case object Fuerza extends StatPrincipal
case object Velocidad extends StatPrincipal
case object Inteligencia extends StatPrincipal



trait Trabajo extends ModificadorStats{
  val statPrincipal: StatPrincipal
}

case object Guerrero extends Trabajo{
  override val statPrincipal = Fuerza

  override def modificarStats(heroe: Heroe) =
    heroe.copy(hp = heroe.hp + 10, fuerza = heroe.fuerza + 15, inteligencia = 1.max(heroe.inteligencia - 10))
}

case object Mago extends Trabajo{
  override val statPrincipal = Inteligencia

  override def modificarStats(heroe: Heroe) =
    heroe.copy(inteligencia = heroe.inteligencia + 20, fuerza = 1.max(heroe.fuerza - 20))
}

case object Ladron extends Trabajo{
  override val statPrincipal = Velocidad

  override def modificarStats(heroe: Heroe) =
    heroe.copy(velocidad = heroe.velocidad + 10, hp = 1.max(heroe.hp - 5))
}