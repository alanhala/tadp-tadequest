import scala.util.Try

case class Equipo(oro: Integer, nombre: String, heroes: List[Heroe]) {

  def mejorHeroeSegun(criterio: Heroe => Int): Option[Heroe] =
    Try(heroes.maxBy(criterio(_))).toOption

  def obtenerItem(item: Item) = {
    mejorHeroeSegun(heroe=>heroe.incrementoMainStat(item)) match{
      case Some(heroe) if(heroe.incrementoMainStat(item)>0) => heroe.equiparItem(item)
      case _  => oro.+=(item.vender)
    }
  }

  def agregarMiembro(heroe: Heroe): Equipo =
    this.copy(heroes = heroe :: this.heroes)

  def quitarMiembro(heroe: Heroe): Equipo =
    this.copy(heroes = this.heroes.filter(h => h != heroe))

  def remplazar(heroeViejo: Heroe, heroeNuevo: Heroe): Equipo =
    this.quitarMiembro(heroeViejo).agregarMiembro(heroeNuevo)
  

  def lider:Option[Heroe] = {
    mejorHeroeSegun(heroe=>heroe.valorStatPrincipal) match {
      case Some(heroe) if(heroes.filter(heroe2=>heroe2.valorStatPrincipal==heroe.valorStatPrincipal).size==1) => Option(heroe)
      case _ => None
    }
  }
}