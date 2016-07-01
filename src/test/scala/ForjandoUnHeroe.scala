import org.junit.Before
import org.junit.Test
import org.junit.Assert._


class ForjandoUnHeroe extends Setup  {

  @Test
  def andaBienElSetup = {
    assertEquals(new Stats(0,0,0,0), heroePelado.stats)
  }
  @Test
  def statsNuncaMenoresA1 = {
    assertEquals(new Stats(1,1,1,1), heroePelado.statsFinales)
  }

  @Test
  def convertirEnGuerreroModificaStats = {

    var heroeConTrabajo:Heroe = heroeSinTrabajo.cambiarTrabajo(Option(Guerrero))
    assertEquals(heroeConTrabajo.statsFinales.hp,110 )
    assertEquals(heroeConTrabajo.statsFinales.fuerza, 115)
    assertEquals(heroeConTrabajo.statsFinales.velocidad,100)
    assertEquals(heroeConTrabajo.statsFinales.inteligencia, 90)
    assertEquals(heroeConTrabajo.statPrincipal.get,Fuerza)
  }

  @Test
  def convertirEnMagoModificaStats = {

    var heroeConTrabajo:Heroe = heroeSinTrabajo.cambiarTrabajo(Option(Mago))
    assertEquals(heroeConTrabajo.statsFinales.hp,100 )
    assertEquals(heroeConTrabajo.statsFinales.fuerza, 80)
    assertEquals(heroeConTrabajo.statsFinales.velocidad,100)
    assertEquals(heroeConTrabajo.statsFinales.inteligencia,120)
    assertEquals(heroeConTrabajo.statPrincipal.get,Inteligencia)
  }

  @Test
  def convertirEnLadronModificaStats = {

    var heroeConTrabajo:Heroe = heroeSinTrabajo.cambiarTrabajo(Option(Ladron))
    assertEquals(heroeConTrabajo.statsFinales.hp,95 )
    assertEquals(heroeConTrabajo.statsFinales.fuerza, 100)
    assertEquals(heroeConTrabajo.statsFinales.velocidad,110)
    assertEquals(heroeConTrabajo.statsFinales.inteligencia,100)
    assertEquals(heroeConTrabajo.statPrincipal.get,Velocidad)
  }

  @Test
  def equiparSombreroVikingo() = {
    assertEquals(110, heroeSinTrabajo.equiparItem(CascoVikingo).statsFinales.hp)
    assertEquals(1, heroePelado.equiparItem(CascoVikingo).statsFinales.hp)
  }
  @Test
  def equiparPalitoMagico() = {
    assertEquals(140, mago.equiparItem(PalitoMagico).statsFinales.inteligencia)
    assertEquals(120, ladron.equiparItem(PalitoMagico).statsFinales.inteligencia)
    var ladronBobo:Heroe = ladron.copy(ladron.stats.copy(inteligencia=30))
    assertEquals(30, ladronBobo.equiparItem(PalitoMagico).statsFinales.inteligencia)
  }
  @Test
  def equiparArmaduraEleganteSport() = {
    assertEquals(70, heroeSinTrabajo.equiparItem(ArmaduraEleganteSport).statsFinales.hp)
    assertEquals(130, heroeSinTrabajo.equiparItem(ArmaduraEleganteSport).statsFinales.velocidad)
  }
  @Test
  def equiparArcoViejo() = {
    assertEquals(102, heroeSinTrabajo.equiparItem(ArcoViejo).statsFinales.fuerza)
  }
  @Test
  def equiparEscudoAntiRobo() = {
    assertEquals(120, heroeSinTrabajo.equiparItem(EscudoAntiRobo).statsFinales.hp)
    assertEquals(95, ladron.equiparItem(EscudoAntiRobo).statsFinales.hp)
    var heroeDebilucho:Heroe = heroeSinTrabajo.copy(heroeSinTrabajo.stats.copy(fuerza=19))
    assertEquals(100, heroeDebilucho.equiparItem(EscudoAntiRobo).statsFinales.hp)
  }
  @Test
  def equiparTalismanDeDedicacion() = {

    assertEquals(110+115*0.1 ,guerrero.equiparItem(TalismanDeDedicacion).statsFinales.hp,0.5)
    assertEquals(115+115*0.1,guerrero.equiparItem(TalismanDeDedicacion).statsFinales.fuerza,0.5)
    assertEquals(100+115*0.1,guerrero.equiparItem(TalismanDeDedicacion).statsFinales.velocidad,0.5)
    assertEquals(90+115*0.1,guerrero.equiparItem(TalismanDeDedicacion).statsFinales.inteligencia,0.5)

    assertEquals(100+120*0.1 ,mago.equiparItem(TalismanDeDedicacion).statsFinales.hp,0.5)
    assertEquals(80+120*0.1,mago.equiparItem(TalismanDeDedicacion).statsFinales.fuerza,0.5)
    assertEquals(100+120*0.1,mago.equiparItem(TalismanDeDedicacion).statsFinales.velocidad,0.5)
    assertEquals(120+120*0.1,mago.equiparItem(TalismanDeDedicacion).statsFinales.inteligencia,0.5)

    assertEquals(95+110*0.1 ,ladron.equiparItem(TalismanDeDedicacion).statsFinales.hp,0.5)
    assertEquals(100+110*0.1,ladron.equiparItem(TalismanDeDedicacion).statsFinales.fuerza,0.5)
    assertEquals(110+110*0.1,ladron.equiparItem(TalismanDeDedicacion).statsFinales.velocidad,0.5)
    assertEquals(100+110*0.1,ladron.equiparItem(TalismanDeDedicacion).statsFinales.inteligencia,0.5)
  }

