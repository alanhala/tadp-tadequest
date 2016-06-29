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

    val criterioMision: (Mision, Mision) => Boolean =
      (mision1, mision2) =>   criterio(mision1.serRealizada(equipo).get, mision2.serRealizada(equipo).get)

    val misionesOrdenadas = tablon.filter(m => !m.serRealizada(equipo).falloEnMision).sortWith(criterioMision)

    misionesOrdenadas.foldLeft[EstadoEquipo](EquipoEnAccion(equipo))((estadoEquipo, misionActual) => {
      estadoEquipo.fold[EstadoEquipo](estadoEquipo)(e => misionActual.serRealizada(e))
    })
  }

}
