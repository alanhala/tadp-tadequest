case class Taberna(tablon: List[Mision]) {

  def mejorMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Option[Mision] =
    this.mejorMisionDeLista(equipo, tablon, criterio)


  def mejorMisionDeLista(equipo: Equipo, misionLista: List[Mision], criterio: (Equipo, Equipo) => Boolean): Option[Mision] = {

    misionLista.filter(m => !m.serRealizada(equipo).falloEnMision)
      .foldLeft[Option[Mision]](None)((mejorMision, misionActual) => {

      mejorMision.fold[Option[Mision]](Some(misionActual))(m =>
        if(criterio(m.serRealizada(equipo).get, misionActual.serRealizada(equipo).get)) mejorMision else Some(misionActual)
      )
    })
  }


  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): EstadoEquipo = {

    var listaMisiones = tablon
    var estadoEquipo: EstadoEquipo = EquipoEnAccion(equipo)
    var proximaMision = this.mejorMisionDeLista(equipo, listaMisiones, criterio)

    while(proximaMision.nonEmpty){
      estadoEquipo = proximaMision.get.serRealizada(estadoEquipo.get)

      listaMisiones = listaMisiones.filter(m => m != proximaMision.get)

      proximaMision = this.mejorMisionDeLista(estadoEquipo.get, listaMisiones, criterio)
    }

    estadoEquipo
  }

}
