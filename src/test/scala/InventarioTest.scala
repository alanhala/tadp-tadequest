/**
  * Created by Zaxtor on 17/06/2016.
  */

import org.junit.{Before, Test}
import org.junit.Assert._

class InventarioTest {

  var unGuerrero: Heroe = _
  var unMago: Heroe = _
  var unLadron: Heroe = _
  var unDesempleado: Heroe = _

  def HP_INICIAL: Int = 100
  def FUERZA_INICIAL: Int = 30
  def VEL_INICIAL: Int = 30
  def INT_INICIAL: Int = 30

  def inventarioVacio = new Inventario(None, None)

  @Before
  def setUp {

    unDesempleado = new Heroe(hp=HP_INICIAL, fuerza=FUERZA_INICIAL, velocidad=VEL_INICIAL, inteligencia=INT_INICIAL, None , inventarioVacio)
    unGuerrero = unDesempleado.copy(trabajo = Some(Guerrero), inventario = inventarioVacio)
    unMago = unDesempleado.copy(trabajo = Some(Mago), inventario = inventarioVacio)
    unLadron = unDesempleado.copy(trabajo = Some(Ladron), inventario = inventarioVacio)

  }

  @Test
  def testTrabajoMago {
    unMago = unMago.heroeConStatsCalculados
    assertEquals(unMago.inteligencia, INT_INICIAL + 20)
    assertEquals(unMago.fuerza, FUERZA_INICIAL - 20)
  }

  @Test
  def testTrabajoGuerrero {
    unGuerrero = unGuerrero.heroeConStatsCalculados
    assertEquals(unGuerrero.hp, HP_INICIAL + 10)
    assertEquals(unGuerrero.fuerza, FUERZA_INICIAL + 15)
    assertEquals(unGuerrero.inteligencia, INT_INICIAL - 10)
  }

  @Test
  def testTrabajoLadron {
    unLadron = unLadron.heroeConStatsCalculados
    assertEquals(unLadron.velocidad, VEL_INICIAL + 10)
    assertEquals(unLadron.hp, HP_INICIAL - 5)
  }

  @Test
  def testEquiparCascoVikingo{

    // Puede
    var unTrabajo = new Heroe(hp=HP_INICIAL, fuerza=40, velocidad=VEL_INICIAL, inteligencia=INT_INICIAL, None, inventarioVacio)
    unTrabajo.equiparCasco(CascoVikingo)
    assertEquals(unTrabajo.heroeConStatsCalculados.hp, HP_INICIAL + 10)
    assertEquals(unTrabajo.heroeConStatsCalculados.velocidad, VEL_INICIAL)

    // No puede
    unTrabajo = new Heroe(hp=HP_INICIAL, fuerza=10, velocidad=VEL_INICIAL, inteligencia=INT_INICIAL, None, inventarioVacio)
    assertEquals(unTrabajo.heroeConStatsCalculados.hp, HP_INICIAL)
    assertEquals(unTrabajo.heroeConStatsCalculados.velocidad, VEL_INICIAL)
  }

  @Test
  def testEquiparPalitoMagico{

    // Mago Puede
    var valInicial = unMago.heroeConStatsCalculados.inteligencia
    unMago.equiparItemDeMano(PalitoMagico)
    assertEquals(unMago.heroeConStatsCalculados.inteligencia, valInicial + 20)

    // Ladron con <= 30
    valInicial = unLadron.heroeConStatsCalculados.inteligencia
    unLadron.equiparItemDeMano(PalitoMagico)
    assertEquals(unLadron.heroeConStatsCalculados.inteligencia, valInicial)

    // Ladron con > 30
    val unLadronInteligente = unLadron.copy(inteligencia = 40)
    valInicial = unLadronInteligente.heroeConStatsCalculados.inteligencia
    unLadronInteligente.equiparItemDeMano(PalitoMagico)
    assertEquals(unLadronInteligente.heroeConStatsCalculados.inteligencia, valInicial + 20)

    // Guerrero no Puede
    valInicial = unGuerrero.heroeConStatsCalculados.inteligencia
    unGuerrero.equiparItemDeMano(PalitoMagico)
    assertEquals(unGuerrero.heroeConStatsCalculados.inteligencia, valInicial)
  }

