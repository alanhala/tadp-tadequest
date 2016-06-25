import scala.util.Try

/**
  * Created by alanhala on 6/18/16.
  */
case class Equipo(oro: Integer, nombre: String, heroes: List[Heroe]) {

  def mejorHeroeSegun(criterio: Heroe => Int): Option[Heroe] =
    Try(heroes.maxBy(criterio(_))).toOption

  def obtenerItem(item: Item) = {
    mejorHeroeSegun(heroe=>heroe.incrementoMainStat(item)) match{
      case Some(heroe) if(heroe.incrementoMainStat(item)>0) => heroe.equiparItem(item)
      case _  => oro.+=(item.vender)
    }
  }

  def agregarMiembro(heroe: Heroe) =
    this.copy(heroes = heroe :: this.heroes)

  def reemplazarMiembro(heroe: Option[Heroe], nuevoHeroe: Option[Heroe]) =
    this.copy(heroes = nuevoHeroe.toList ::: heroes.filterNot(_ == heroe))

  def lider:Option[Heroe] = {
    mejorHeroeSegun(heroe=>heroe.valorStatPrincipal) match {
      case Some(heroe) if(heroes.filter(heroe2=>heroe2.valorStatPrincipal==heroe.valorStatPrincipal).size==1) => Option(heroe)
      case _ => None
    }
  }
}