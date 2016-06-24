trait StatPrincipal

case object Hp extends StatPrincipal
case object Fuerza extends StatPrincipal
case object Velocidad extends StatPrincipal
case object Inteligencia extends StatPrincipal

class Trabajo(var statPrincipal: StatPrincipal, var modificadores: Stats) extends ModificadorStats{

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(hp = 1.max(stats.hp + modificadores.hp),
                     fuerza = 1.max(stats.fuerza + modificadores.fuerza),
                     velocidad = 1.max(stats.velocidad + modificadores.velocidad),
                     inteligencia = 1.max(stats.inteligencia + modificadores.inteligencia))
}

case object Guerrero extends Trabajo(Fuerza, Stats(10, 15, 0, -10))

case object Mago extends Trabajo(Inteligencia, Stats(0, -20, 0, 20))

case object Ladron extends Trabajo(Velocidad, Stats(-5, 0, 10, 0))

/*trait Trabajo extends ModificadorStats{
  val statPrincipal: StatPrincipal
}

case object Guerrero extends Trabajo{
  override val statPrincipal = Fuerza

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(hp = stats.hp + 10, fuerza = stats.fuerza + 15, inteligencia = 1.max(stats.inteligencia - 10))
}

case object Mago extends Trabajo{
  override val statPrincipal = Inteligencia

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(inteligencia = stats.inteligencia + 20, fuerza = 1.max(stats.fuerza - 20))
}

case object Ladron extends Trabajo{
  override val statPrincipal = Velocidad

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(velocidad = stats.velocidad + 10, hp = 1.max(stats.hp - 5))
}*/