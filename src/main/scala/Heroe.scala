/**
  * Created by alanhala on 6/17/16.
  */
case class Heroe(hp: Double, fuerza: Double, velocidad: Double, inteligencia: Double, items: List[Item] = List(), trabajo: Option[Trabajo]) {

  def aplicarTrabajo: Heroe = {
    trabajo match {
      case Some(Guerrero) => copy(hp + 10, fuerza + 15, inteligencia - 10)
      case Some(Mago)     => copy(fuerza - 20, inteligencia + 20)
      case Some(Ladron)   => copy(hp - 5, velocidad + 10)
      case _              => this
    }
  }

  def aplicarItem(item: Item): Heroe = {
    item match {
      case CascoVikingo          => copy(hp + 10)
      case PalitoMagico          => copy(inteligencia + 20)
      case ArmaduraEleganteSport => copy(hp - 30, velocidad + 30)
      case ArcoViejo             => copy(fuerza + 2)
      case EscudoAntiRobo        => copy(hp + 20)
      case TalismanDeDedicacion => trabajo match {
        case Some(Guerrero) => copy(hp + (fuerza * 0.1), fuerza + (fuerza * 0.1), inteligencia + (fuerza * 0.1), velocidad + (fuerza * 0.1))
        case Some(Mago)     => copy(hp + (inteligencia * 0.1), fuerza + (inteligencia * 0.1), inteligencia + (inteligencia * 0.1), velocidad + (inteligencia * 0.1))
        case Some(Ladron)   => copy(hp + (velocidad * 0.1), fuerza + (velocidad * 0.1), inteligencia + (velocidad * 0.1), velocidad + (velocidad * 0.1))
        case _              => this
      }
      case TalismanDeMinimalismo => copy(hp + 50 - ((items.size - 1) * 10))
      case VinchaDelBufaloDeAgua => if (fuerza > inteligencia) copy(inteligencia + 30) else copy(hp + 10, fuerza + 10, velocidad + 10)
      case TalismanMaldito       => copy(hp = 1, fuerza = 1, velocidad = 1, inteligencia = 1)
      case EspadaDeLaVida        => copy(fuerza = hp)
      case _                     => this
    }
  }

  def calcularStats: Heroe = {
    var heroeConCalculoDeTrabajo: Heroe = this.aplicarTrabajo
    items.foldLeft(heroeConCalculoDeTrabajo)((heroe: Heroe, item: Item) => heroe.aplicarItem(item))
  }


}

trait Trabajo

case object Guerrero extends Trabajo
case object Mago extends Trabajo
case object Ladron extends Trabajo

trait Item

case object CascoVikingo extends Item
case object PalitoMagico extends Item
case object ArmaduraEleganteSport extends Item
case object ArcoViejo extends Item
case object EscudoAntiRobo extends Item
case object TalismanDeDedicacion extends Item
case object TalismanDeMinimalismo extends Item
case object VinchaDelBufaloDeAgua extends Item
case object TalismanMaldito extends Item
case object EspadaDeLaVida extends Item



