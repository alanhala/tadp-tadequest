/**
  * Created by Zaxtor on 17/06/2016.
  */

import org.junit.{Before, Test}
import org.junit.Assert._

/*
class InventarioTest {

  var unGuerrero: Heroe = _
  var unMago: Heroe = _
  var unLadron: Heroe = _
  var unDesempleado: Heroe = _

  def HP_INICIAL: Int = 100
  def FUERZA_INICIAL: Int = 30
  def VEL_INICIAL: Int = 30
  def INT_INICIAL: Int = 30
  def STATS_INICIAL:Stats = new Stats(hp=HP_INICIAL, fuerza=FUERZA_INICIAL, velocidad=VEL_INICIAL, inteligencia=INT_INICIAL)
  def inventarioVacio = new Inventario(None, None)

  @Before
  def setUp {

    unDesempleado = new Heroe(STATS_INICIAL, None , inventarioVacio)
    unGuerrero = unDesempleado.copy(trabajo = Some(Guerrero), inventario = inventarioVacio)
    unMago = unDesempleado.copy(trabajo = Some(Mago), inventario = inventarioVacio)
    unLadron = unDesempleado.copy(trabajo = Some(Ladron), inventario = inventarioVacio)

  }

  @Test
  def testTrabajoMago {
    assertEquals(unMago.statsFinales.inteligencia, INT_INICIAL + 20)
    assertEquals(unMago.statsFinales.fuerza, FUERZA_INICIAL - 20)
  }

  @Test
  def testTrabajoGuerrero {
    assertEquals(unGuerrero.statsFinales.hp, HP_INICIAL + 10)
    assertEquals(unGuerrero.statsFinales.fuerza, FUERZA_INICIAL + 15)
    assertEquals(unGuerrero.statsFinales.inteligencia, INT_INICIAL - 10)
  }

  @Test
  def testTrabajoLadron {
    assertEquals(unLadron.statsFinales.velocidad, VEL_INICIAL + 10)
    assertEquals(unLadron.statsFinales.hp, HP_INICIAL - 5)
  }

  @Test
  def testEquiparCascoVikingo{

    // Puede
    var unTrabajo = new Heroe(stats = STATS_INICIAL.copy(fuerza=40), None, inventarioVacio)
    unTrabajo.equiparCasco(CascoVikingo)
    assertEquals(unTrabajo.statsFinales.hp, HP_INICIAL + 10)
    assertEquals(unTrabajo.statsFinales.velocidad, VEL_INICIAL)

    // No puede
    unTrabajo = new Heroe(stats = STATS_INICIAL.copy(fuerza=10), None, inventarioVacio)
    assertEquals(unTrabajo.statsFinales.hp, HP_INICIAL)
    assertEquals(unTrabajo.statsFinales.velocidad, VEL_INICIAL)
  }

  @Test
  def testEquiparPalitoMagico{

    // Mago Puede
    var valInicial = unMago.statsFinales.inteligencia
    unMago.equiparItemDeMano(PalitoMagico)
    assertEquals(unMago.statsFinales.inteligencia, valInicial + 20)

    // Ladron con <= 30
    valInicial = unLadron.statsFinales.inteligencia
    unLadron.equiparItemDeMano(PalitoMagico)
    assertEquals(unLadron.statsFinales.inteligencia, valInicial)

    // Ladron con > 30
    val unLadronInteligente = unLadron.copy(stats = STATS_INICIAL.copy(inteligencia = 40))
    valInicial = unLadronInteligente.statsFinales.inteligencia
    unLadronInteligente.equiparItemDeMano(PalitoMagico)
    assertEquals(unLadronInteligente.statsFinales.inteligencia, valInicial + 20)

    // Guerrero no Puede
    valInicial = unGuerrero.statsFinales.inteligencia
    unGuerrero.equiparItemDeMano(PalitoMagico)
    assertEquals(unGuerrero.statsFinales.inteligencia, valInicial)
  }

  @Test
  def testArmaduraEleganteSport{

    val velInicial = unMago.statsFinales.velocidad
    val hpInicial = unMago.statsFinales.hp
    unMago.equiparArmadura(ArmaduraEleganteSport)
    assertEquals(unMago.statsFinales.velocidad, velInicial + 30)
    assertEquals(unMago.statsFinales.hp, hpInicial - 30)
  }

  @Test
  def testArcoViejo{

    val valInicial = unGuerrero.statsFinales.fuerza
    unGuerrero.equiparItemDeMano(ArcoViejo)
    assertEquals(unGuerrero.statsFinales.fuerza, valInicial + 2)
  }

  @Test
  def testEscudoAntiRobo{

    // Puede
    var valInicial = unGuerrero.statsFinales.hp
    unGuerrero.equiparItemDeMano(EscudoAntiRobo)
    assertEquals(unGuerrero.statsFinales.hp, valInicial + 20)

    // Ladron no puede
    valInicial = unLadron.statsFinales.hp
    unLadron.equiparItemDeMano(EscudoAntiRobo)
    assertEquals(unLadron.statsFinales.hp, valInicial)

    // Mago no puede (Fuerza < 20)
    unMago = unMago.copy(stats = STATS_INICIAL.copy(fuerza = 10))
    assertTrue(unMago.statsFinales.fuerza < 30)
    valInicial = unMago.statsFinales.hp
    unMago.equiparItemDeMano(EscudoAntiRobo)
    assertEquals(unMago.statsFinales.hp, valInicial)
  }

  @Test
  def testTalismanDeDedicacion{

    // Guerrero, stat principal: Fuerza
    var incremento = unGuerrero.statsFinales.fuerza / 10
    var hpInicial = unGuerrero.statsFinales.hp
    var fuerzaInicial = unGuerrero.statsFinales.fuerza
    var intInicial = unGuerrero.statsFinales.inteligencia
    var velInicial = unGuerrero.statsFinales.velocidad
    unGuerrero.equiparTalisman(TalismanDeDedicacion)
    assertEquals(unGuerrero.statsFinales.hp, hpInicial + incremento)
    assertEquals(unGuerrero.statsFinales.fuerza, fuerzaInicial + incremento)
    assertEquals(unGuerrero.statsFinales.inteligencia, intInicial + incremento)
    assertEquals(unGuerrero.statsFinales.velocidad, velInicial + incremento)

    // Mago, stat principal: Inteligencia
    incremento = unMago.statsFinales.inteligencia / 10
    hpInicial = unMago.statsFinales.hp
    fuerzaInicial = unMago.statsFinales.fuerza
    intInicial = unMago.statsFinales.inteligencia
    velInicial = unMago.statsFinales.velocidad
    unMago.equiparTalisman(TalismanDeDedicacion)
    assertEquals(unMago.statsFinales.hp, hpInicial + incremento)
    assertEquals(unMago.statsFinales.fuerza, fuerzaInicial + incremento)
    assertEquals(unMago.statsFinales.inteligencia, intInicial + incremento)
    assertEquals(unMago.statsFinales.velocidad, velInicial + incremento)

    // Ladron, stat principal: Velocidad
    incremento = unLadron.statsFinales.velocidad / 10
    hpInicial = unLadron.statsFinales.hp
    fuerzaInicial = unLadron.statsFinales.fuerza
    intInicial = unLadron.statsFinales.inteligencia
    velInicial = unLadron.statsFinales.velocidad
    unLadron.equiparTalisman(TalismanDeDedicacion)
    assertEquals(unLadron.statsFinales.hp, hpInicial + incremento)
    assertEquals(unLadron.statsFinales.fuerza, fuerzaInicial + incremento)
    assertEquals(unLadron.statsFinales.inteligencia, intInicial + incremento)
    assertEquals(unLadron.statsFinales.velocidad, velInicial + incremento)
  }

  @Test
  def testTalismanDelMinimalismo{
    val hpInicial = unGuerrero.statsFinales.hp
    unGuerrero.equiparTalisman(TalismanDeMinimalismo)
    assertEquals(hpInicial + 50, unGuerrero.statsFinales.hp)
    unGuerrero.equiparItemDeMano(ArcoViejo)
    assertEquals(hpInicial + 40, unGuerrero.statsFinales.hp)
  }

  @Test
  def testVinchaDelBufaloDelAgua{

    var otroDesempleado = unDesempleado.copy(stats = STATS_INICIAL.copy(inteligencia = 20, fuerza = 30))
    var hpInicial = otroDesempleado.statsFinales.hp
    var fuerzaInicial = otroDesempleado.statsFinales.fuerza
    var intInicial = otroDesempleado.statsFinales.inteligencia
    var velInicial = otroDesempleado.statsFinales.velocidad
    otroDesempleado.equiparCasco(VinchaDelBufaloDeAgua)
    assertEquals(intInicial + 30, otroDesempleado.statsFinales.inteligencia)
    assertEquals(hpInicial, otroDesempleado.statsFinales.hp)
    assertEquals(fuerzaInicial, otroDesempleado.statsFinales.fuerza)
    assertEquals(velInicial, otroDesempleado.statsFinales.velocidad)

    otroDesempleado = unDesempleado.copy(stats = STATS_INICIAL.copy(inteligencia = 40, fuerza = 10), inventario = inventarioVacio)
    hpInicial = otroDesempleado.statsFinales.hp
    fuerzaInicial = otroDesempleado.statsFinales.fuerza
    intInicial = otroDesempleado.statsFinales.inteligencia
    velInicial = otroDesempleado.statsFinales.velocidad
    otroDesempleado.equiparCasco(VinchaDelBufaloDeAgua)
    assertEquals(intInicial, otroDesempleado.statsFinales.inteligencia)
    assertEquals(hpInicial + 10, otroDesempleado.statsFinales.hp)
    assertEquals(fuerzaInicial + 10, otroDesempleado.statsFinales.fuerza)
    assertEquals(velInicial + 10, otroDesempleado.statsFinales.velocidad)
  }

  @Test
  def testTalismanMaldito{

    unMago.equiparTalisman(TalismanMaldito)
    assertEquals(1, unMago.statsFinales.inteligencia)
    assertEquals(1, unMago.statsFinales.hp)
    assertEquals(1, unMago.statsFinales.fuerza)
    assertEquals(1, unMago.statsFinales.velocidad)
  }

  @Test
  def testEspadaDeLaVida{
    val otroGerrero = unGuerrero.copy(stats = STATS_INICIAL.copy(fuerza = 20, hp = 50))
    otroGerrero.equiparItemDeMano(EspadaDeLaVida)
    assertEquals(unGuerrero.statsFinales.hp, unGuerrero.statsFinales.fuerza)
  }

  @Test
  def testEquiparSoloUnItem{
    val otroDesempleado = unDesempleado.copy(stats = STATS_INICIAL.copy(fuerza = 60))
    otroDesempleado.equiparCasco(VinchaDelBufaloDeAgua)
    assertEquals(VinchaDelBufaloDeAgua, otroDesempleado.inventario.cabeza.get)
    otroDesempleado.equiparCasco(CascoVikingo)
    assertEquals(CascoVikingo, otroDesempleado.inventario.cabeza.get)
  }

  @Test
  def testStatsSiemprePositivos{
    val magoDebil = unMago.copy(stats = STATS_INICIAL.copy(fuerza = 5))
    assertEquals(1, magoDebil.statsFinales.fuerza)
    val guerreroTonto = unGuerrero.copy(stats = STATS_INICIAL.copy(inteligencia = 5))
    assertEquals(1, guerreroTonto.statsFinales.inteligencia)
    val ladronDebil = unLadron.copy(stats = STATS_INICIAL.copy(hp = 3))
    assertEquals(1, ladronDebil.statsFinales.hp)
  }

}

*/