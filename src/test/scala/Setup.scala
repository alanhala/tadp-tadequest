import org.junit.Before

/**
  * Created by f on 01/07/16.
  */
class Setup {

  var heroePelado: Heroe = null
  var heroeSinTrabajo: Heroe = null
  var guerrero: Heroe = null
  var mago: Heroe = null
  var ladron: Heroe = null


  @Before
  def setup = {
    heroePelado = new Heroe(new Stats(0, 0, 0, 0), None, new Inventario(None, None))
    heroeSinTrabajo = new Heroe(new Stats(100, 100, 100, 100), None, new Inventario(None, None))
    guerrero = new Heroe(new Stats(100,100,100,100),Option(Guerrero),new Inventario(None, None))
    mago = new Heroe(new Stats(100,100,100,100),Option(Mago),new Inventario(None, None))
    ladron = new Heroe(new Stats(100,100,100,100),Option(Ladron),new Inventario(None, None))

  }

}
