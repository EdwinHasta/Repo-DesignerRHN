package Administrar;

import Entidades.Ciudades;
import Entidades.Departamentos;
import InterfaceAdministrar.AdministrarCiudadesInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarCiudades implements AdministrarCiudadesInterface {

    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    @EJB
    PersistenciaDepartamentosInterface persistenciaDepartamentos;

    public List<Departamentos> Departamentos() {
        List<Departamentos> listaDepartamentos;
        listaDepartamentos = persistenciaDepartamentos.departamentos();
        return listaDepartamentos;
    }

    public List<Ciudades> Ciudades() {
        List<Ciudades> listaCiudades;
        listaCiudades = persistenciaCiudades.ciudades();
        return listaCiudades;
    }
}
