import org.junit.Before
import org.junit.Test
import org.junit.Assert._

/**
  * Created by f on 01/07/16.
  */
class Misiones extends HayEquipo {

  var mision:Mision=null

  @Before
  override def setup = {
    super.setup

  }

  @Test
  def hacenMisionPelearContraMonstruosYObtienen5DeOro  {
    mision= new Mision(List(pelearContraMonstruo),equipo => equipo.obtenerItem(TalismanMaldito))
    assertEquals(105, mision.serRealizada(equipoTrabajadores).get.oro)
  }
  @Test
  def hacenMisionPelearContraMonstruosYEquipaConPalitoMagicoAlMago  {
    mision= new Mision(List(pelearContraMonstruo),equipo => equipo.obtenerItem(PalitoMagico))
    assertEquals(100,mision.serRealizada(equipo.agregarMiembro(mago)).get.oro)
    assertEquals(140,mision.serRealizada(equipo.agregarMiembro(mago)).get.heroes.find(heroe=>heroe.equals(mago.equiparItem(PalitoMagico))).get.statsFinales.inteligencia)
  }
  @Test
  def hacenMisionPelearContraMonstruosEquipoVacioYFalla  {
    mision= new Mision(List(pelearContraMonstruo),equipo => equipo.obtenerItem(PalitoMagico))
    assertTrue(mision.serRealizada(equipoVacio).falloEnMision)
  }
  @Test
  def hacenMisionForzarPuerta{

    var mision = new Mision(List(forzarPuerta), equipo => equipo.quitarMiembro(heroePelado))
    assertEquals(101,mision.serRealizada(equipo).get.heroes.head.statsFinales.fuerza)
    assertEquals(95,mision.serRealizada(equipo).get.heroes.head.statsFinales.hp)
    assertFalse(mision.serRealizada(equipo).get.heroes.contains(heroePelado))
    assertTrue(mision.serRealizada(equipoVacio).falloEnMision)
  }

  @Test
  def hacenMisionRobarTalisman{

    var mision = new Mision(List(new robarTalisman(TalismanMaldito)), equipo => equipo.obtenerItem(TalismanMaldito))
    var ladronLider:Heroe = new Heroe(new Stats(100,100,200,100),Option(Ladron),new Inventario(None, None))
    var equipoTrabajadoresModificado:Equipo = equipoTrabajadores.quitarMiembro(ladron).agregarMiembro(ladronLider)
    assertEquals(110, mision.serRealizada(equipoTrabajadoresModificado).get.oro)
    assertTrue(mision.serRealizada(equipoVacio).falloEnMision)
    //assertTrue(mision.serRealizada(equipoTrabajadores).falloEnMision)
  }
}