  @Test
  def testArmaduraEleganteSport{

    val velInicial = unMago.heroeConStatsCalculados.velocidad
    val hpInicial = unMago.heroeConStatsCalculados.hp
    unMago.equiparArmadura(ArmaduraEleganteSport)
    assertEquals(unMago.heroeConStatsCalculados.velocidad, velInicial + 30)
    assertEquals(unMago.heroeConStatsCalculados.hp, hpInicial - 30)
  }

  @Test
  def testArcoViejo{

    val valInicial = unGuerrero.heroeConStatsCalculados.fuerza
    unGuerrero.equiparItemDeMano(ArcoViejo)
    assertEquals(unGuerrero.heroeConStatsCalculados.fuerza, valInicial + 2)
  }

  @Test
  def testEscudoAntiRobo{

    // Puede
    var valInicial = unGuerrero.heroeConStatsCalculados.hp
    unGuerrero.equiparItemDeMano(EscudoAntiRobo)
    assertEquals(unGuerrero.heroeConStatsCalculados.hp, valInicial + 20)

    // Ladron no puede
    valInicial = unLadron.heroeConStatsCalculados.hp
    unLadron.equiparItemDeMano(EscudoAntiRobo)
    assertEquals(unLadron.heroeConStatsCalculados.hp, valInicial)

    // Mago no puede (Fuerza < 20)
    unMago = unMago.copy(fuerza = 10)
    assertTrue(unMago.fuerza < 30)
    valInicial = unMago.heroeConStatsCalculados.hp
    unMago.equiparItemDeMano(EscudoAntiRobo)
    assertEquals(unMago.heroeConStatsCalculados.hp, valInicial)
  }

  @Test
  def testTalismanDeDedicacion{

    // Guerrero, stat principal: Fuerza
    var incremento = unGuerrero.heroeConStatsCalculados.fuerza / 10
    var hpInicial = unGuerrero.heroeConStatsCalculados.hp
    var fuerzaInicial = unGuerrero.heroeConStatsCalculados.fuerza
    var intInicial = unGuerrero.heroeConStatsCalculados.inteligencia
    var velInicial = unGuerrero.heroeConStatsCalculados.velocidad
    unGuerrero.equiparTalisman(TalismanDeDedicacion)
    assertEquals(unGuerrero.heroeConStatsCalculados.hp, hpInicial + incremento)
    assertEquals(unGuerrero.heroeConStatsCalculados.fuerza, fuerzaInicial + incremento)
    assertEquals(unGuerrero.heroeConStatsCalculados.inteligencia, intInicial + incremento)
    assertEquals(unGuerrero.heroeConStatsCalculados.velocidad, velInicial + incremento)

    // Mago, stat principal: Inteligencia
    incremento = unMago.heroeConStatsCalculados.inteligencia / 10
    hpInicial = unMago.heroeConStatsCalculados.hp
    fuerzaInicial = unMago.heroeConStatsCalculados.fuerza
    intInicial = unMago.heroeConStatsCalculados.inteligencia
    velInicial = unMago.heroeConStatsCalculados.velocidad
    unMago.equiparTalisman(TalismanDeDedicacion)
    assertEquals(unMago.heroeConStatsCalculados.hp, hpInicial + incremento)
    assertEquals(unMago.heroeConStatsCalculados.fuerza, fuerzaInicial + incremento)
    assertEquals(unMago.heroeConStatsCalculados.inteligencia, intInicial + incremento)
    assertEquals(unMago.heroeConStatsCalculados.velocidad, velInicial + incremento)

    // Ladron, stat principal: Velocidad
    incremento = unLadron.heroeConStatsCalculados.velocidad / 10
    hpInicial = unLadron.heroeConStatsCalculados.hp
    fuerzaInicial = unLadron.heroeConStatsCalculados.fuerza
    intInicial = unLadron.heroeConStatsCalculados.inteligencia
    velInicial = unLadron.heroeConStatsCalculados.velocidad
    unLadron.equiparTalisman(TalismanDeDedicacion)
    assertEquals(unLadron.heroeConStatsCalculados.hp, hpInicial + incremento)
    assertEquals(unLadron.heroeConStatsCalculados.fuerza, fuerzaInicial + incremento)
    assertEquals(unLadron.heroeConStatsCalculados.inteligencia, intInicial + incremento)
    assertEquals(unLadron.heroeConStatsCalculados.velocidad, velInicial + incremento)
  }

