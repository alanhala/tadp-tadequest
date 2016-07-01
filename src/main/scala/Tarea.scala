
class Mision(tareas: List[Tarea], val recompensa: Equipo => Equipo) {

  def serRealizada(equipo: Equipo): EstadoEquipo = {
    var resultadoMision: EstadoEquipo = tareas.foldLeft[EstadoEquipo](EquipoEnAccion(equipo))((equipoAntes, tareaActual) => equipoAntes.flatMap(e => tareaActual.serRealizadaPor(e)))
    if (!resultadoMision.falloEnMision) EquipoEnAccion(this.recompensa(resultadoMision.get))
    else resultadoMision
  }
}


class Tarea(val descripcion: String,
            val facilidad: (Heroe, Equipo) => Int,
            val modificarHeroe: (Heroe, Equipo) => Equipo) {

  def mejorHeroeParaTarea(equipo: Equipo): Option[Heroe] =
    equipo.mejorHeroeSegun { heroe => this.facilidad(heroe, equipo) }


  def serRealizadaPor(equipo: Equipo): EstadoEquipo = {
    val mejorHeroe = this.mejorHeroeParaTarea(equipo)

    mejorHeroe.fold[EstadoEquipo](EquipoFallido(equipo, this))(heroe => {
      val equipoConHeroeModificado = this.modificarHeroe(heroe, equipo)
      EquipoEnAccion(equipoConHeroeModificado)
    })
  }
}

object pelearContraMonstruo extends Tarea("pelearContraMonstruo", { (heroe, equipo) => equipo.lider.flatMap(_.trabajo) match {
  case Some(Guerrero) => 20
  case _ => 10
}
}, { (heroe, equipo) => if (heroe.statsFinales.fuerza < 20)
  equipo.remplazar(heroe, heroe.copy(stats = heroe.stats.copy(hp = heroe.stats.hp - 10)))
else equipo
})

object forzarPuerta extends Tarea("forzarPuerta", { (heroe, equipo) => heroe.statsFinales.inteligencia + 10 * equipo.heroes.count(_.trabajo.getOrElse(None) == Ladron)
}, { (heroe, equipo) => heroe.trabajo match {
  case Some(Ladron) => equipo
  case Some(Mago) => equipo
  case _ => equipo.remplazar(heroe, heroe.copy(stats = heroe.stats.copy(hp = heroe.stats.hp - 5, fuerza = heroe.stats.fuerza + 1)))
}
})

class robarTalisman(talisman:Talisman) extends Tarea("robarTalisman", { (heroe, equipo) => equipo.lider.flatMap(_.trabajo) match {
  case Some(Ladron) => heroe.statsFinales.velocidad
  case _ => 0
}
}, { (heroe, equipo) => equipo.obtenerItem(talisman)
})






