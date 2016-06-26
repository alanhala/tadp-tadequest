import scala.util.Try

case class Equipo(oro: Int, nombre: String, heroes: List[Heroe]) {

  def mejorHeroeSegun(criterio: Heroe => Int): Option[Heroe] =
    Try(heroes.maxBy(criterio(_))).toOption

  def obtenerItem(item: Item): Equipo = {
    val mejorHeroeParaItem = mejorHeroeSegun(heroe=>heroe.incrementoMainStat(item))

    if(mejorHeroeParaItem.exists(h => h.incrementoMainStat(item) > 0))
      this.remplazar(mejorHeroeParaItem.get, mejorHeroeParaItem.get.equiparItem(item))
    else
      this.copy(oro = oro + item.valor)
  }

  def agregarMiembro(heroe: Heroe): Equipo =
    this.copy(heroes = heroe :: this.heroes)

  def quitarMiembro(heroe: Heroe): Equipo =
    this.copy(heroes = this.heroes.filter(h => h != heroe))

  def remplazar(heroeViejo: Heroe, heroeNuevo: Heroe): Equipo =
    this.quitarMiembro(heroeViejo).agregarMiembro(heroeNuevo)
  

  def lider: Option[Heroe] = {
    val posibleLider = mejorHeroeSegun(heroe => heroe.valorStatPrincipal)

    posibleLider.fold[Option[Heroe]](None)(pl => {
      if(heroes.exists(h => h.valorStatPrincipal == pl.valorStatPrincipal && h != pl)) None else posibleLider
    })
  }
}


trait EstadoEquipo {
  val falloEnMision: Boolean

  def flatMap(f: (Equipo => EstadoEquipo)): EstadoEquipo
}

case class EquipoEnAccion(equipo: Equipo) extends EstadoEquipo {
  override  val falloEnMision = false

  def flatMap(f: (Equipo => EstadoEquipo)): EstadoEquipo = f(equipo)
}

case class EquipoFallido(equipo: Equipo, tareaFallida: Tarea) extends EstadoEquipo {
  override  val falloEnMision = true

  def flatMap(f: (Equipo => EstadoEquipo)): EstadoEquipo = this
}
