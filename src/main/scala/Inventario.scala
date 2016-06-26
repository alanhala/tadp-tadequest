trait Item extends ModificadorStats{
  val valor: Int = 0 //Hay que overridearlo en cada case object especifico para darle un valor distinto
  def puedeSerUsado(heroe: Heroe): Boolean
}

trait Casco extends Item
trait Armadura extends Item
trait Talisman extends Item

trait ItemDeMano extends Item{
  val usaDosManos: Boolean
}

case object CascoVikingo extends Casco{

  override def puedeSerUsado(heroe: Heroe): Boolean = heroe.stats.fuerza > 30

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(hp = stats.hp + 10)
}

case object PalitoMagico extends ItemDeMano{
  override val usaDosManos = false

  override def puedeSerUsado(heroe: Heroe): Boolean = {
    heroe.trabajo match{
      case Some(Mago) => true
      case Some(Ladron) => heroe.stats.inteligencia > 30
      case _ => false
    }
  }

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(inteligencia = stats.inteligencia + 20)
}

case object ArmaduraEleganteSport extends Armadura{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(velocidad = stats.velocidad + 30, hp = 1.max(stats.hp - 30))
}

case object ArcoViejo extends ItemDeMano{
  override val usaDosManos = true

  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(fuerza = stats.fuerza + 2)
}

case object EscudoAntiRobo extends ItemDeMano{
  override val usaDosManos = false

  override def puedeSerUsado(heroe: Heroe): Boolean = {
    heroe.trabajo match{
      case Some(Ladron) => false
      case _ => heroe.stats.fuerza >= 20
    }
  }

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(hp = stats.hp + 20)
}

case object TalismanDeDedicacion extends Talisman{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(stats: Stats, heroe: Heroe): Stats = {
    heroe.trabajo.map(t => t.statPrincipal) match{
      case Some(Fuerza) => stats.copy(hp = stats.hp + stats.fuerza/10, fuerza = stats.fuerza + stats.fuerza/10, velocidad = stats.velocidad + stats.fuerza/10, inteligencia = stats.inteligencia + stats.fuerza/10)
      case Some(Hp) => stats.copy(hp = stats.hp + stats.hp/10, fuerza = stats.fuerza + stats.hp/10, velocidad = stats.velocidad + stats.hp/10, inteligencia = stats.inteligencia + stats.hp/10)
      case Some(Velocidad) => stats.copy(hp = stats.hp + stats.velocidad/10, fuerza = stats.fuerza + stats.velocidad/10, velocidad = stats.velocidad + stats.velocidad/10, inteligencia = stats.inteligencia + stats.velocidad/10)
      case Some(Inteligencia) => stats.copy(hp = stats.hp + stats.inteligencia/10, fuerza = stats.fuerza + stats.inteligencia/10, velocidad = stats.velocidad + stats.inteligencia/10, inteligencia = stats.inteligencia + stats.inteligencia/10)
      case None => stats
    }
  }
}

case object TalismanDeMinimalismo extends Talisman{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(hp = 1.max(stats.hp + 50 - 10 * (heroe.inventario.itemList.size - 1)))
}

case object VinchaDelBufaloDeAgua extends Casco{
  override def puedeSerUsado(heroe: Heroe): Boolean = heroe.trabajo.isEmpty

  override def modificarStats(stats: Stats, heroe: Heroe): Stats = {
    if(heroe.stats.fuerza > heroe.stats.inteligencia)
      stats.copy(inteligencia = stats.inteligencia + 30)
    else
      stats.copy(hp = stats.hp + 10, velocidad = stats.velocidad + 10, fuerza = stats.fuerza + 10)
  }
}

case object TalismanMaldito extends Talisman{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(hp = 1, fuerza = 1, velocidad = 1, inteligencia = 1)
}

case object EspadaDeLaVida extends ItemDeMano{
  override val usaDosManos = false

  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(stats: Stats, heroe: Heroe): Stats =
    stats.copy(fuerza = stats.hp)
}


case class Inventario (cabeza: Option[Casco],
                                     torso: Option[Armadura],
                                     manos: List[ItemDeMano] = List(),
                                     talismanes: List[Talisman] = List())
{
  def itemList: List[Item] =
    cabeza.toList ::: torso.toList ::: manos ::: talismanes


  def equiparCasco(casco: Casco): Inventario = this.copy(cabeza = Some(casco))
  def equiparArmadura(armadura: Armadura): Inventario = this.copy(torso = Some(armadura))
  def equiparTalisman(talisman: Talisman): Inventario = this.copy(talismanes = talisman :: talismanes)

  def equiparItemDeMano(item: ItemDeMano): Inventario = {
    if(item.usaDosManos || manos.exists(i => i.usaDosManos))
      this.copy(manos = List(item))
    else if(manos.size <= 1)
      this.copy(manos = item :: manos)
    else
      this.copy(manos = List(manos.head, item))
  }
}
