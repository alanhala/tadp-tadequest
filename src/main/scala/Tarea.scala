class Mision(tareas: List[Tarea]) {

  def serRealizada(equipo: Equipo): EstadoEquipo =
    tareas.foldLeft[EstadoEquipo](EquipoEnAccion(equipo)) ( (equipoAntes, tareaActual) => equipoAntes.flatMap(e => tareaActual.serRealizadaPor(e)) )
}



class Tarea(val descripcion: String,
                    val facilidad: (Heroe, Equipo) => Int,
                    val modificarHeroe: Heroe => Heroe,
                    val recompensa: Equipo => Equipo) {

  def mejorHeroeParaTarea(equipo: Equipo): Option[Heroe] =
    equipo.mejorHeroeSegun { heroe => this.facilidad(heroe, equipo) }


  def serRealizadaPor(equipo: Equipo): EstadoEquipo = {
      val mejorHeroe = this.mejorHeroeParaTarea(equipo)

      mejorHeroe.fold[EstadoEquipo](EquipoFallido(equipo, this))(heroe => {
        val equipoConHeroeModificado = equipo.remplazar(heroe, this.modificarHeroe(heroe))
        EquipoEnAccion(this.recompensa(equipoConHeroeModificado))
      })
    }
}
