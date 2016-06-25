import scala.util.Try
import scala.util.Success


class Mision(tareas: List[Tarea]) {

  def serRealizada(equipo: Equipo): Try[Equipo] =
    tareas.foldLeft(Try(equipo)) ( (equipoAntes, tareaActual) => equipoAntes.flatMap(e => tareaActual.serRealizadaPor(e)) )

}



class Tarea(val descripcion: String,
                    val facilidad: (Heroe, Equipo) => Int,
                    val modificarHeroe: Heroe => Heroe,
                    val recompensa: Equipo => Equipo) {

  def mejorHeroeParaTarea(equipo: Equipo): Option[Heroe] =
    equipo.mejorHeroeSegun { heroe => this.facilidad(heroe, equipo) }


  def serRealizadaPor(equipo: Equipo): Try[Equipo] = {
      val mejorHeroe = this.mejorHeroeParaTarea(equipo)

      mejorHeroe match{
        case Some(heroe) => {
          val equipoConHeroeModificado = equipo.remplazar(heroe, this.modificarHeroe(heroe))
          Success(this.recompensa(equipoConHeroeModificado))
        }

        case None => throw new Exception("Fallé en la tarea " + this.descripcion)
      }
    }
}
