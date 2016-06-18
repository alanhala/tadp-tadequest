/**
  * Created by alanhala on 6/18/16.
  */
class Equipo(oro: Integer, nombre: String, var heroes: List[Heroe]) {
  def mejorHeroeSegun(criterio: Heroe => Integer): Heroe =
    heroes.maxBy(criterio(_))

  def obtenerItem(item: Item) =
    heroes.maxBy(_.incrementoMainStat(item))

  def agregarMiembro(heroe: Heroe) =
    this.heroes = heroe :: this.heroes
}