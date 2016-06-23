case class Heroe(stats: Stats,  trabajo: Option[Trabajo], inventario: Inventario) {

  def statsFinales: Stats = {
    val modificadores: List[ModificadorStats] = trabajo.toList ::: inventario.itemList
    modificadores.foldLeft(stats)((stats: Stats, modificador: ModificadorStats) => modificador.modificarStats(stats, this))
  }

  def equiparItem(item:Item): Unit = {
    item match {
      case item:Casco => this.equiparCasco(item)
      case item:Armadura => this.equiparArmadura(item)
      case item:ItemDeMano => this.equiparItemDeMano(item)
      case item:Talisman => this.equiparTalisman(item)
    }
  }

  def equiparCasco(casco: Casco): Unit = if(casco.puedeSerUsado(this)) inventario.equiparCasco(casco)
  def equiparArmadura(armadura: Armadura): Unit = if(armadura.puedeSerUsado(this)) inventario.equiparArmadura(armadura)
  def equiparItemDeMano(itemDeMano: ItemDeMano): Unit = if(itemDeMano.puedeSerUsado(this)) inventario.equiparItemDeMano(itemDeMano)
  def equiparTalisman(talisman: Talisman): Unit = if(talisman.puedeSerUsado(this)) inventario.equiparTalisman(talisman)
  //def cambiarTrabajo(trabajo: Option[Trabajo]) =
    //this.trabajo = trabajo

  def incrementoMainStat(item: Item): Integer = {
    if(item.puedeSerUsado(this)){
      val statsModificados:Stats = item.modificarStats(this.stats,this)
      this.trabajo.map(t => t.statPrincipal) match {
        case Some(Hp) => statsModificados.hp-this.stats.hp
        case Some(Fuerza) => statsModificados.fuerza-this.stats.fuerza
        case Some(Velocidad) => statsModificados.fuerza-this.stats.fuerza
        case Some(Inteligencia) => statsModificados.fuerza-this.stats.fuerza
        case None => 0
      }
    }else {
      0
    }
  }

  def valorStatPrincipal:Integer = {
    this.trabajo.map(t => t.statPrincipal) match {
      case Some(Hp) => this.stats.hp
      case Some(Fuerza) => this.stats.fuerza
      case Some(Velocidad) => this.stats.velocidad
      case Some(Inteligencia) => this.stats.inteligencia
      case None => 0
    }
  }

}


case class Stats(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int)


trait ModificadorStats{
  def modificarStats(stats: Stats, heroe: Heroe): Stats
}