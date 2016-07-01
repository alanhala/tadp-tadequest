import scala.util.Try

case class Equipo(oro: Int, nombre: String, heroes: List[Heroe]) {

  def mejorHeroeSegun(criterio: Heroe => Int): Option[Heroe] =
    Try(heroes.maxBy(criterio(_))).toOption

  def obtenerItem(item: Item): Equipo = {
    val mejorHeroeParaItem = mejorHeroeSegun(heroe=>heroe.incrementoMainStat(item))
    var heroeClonado:Heroe = mejorHeroeParaItem.get.copy().equiparItem(item)

    if(mejorHeroeParaItem.get.incrementoMainStat(item)>0){
      this.quitarMiembro(mejorHeroeParaItem.get)
      this.agregarMiembro(heroeClonado)
    }
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

  def get: Equipo

  def flatMap(f: (Equipo => EstadoEquipo)): EstadoEquipo
  def fold[B](ifEmpty: B)(f: Equipo => B): B
}

case class EquipoEnAccion(equipo: Equipo) extends EstadoEquipo {
  override  val falloEnMision = false

  override def get: Equipo = equipo

  override def flatMap(f: (Equipo => EstadoEquipo)): EstadoEquipo = f(equipo)

  override def fold[B](ifEmpty: B)(f: Equipo => B): B = f(equipo)
}

case class EquipoFallido(equipo: Equipo, tareaFallida: Tarea) extends EstadoEquipo {
  override  val falloEnMision = true

  override def get: Equipo = equipo

  override def flatMap(f: (Equipo => EstadoEquipo)): EstadoEquipo = this

  override def fold[B](ifEmpty: B)(f: Equipo => B): B = ifEmpty
}
