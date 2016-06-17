trait Item extends ModificadorStats{
  def puedeSerUsado(heroe: Heroe): Boolean
}

trait Casco extends Item
trait Armadura extends Item
trait Talisman extends Item

trait ItemDeMano extends Item{
  val usaDosManos: Boolean
}

case object CascoVikingo extends Casco{
  override def puedeSerUsado(heroe: Heroe): Boolean = heroe.fuerza > 30

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(hp = heroe.hp + 10)
}

case object PalitoMagico extends ItemDeMano{
  override val usaDosManos = false

  override def puedeSerUsado(heroe: Heroe): Boolean = {
    heroe.trabajo match{
      case Some(Mago) => true
      case Some(Ladron) => heroe.inteligencia > 30
      case _ => false
    }
  }

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(inteligencia = heroe.inteligencia + 20)
}

case object ArmaduraEleganteSport extends Armadura{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(velocidad = heroe.velocidad + 30, hp = 1.max(heroe.hp - 30))
}

case object ArcoViejo extends ItemDeMano{
  override val usaDosManos = true

  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(fuerza = heroe.fuerza + 2)
}

case object EscudoAntiRobo extends ItemDeMano{
  override val usaDosManos = false

  override def puedeSerUsado(heroe: Heroe): Boolean = {
    heroe.trabajo match{
      case Some(Ladron) => false
      case _ => heroe.fuerza >= 20
    }
  }

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(hp = heroe.hp + 20)
}

case object TalismanDeDedicacion extends Talisman{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(heroe: Heroe): Heroe = {
    heroe.trabajo.map(t => t.statPrincipal) match{
      case Some(Fuerza) => heroe.copy(hp = heroe.hp + heroe.fuerza/10, fuerza = heroe.fuerza + heroe.fuerza/10, velocidad = heroe.velocidad + heroe.fuerza/10, inteligencia = heroe.inteligencia + heroe.fuerza/10)
      case Some(Hp) => heroe.copy(hp = heroe.hp + heroe.hp/10, fuerza = heroe.fuerza + heroe.hp/10, velocidad = heroe.velocidad + heroe.hp/10, inteligencia = heroe.inteligencia + heroe.hp/10)
      case Some(Velocidad) => heroe.copy(hp = heroe.hp + heroe.velocidad/10, fuerza = heroe.fuerza + heroe.velocidad/10, velocidad = heroe.velocidad + heroe.velocidad/10, inteligencia = heroe.inteligencia + heroe.velocidad/10)
      case Some(Inteligencia) => heroe.copy(hp = heroe.hp + heroe.inteligencia/10, fuerza = heroe.fuerza + heroe.inteligencia/10, velocidad = heroe.velocidad + heroe.inteligencia/10, inteligencia = heroe.inteligencia + heroe.inteligencia/10)
      case None => heroe
    }
  }
}

case object TalismanDeMinimalismo extends Talisman{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(hp = 1.max(heroe.hp + 50 - 10 * (heroe.inventario.itemList.size - 1)))
}

case object VinchaDelBufaloDeAgua extends Casco{
  override def puedeSerUsado(heroe: Heroe): Boolean = heroe.trabajo.isEmpty

  override def modificarStats(heroe: Heroe): Heroe = {
    if(heroe.fuerza > heroe.inteligencia)
      heroe.copy(inteligencia = heroe.inteligencia + 30)
    else
      heroe.copy(hp = heroe.hp + 10, velocidad = heroe.velocidad + 10, fuerza = heroe.fuerza + 10)
  }
}

case object TalismanMaldito extends Talisman{
  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(hp = 1, fuerza = 1, velocidad = 1, inteligencia = 1)
}

case object EspadaDeLaVida extends ItemDeMano{
  override val usaDosManos = false

  override def puedeSerUsado(heroe: Heroe): Boolean = true

  override def modificarStats(heroe: Heroe): Heroe =
    heroe.copy(fuerza = heroe.hp)
}


class Inventario (var cabeza: Option[Casco],
                  var torso: Option[Armadura],
                  var manos: List[ItemDeMano] = List(),
                  var talismanes: List[Talisman] = List())
{
  def itemList: List[Item] =
    cabeza.toList ::: torso.toList ::: manos ::: talismanes


  def equiparCasco(casco: Casco): Unit = cabeza = Some(casco)
  def equiparArmadura(armadura: Armadura): Unit = torso = Some(armadura)
  def equiparTalisman(talisman: Talisman): Unit = talismanes = talisman :: talismanes

  def equiparItemDeMano(item: ItemDeMano): Unit = {
    if(item.usaDosManos || manos.exists(i => i.usaDosManos))
      manos = List(item)
    else if(manos.size <= 1)
      manos = item :: manos
    else
      manos = List(manos.head, item)
  }
}
