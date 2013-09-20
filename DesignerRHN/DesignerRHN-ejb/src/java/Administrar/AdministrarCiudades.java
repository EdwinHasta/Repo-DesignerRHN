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
    private Ciudades c;

    public List<Departamentos> Departamentos() {
        List<Departamentos> listaDepartamentos;
        listaDepartamentos = persistenciaDepartamentos.departamentos();
        return listaDepartamentos;
    }

    @Override
    public List<Ciudades> Ciudades() {
        List<Ciudades> listaCiudades;
        listaCiudades = persistenciaCiudades.ciudades();
        return listaCiudades;
    }
    
    @Override
    public List<Ciudades>  lovCiudades(){
        return persistenciaCiudades.ciudades();
    }

    @Override
    public void modificarCiudad(List<Ciudades> listaCiudadesModificar) {
        for (int i = 0; i < listaCiudadesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaCiudadesModificar.get(i).getDepartamento().getSecuencia() == null) {
                listaCiudadesModificar.get(i).setDepartamento(null);
                c = listaCiudadesModificar.get(i);
            } else {
                c = listaCiudadesModificar.get(i);
            }
            persistenciaCiudades.editar(c);
        }
    }

    public void borrarCiudad(Ciudades ciudades) {
        persistenciaCiudades.borrar(ciudades);
    }

    public void crearCiudad(Ciudades ciudades) {
        persistenciaCiudades.crear(ciudades);
    }
}