  @Test
  def testTalismanDelMinimalismo{
    val hpInicial = unGuerrero.heroeConStatsCalculados.hp
    unGuerrero.equiparTalisman(TalismanDeMinimalismo)
    assertEquals(hpInicial + 50, unGuerrero.heroeConStatsCalculados.hp)
    unGuerrero.equiparItemDeMano(ArcoViejo)
    assertEquals(hpInicial + 40, unGuerrero.heroeConStatsCalculados.hp)
  }

  @Test
  def testVinchaDelBufaloDelAgua{

    var otroDesempleado = unDesempleado.copy(inteligencia = 20, fuerza = 30)
    var hpInicial = otroDesempleado.heroeConStatsCalculados.hp
    var fuerzaInicial = otroDesempleado.heroeConStatsCalculados.fuerza
    var intInicial = otroDesempleado.heroeConStatsCalculados.inteligencia
    var velInicial = otroDesempleado.heroeConStatsCalculados.velocidad
    otroDesempleado.equiparCasco(VinchaDelBufaloDeAgua)
    assertEquals(intInicial + 30, otroDesempleado.heroeConStatsCalculados.inteligencia)
    assertEquals(hpInicial, otroDesempleado.heroeConStatsCalculados.hp)
    assertEquals(fuerzaInicial, otroDesempleado.heroeConStatsCalculados.fuerza)
    assertEquals(velInicial, otroDesempleado.heroeConStatsCalculados.velocidad)

    otroDesempleado = unDesempleado.copy(inteligencia = 40, fuerza = 10, inventario = inventarioVacio)
    hpInicial = otroDesempleado.heroeConStatsCalculados.hp
    fuerzaInicial = otroDesempleado.heroeConStatsCalculados.fuerza
    intInicial = otroDesempleado.heroeConStatsCalculados.inteligencia
    velInicial = otroDesempleado.heroeConStatsCalculados.velocidad
    otroDesempleado.equiparCasco(VinchaDelBufaloDeAgua)
    assertEquals(intInicial, otroDesempleado.heroeConStatsCalculados.inteligencia)
    assertEquals(hpInicial + 10, otroDesempleado.heroeConStatsCalculados.hp)
    assertEquals(fuerzaInicial + 10, otroDesempleado.heroeConStatsCalculados.fuerza)
    assertEquals(velInicial + 10, otroDesempleado.heroeConStatsCalculados.velocidad)
  }

  @Test
  def testTalismanMaldito{

    unMago.equiparTalisman(TalismanMaldito)
    assertEquals(1, unMago.heroeConStatsCalculados.inteligencia)
    assertEquals(1, unMago.heroeConStatsCalculados.hp)
    assertEquals(1, unMago.heroeConStatsCalculados.fuerza)
    assertEquals(1, unMago.heroeConStatsCalculados.velocidad)
  }

  @Test
  def testEspadaDeLaVida{
    val otroGerrero = unGuerrero.copy(fuerza = 20, hp = 50)
    otroGerrero.equiparItemDeMano(EspadaDeLaVida)
    assertEquals(unGuerrero.heroeConStatsCalculados.hp, unGuerrero.heroeConStatsCalculados.fuerza)
  }

  @Test
  def testEquiparSoloUnItem{
    val otroDesempleado = unDesempleado.copy(fuerza = 60)
    otroDesempleado.equiparCasco(VinchaDelBufaloDeAgua)
    assertEquals(VinchaDelBufaloDeAgua, otroDesempleado.heroeConStatsCalculados.inventario.cabeza.get)
    otroDesempleado.equiparCasco(CascoVikingo)
    assertEquals(CascoVikingo, otroDesempleado.heroeConStatsCalculados.inventario.cabeza.get)
  }

  @Test
  def testStatsSiemprePositivos{
    val magoDebil = unMago.copy(fuerza = 5)
    assertEquals(1, magoDebil.heroeConStatsCalculados.fuerza)
    val guerreroTonto = unGuerrero.copy(inteligencia = 5)
    assertEquals(1, guerreroTonto.heroeConStatsCalculados.inteligencia)
    val ladronDebil = unLadron.copy(hp = 3)
    assertEquals(1, ladronDebil.heroeConStatsCalculados.hp)
  }
}
