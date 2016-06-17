case class Heroe(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int, trabajo: Option[Trabajo], inventario: Inventario) {

  def heroeConStatsCalculados: Heroe = {
    val modificadores: List[ModificadorStats] = trabajo.toList ::: inventario.itemList
    modificadores.foldLeft(this)((heroe: Heroe, modificador: ModificadorStats) => modificador.modificarStats(heroe))
  }

  def equiparCasco(casco: Casco): Unit = if(casco.puedeSerUsado(this)) inventario.equiparCasco(casco)
  def equiparArmadura(armadura: Armadura): Unit = if(armadura.puedeSerUsado(this)) inventario.equiparArmadura(armadura)
  def equiparItemDeMano(itemDeMano: ItemDeMano): Unit = if(itemDeMano.puedeSerUsado(this)) inventario.equiparItemDeMano(itemDeMano)
  def equiparTalisman(talisman: Talisman): Unit = if(talisman.puedeSerUsado(this)) inventario.equiparTalisman(talisman)
}


trait ModificadorStats{
  def modificarStats(heroe: Heroe): Heroe
}