  @Test
  def equiparTalismanDeMinimalismo() = {
    assertEquals(150, heroeSinTrabajo.equiparItem(TalismanDeMinimalismo).statsFinales.hp)
    assertEquals(140, heroeSinTrabajo.equiparItem(TalismanDeMinimalismo).equiparItem(EspadaDeLaVida).statsFinales.hp)
  }

  @Test
  def equiparVinchaDelBufaloDeAgua() = {
    assertEquals(110, heroeSinTrabajo.equiparItem(VinchaDelBufaloDeAgua).statsFinales.hp)
    assertEquals(110, heroeSinTrabajo.equiparItem(VinchaDelBufaloDeAgua).statsFinales.fuerza)
    assertEquals(110, heroeSinTrabajo.equiparItem(VinchaDelBufaloDeAgua).statsFinales.velocidad)
    assertEquals(100, heroeSinTrabajo.equiparItem(VinchaDelBufaloDeAgua).statsFinales.inteligencia)
    var heroeSinTrabajoConMasFuerzaQueInteligencia:Heroe = heroeSinTrabajo.copy(heroeSinTrabajo.stats.copy(inteligencia = 90))
    assertEquals(120, heroeSinTrabajoConMasFuerzaQueInteligencia.equiparItem(VinchaDelBufaloDeAgua).statsFinales.inteligencia)
    assertEquals(100, heroeSinTrabajo.cambiarTrabajo(None).equiparItem(VinchaDelBufaloDeAgua).statsFinales.inteligencia)
  }
  @Test
  def equiparTalismanMaldito() = {
    assertEquals(1, heroeSinTrabajo.equiparItem(TalismanMaldito).statsFinales.hp)
    assertEquals(1, heroeSinTrabajo.equiparItem(TalismanMaldito).statsFinales.fuerza)
    assertEquals(1, heroeSinTrabajo.equiparItem(TalismanMaldito).statsFinales.velocidad)
    assertEquals(1, heroeSinTrabajo.equiparItem(TalismanMaldito).statsFinales.inteligencia)
  }
  @Test
  def equiparEspadaDeLaVida() = {
    var heroeConDistintoHpYFuerza:Heroe = heroeSinTrabajo.copy(heroeSinTrabajo.stats.copy(hp=120,fuerza = 90))
    assertEquals(heroeConDistintoHpYFuerza.equiparItem(EspadaDeLaVida).statsFinales.fuerza, heroeConDistintoHpYFuerza.equiparItem(EspadaDeLaVida).statsFinales.hp)
  }
  @Test
  def puedeUsarUnSoloSombreroOCasco() = {
    var heroeSinTrabajoConMasFuerzaQueInteligencia:Heroe = heroeSinTrabajo.copy(heroeSinTrabajo.stats.copy(inteligencia = 90))
    assertEquals(120, heroeSinTrabajoConMasFuerzaQueInteligencia.equiparItem(VinchaDelBufaloDeAgua).statsFinales.inteligencia)
    assertEquals(90, heroeSinTrabajoConMasFuerzaQueInteligencia.equiparItem(VinchaDelBufaloDeAgua).equiparItem(CascoVikingo).statsFinales.inteligencia)
    assertEquals(110, heroeSinTrabajoConMasFuerzaQueInteligencia.equiparItem(VinchaDelBufaloDeAgua).equiparItem(CascoVikingo).statsFinales.hp)
  }
  @Test
  def puedeUsarUnaSolaArmaduraOVestido() = {
    assertEquals(70, heroeSinTrabajo.equiparItem(ArmaduraEleganteSport).statsFinales.hp)
    assertEquals(130, heroeSinTrabajo.equiparItem(ArmaduraEleganteSport).statsFinales.velocidad)
    assertEquals(70, heroeSinTrabajo.equiparItem(ArmaduraEleganteSport).equiparItem(ArmaduraEleganteSport).statsFinales.hp)
    assertEquals(130, heroeSinTrabajo.equiparItem(ArmaduraEleganteSport).equiparItem(ArmaduraEleganteSport).statsFinales.velocidad)
  }
  @Test
  def puedeUsarSoloDosArmasQueOcupenUnaManoCadaUna() = {
    assertEquals(140, mago.equiparItem(PalitoMagico).statsFinales.inteligencia)
    assertEquals(160, mago.equiparItem(PalitoMagico).equiparItem(PalitoMagico).statsFinales.inteligencia)
    assertNotEquals(180, mago.equiparItem(PalitoMagico).equiparItem(PalitoMagico).equiparItem(PalitoMagico).statsFinales.inteligencia)
    assertEquals(160, mago.equiparItem(PalitoMagico).equiparItem(PalitoMagico).equiparItem(PalitoMagico).statsFinales.inteligencia)
  }
  @Test
  def puedeUsarSoloUnArmaQueOcupeDosManos() = {
    assertEquals(102, heroeSinTrabajo.equiparItem(ArcoViejo).statsFinales.fuerza)
    assertNotEquals(104, heroeSinTrabajo.equiparItem(ArcoViejo).equiparItem(ArcoViejo).statsFinales.fuerza)
    assertEquals(102, heroeSinTrabajo.equiparItem(ArcoViejo).equiparItem(ArcoViejo).statsFinales.fuerza)
  }


}
