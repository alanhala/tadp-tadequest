case class Heroe(stats: Stats,  trabajo: Option[Trabajo], inventario: Inventario) {

  def statsFinales: Stats = {
    val modificadores: List[ModificadorStats] = trabajo.toList ::: inventario.itemList
    modificadores.foldLeft(stats)((stats: Stats, modificador: ModificadorStats) => modificador.modificarStats(stats, this))
  }

  def equiparCasco(casco: Casco): Unit = if(casco.puedeSerUsado(this)) inventario.equiparCasco(casco)
  def equiparArmadura(armadura: Armadura): Unit = if(armadura.puedeSerUsado(this)) inventario.equiparArmadura(armadura)
  def equiparItemDeMano(itemDeMano: ItemDeMano): Unit = if(itemDeMano.puedeSerUsado(this)) inventario.equiparItemDeMano(itemDeMano)
  def equiparTalisman(talisman: Talisman): Unit = if(talisman.puedeSerUsado(this)) inventario.equiparTalisman(talisman)
  //def cambiarTrabajo(trabajo: Option[Trabajo]) =
    //this.trabajo = trabajo
  def incrementoMainStat(item: Item): Integer = ???
}


case class Stats(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int)


trait ModificadorStats{
  def modificarStats(stats: Stats, heroe: Heroe): Stats
}