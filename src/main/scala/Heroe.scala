case class Heroe(stats: Stats,  trabajo: Option[Trabajo], inventario: Inventario) {

  def statsFinales: Stats = {
    val modificadores: List[ModificadorStats] = trabajo.toList ::: inventario.itemList
    modificadores.foldLeft(stats)((stats: Stats, modificador: ModificadorStats) => modificador.modificarStats(stats, this))
  }

  def equiparItem(item: Item): Heroe = {
    if(item.puedeSerUsado(this)){
      item match {
        case item: Casco => this.copy(inventario = inventario.equiparCasco(item))
        case item: Armadura => this.copy(inventario = inventario.equiparArmadura(item))
        case item: ItemDeMano => this.copy(inventario = inventario.equiparItemDeMano(item))
        case item:Talisman => this.copy(inventario = inventario.equiparTalisman(item))
      }
    }
    else
      this
  }

  def cambiarTrabajo(trabajo: Trabajo): Heroe =
    this.copy(trabajo = Some(trabajo))

  def incrementoMainStat(item: Item): Int = {

    if(item.puedeSerUsado(this)) {
      val statsModificados:Stats = item.modificarStats(this.stats, this)

      this.trabajo.map(t => t.statPrincipal).fold(0)(stat =>
        stat match{
          case Hp => statsModificados.hp - this.stats.hp
          case Fuerza => statsModificados.fuerza - this.stats.fuerza
          case Velocidad => statsModificados.fuerza - this.stats.fuerza
          case Inteligencia => statsModificados.fuerza - this.stats.fuerza
        }
      )
    } else
      0
  }

  def valorStatPrincipal: Int = {

    this.trabajo.map(t => t.statPrincipal).fold(0)(stat =>
      stat match{
        case Hp => this.stats.hp
        case Fuerza => this.stats.fuerza
        case Velocidad => this.stats.velocidad
        case Inteligencia => this.stats.inteligencia
      }
    )
  }

}


case class Stats(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int)


trait ModificadorStats{
  def modificarStats(stats: Stats, heroe: Heroe): Stats
}