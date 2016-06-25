/**
  * Created by alanhala on 6/24/16.
  */
class Tarea(val facilidad: (Heroe, Equipo) => Int, val accion: Option[Heroe] => Option[Heroe],
            val recompensa: Equipo => Equipo) {
  def serRealizadaPor(equipo: Equipo): Equipo =
    equipo.reemplazarMiembro(mejorHeroeParaTarea(equipo), accion(mejorHeroeParaTarea(equipo)))

  def mejorHeroeParaTarea(equipo:Equipo):Option[Heroe] = {
    equipo.mejorHeroeSegun { heroe => this.facilidad(heroe,equipo) }
  }
}
