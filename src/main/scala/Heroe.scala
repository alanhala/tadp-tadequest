case class Heroe(stats: Stats,  var trabajo: Option[Trabajo], inventario: Inventario) {

  def statsFinales: Stats = {
    val modificadores: List[ModificadorStats] = trabajo.toList ::: inventario.itemList
    val statsModificados: Stats = modificadores.foldLeft(stats)((stats: Stats, modificador: ModificadorStats) => modificador.modificarStats(stats, this))
    nuncaMenorA1(statsModificados)
  }

  def nuncaMenorA1(stats: Stats): Stats = {
    var hp: Int = stats.hp
    var fuerza: Int = stats.fuerza
    var velocidad: Int = stats.velocidad
    var inteligencia: Int = stats.inteligencia
    if (stats.hp < 1) hp = 1
    if (stats.fuerza < 1) fuerza = 1
    if (stats.velocidad < 1) velocidad = 1
    if (stats.inteligencia < 1) inteligencia = 1
    stats.copy(hp, fuerza, velocidad, inteligencia)
  }


  def equiparItem(item: Item): Heroe = {
    if (item.puedeSerUsado(this)) {
      item match {
        case item: Casco => this.copy(inventario = inventario.equiparCasco(item))
        case item: Armadura => this.copy(inventario = inventario.equiparArmadura(item))
        case item: ItemDeMano => this.copy(inventario = inventario.equiparItemDeMano(item))
        case item: Talisman => this.copy(inventario = inventario.equiparTalisman(item))
      }
    }
    else
      this
  }

  def cambiarTrabajo(trabajo: Option[Trabajo]): Heroe ={
    this.copy(trabajo=trabajo)
  }

  def incrementoMainStat(item: Item): Int = {

    if(item.puedeSerUsado(this)) {
      val statsModificados:Stats = item.modificarStats(this.stats, this)

      this.trabajo.map(t => t.statPrincipal).fold(0)(stat =>
        stat match{
          case Hp => statsModificados.hp - this.stats.hp
          case Fuerza => statsModificados.fuerza - this.stats.fuerza
          case Velocidad => statsModificados.velocidad - this.stats.velocidad
          case Inteligencia => statsModificados.inteligencia - this.stats.inteligencia
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

  def statPrincipal: Option[StatPrincipal] = {
    this.trabajo.map(_.statPrincipal)
  }

}

case class Stats(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int)


trait ModificadorStats {
  def modificarStats(stats: Stats, heroe: Heroe): Stats

}
