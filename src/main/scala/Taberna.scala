case class Taberna(tablon: List[Mision]) {

  def mejorMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Option[Mision] = {

    tablon.filter(m => !m.serRealizada(equipo).falloEnMision)
              .foldLeft[Option[Mision]](None)((mejorMision, misionActual) => {

                mejorMision.fold[Option[Mision]](Some(misionActual))(m =>
                  if(criterio(m.serRealizada(equipo).get, misionActual.serRealizada(equipo).get)) mejorMision else Some(misionActual)
                )
    })
  }


  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Equipo = {

    var equipoNuevo = equipo
    var proximaMision: Try[Mision] = this.mejorMision(equipo, criterio)
    var taberna: Taberna = this

    while(proximaMision.isSuccess){
      equipoNuevo = proximaMision.get.serRealizada(equipoNuevo).get

      taberna = taberna.copy(tablon = taberna.tablon.filter(m => m != proximaMision.get))

      proximaMision = taberna.mejorMision(equipoNuevo, criterio)
    }

    equipoNuevo
  }

}
