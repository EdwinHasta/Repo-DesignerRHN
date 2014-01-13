/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Ciudades;
import InterfaceAdministrar.AdministrarCiudadesInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'Ciudades'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarCiudades implements AdministrarCiudadesInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCiudades'.
     */
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaDepartamentos'.
     */
    @EJB
    PersistenciaDepartamentosInterface persistenciaDepartamentos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------    

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
    public void modificarCiudades(List<Ciudades> listaCiudades) {
        Ciudades c;
        for (int i = 0; i < listaCiudades.size(); i++) {
            System.out.println("Modificando...");
            if (listaCiudades.get(i).getDepartamento().getSecuencia() == null) {
                listaCiudades.get(i).setDepartamento(null);
                c = listaCiudades.get(i);
            } else {
                c = listaCiudades.get(i);
            }
            persistenciaCiudades.editar(c);
        }
    }

    @Override
    public void borrarCiudades(List<Ciudades> listaCiudades) {
        for (int i = 0; i < listaCiudades.size(); i++) {
            System.out.println("Borrando...");
            if (listaCiudades.get(i).getDepartamento().getSecuencia() == null) {

                listaCiudades.get(i).setDepartamento(null);
                persistenciaCiudades.borrar(listaCiudades.get(i));
            } else {
                persistenciaCiudades.borrar(listaCiudades.get(i));
            }
        }        
    }
    
    @Override
    public void crearCiudades(List<Ciudades> listaCiudades){
        for (int i = 0; i < listaCiudades.size(); i++) {
            System.out.println("Borrando...");
            if (listaCiudades.get(i).getDepartamento().getSecuencia() == null) {

                listaCiudades.get(i).setDepartamento(null);
                persistenciaCiudades.crear(listaCiudades.get(i));
            } else {
                persistenciaCiudades.crear(listaCiudades.get(i));
            }
        }     
    }
}
