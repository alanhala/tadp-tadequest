import org.junit.Before
import org.junit.Test
import org.junit.Assert._


class HayEquipo extends Setup{

  var equipo: Equipo = null
  var equipoVacio: Equipo = null
  var equipoTrabajadores: Equipo = null

  @Before
  override def setup = {
    super.setup
    equipo = new Equipo(100,"equipo",List(heroePelado,heroeSinTrabajo))
    equipoVacio = new Equipo(100,"equipoVacio",List())
    equipoTrabajadores = new Equipo(100,"equipoTrabajadores",List(guerrero,mago,ladron))
  }

  @Test
  def mejorHeroeSegun(){

    assertTrue(equipo.mejorHeroeSegun{ heroe => heroe.statsFinales.hp }.contains(heroeSinTrabajo))
    assertFalse(equipo.mejorHeroeSegun{ heroe => heroe.statsFinales.hp }.contains(heroePelado))
    assertTrue(equipo.mejorHeroeSegun{ heroe => heroe.statsFinales.hp^(-1) }.contains(heroePelado))
    assertFalse(equipo.mejorHeroeSegun{ heroe => heroe.statsFinales.hp^(-1) }.contains(heroeSinTrabajo))
    assertTrue(equipoVacio.mejorHeroeSegun(heroe=>heroe.statsFinales.fuerza).equals(None))
    assertTrue(equipoTrabajadores.mejorHeroeSegun{heroe => heroe.statsFinales.fuerza}.contains(guerrero))
    assertFalse(equipoTrabajadores.mejorHeroeSegun{heroe => heroe.statsFinales.fuerza}.contains(mago))
    assertFalse(equipoTrabajadores.mejorHeroeSegun{heroe => heroe.statsFinales.fuerza}.contains(ladron))
  }


  @Test
  def obtenerItem(){
    assertEquals(105,equipo.obtenerItem(TalismanMaldito).oro)
    assertEquals(100,equipo.agregarMiembro(mago).obtenerItem(PalitoMagico).oro)
    assertEquals(140,equipo.agregarMiembro(mago).obtenerItem(PalitoMagico).heroes.find(heroe=>heroe.equals(mago.equiparItem(PalitoMagico))).get.statsFinales.inteligencia)
  }


  @Test
  def obtenerMiembro(){
    assertTrue(equipoVacio.heroes.isEmpty)
    assertTrue(equipoVacio.agregarMiembro(mago).heroes.contains(mago))
  }


  @Test
  def reemplazarMiembro(){

    assertFalse(equipo.heroes.contains(mago))
    assertTrue(equipo.remplazar(heroePelado,mago).heroes.contains(mago))
    assertFalse(equipo.remplazar(heroePelado,mago).heroes.contains(heroePelado))
  }

  @Test
  def lider(){
    assertEquals(mago,equipo.agregarMiembro(mago).lider.get)
    var nuevoLadron:Heroe = ladron.copy(stats=ladron.stats.copy(velocidad=130))
    var equipoTrabajadoresModificado:Equipo = equipoTrabajadores.agregarMiembro(nuevoLadron)
    assertEquals(nuevoLadron,equipoTrabajadoresModificado.lider.get)
    var nuevoMago:Heroe = mago.copy(stats=mago.stats.copy(inteligencia=130))
    equipoTrabajadoresModificado = equipoTrabajadoresModificado.agregarMiembro(nuevoMago)
    assertEquals(None,equipoTrabajadoresModificado.lider)
  }


}
