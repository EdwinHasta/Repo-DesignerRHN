package Administrar;

import Entidades.Cargos;
import InterfaceAdministrar.AdministrarCargosInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import Persistencia.PersistenciaCargos;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarCargos implements  AdministrarCargosInterface{

    @EJB
    PersistenciaCargosInterface persistenciaCargos;

    @Override
    public List<Cargos> Cargos() {
        List<Cargos> listaCargos;
        listaCargos = persistenciaCargos.buscarCargos();
        return listaCargos;
    }

    @Override
    public List<Cargos> lovCargos() {
        return persistenciaCargos.buscarCargos();
    }
}
