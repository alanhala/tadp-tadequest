import scala.util.Try

/**
  * Created by alanhala on 6/18/16.
  */
class Equipo(var oro: Integer, nombre: String, var heroes: List[Heroe]) {

  def mejorHeroeSegun(criterio: Heroe => Integer): Option[Heroe] =
    Try(heroes.maxBy(criterio(_))).toOption

  def obtenerItem(item: Item) = {
    mejorHeroeSegun(heroe=>heroe.incrementoMainStat(item)) match{
      case Some(heroe) if(heroe.incrementoMainStat(item)>0) => heroe.equiparItem(item)
      case _  => oro.+=(item.vender)
    }
  }

  def agregarMiembro(heroe: Heroe) =
    this.heroes = heroe :: this.heroes

  def reemplazarMiembro(heroe: Heroe) =
    this.heroes = heroe :: this.heroes.dropRight(1)

  def lider:Option[Heroe] = {
    mejorHeroeSegun(heroe=>heroe.valorStatPrincipal) match {
      case Some(heroe) if(heroes.filter(heroe2=>heroe2.valorStatPrincipal==heroe.valorStatPrincipal).size==1) => Option(heroe)
      case _ => None
    }
  }

}